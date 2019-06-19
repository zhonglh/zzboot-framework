package com.zzboot.framework.controller;

import com.zzboot.framework.core.Constant;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.generics.AnnotaionEntityManager;
import com.zzboot.framework.core.ui.Pages;
import com.zzboot.framework.core.vo.AjaxJson;
import com.zzboot.framework.shiro.utils.ShiroUtils;
import com.zzboot.util.base.BankNoValidateUtils;
import com.zzboot.util.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础控制器，其他控制器需集成此控制器获得initBinder自动转换的功能
 * 
 */
@Slf4j
public abstract class BaseController<PK extends Serializable> {


    protected final  Pattern pattern= Pattern.compile ("^([a-z]{2})_([A-Z]{2})$");
    protected final String organId = "11111111111111111111111111111111";

    @Autowired(required = false)
    private SessionLocaleResolver localeResolver;



    @Autowired
    protected AnnotaionEntityManager annotaionEntityManager;



    /**
     * 检查银行卡是否正确
     * @param bankNo
     * @return
     */
    public AjaxJson checkBankNo(String bankNo){
        String result = BankNoValidateUtils.luhmCheck(bankNo);
        if(result.equals("true")){
            return AjaxJson.successAjax;
        }else {
            return  new AjaxJson(false,result);
        }
    }


    /*@InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
        binder.registerCustomEditor(BigDecimal.class, new BigDecimalConvertEditor());
        binder.registerCustomEditor(Timestamp.class, new TimestampConvertEditor());
    }*/


    protected ILoginUserEntity<PK> getSessionUser(HttpServletRequest request ){
        return getSessionUser();
    }

    protected ILoginUserEntity<PK> getSessionUser(){
        try {
            ILoginUserEntity loginUser = (ILoginUserEntity) ShiroUtils.getSubject().getPrincipal();
            if (loginUser == null) {
                RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                 if (requestAttributes != null) {
                     HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                     return (ILoginUserEntity)request.getSession().getAttribute(Constant.SESSION_USER);
                 }
            }

            return loginUser;
        }catch(Exception e){
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                return (ILoginUserEntity)request.getSession().getAttribute(Constant.SESSION_USER);
            }
        }
        return null;
    }




    protected HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    protected Locale getLocale(){
        return getLocale(getHttpServletRequest());
    }


    protected Locale getLocale(HttpServletRequest req){
        return RequestContextUtils.getLocale (req);
    }




    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale){
        localeResolver.setLocale (request, response, locale);
        request.getSession ().setAttribute ("locale", locale);
    }

    public Locale parseLocale(String locale){
        try {
            Matcher matcher = pattern.matcher (locale);
            if (matcher.find ()) {
                String language = matcher.group (1);
                String country = matcher.group (2);
                return new Locale (language,country);
            } else {
                return null;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace ();
            return null;
        }
    }


    protected String getMessage(HttpServletRequest request, String key, Object ... args){
        return SpringUtil.getMessage (getLocale (request) ,key, args );
    }

    protected String getMessage(String key,Object ... args){
        return SpringUtil.getMessage (getLocale (getHttpServletRequest()) ,key, args );
    }


    protected String getMessage(String key,String defaultVal , Object ... args){
        return SpringUtil.getMessage (getLocale (getHttpServletRequest()) ,key, defaultVal ,args );
    }



    /**
     * 设置cookie
     *
     * @param res
     * @param name      cookie名字
     * @param value     cookie值
     * @param maxAge    cookie生命周期 以秒为单位
     */
    protected void addCookie(HttpServletResponse res, String name, String value, int maxAge){
        try {
            value = URLEncoder.encode(value, "utf-8");
        }catch (Exception e){}
        Cookie cookie = new Cookie(name,value);
        cookie.setPath ("/");
        if (maxAge > 0) {cookie.setMaxAge (maxAge);}
        res.addCookie (cookie);
    }

    /**
     * 删除Cookie
     * @param req
     * @param res
     * @param name
     */
    public void removeCookie(HttpServletRequest req, HttpServletResponse res, String name) {
        Cookie cookie = this.getCookieByName(req,name);
        if(cookie != null) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name      cookie名字
     * @return
     */
    protected Cookie getCookieByName(HttpServletRequest request, String name){
        Map<String, Cookie> cookieMap = ReadCookieMap (request);
        if (cookieMap.containsKey (name)) {
            Cookie cookie = (Cookie) cookieMap.get (name);
            return cookie;
        } else {
            return null;
        }
    }


    protected  String getCookieVal(Cookie cookie){
        if(cookie==null) {
            return "";
        }else {
            String val = cookie.getValue();

            try {
                val = URLDecoder.decode(val,"UTF-8");
            } catch (Exception e) {
            }
            return val;

        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    protected Map<String, Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies ();
        if (null != cookies) {
            for ( Cookie cookie : cookies ) {
                cookieMap.put (cookie.getName (), cookie);
            }
        }
        return cookieMap;
    }


    protected  String getServerPath(HttpServletRequest request){
        return request.getSession().getServletContext().getRealPath("/");
    }


    public String getFullURL(HttpServletRequest request) {

        StringBuffer url = request.getRequestURL();
        if (request.getQueryString() != null) {
            url.append("?");
            url.append(request.getQueryString());
        }
        try {
            return URLDecoder.decode(url.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }



    protected void processQueryString(ModelMap modelMap, HttpServletRequest request) {

        String queryString = request.getQueryString();
        String inAllPage = request.getParameter("inAllPage");
        if(inAllPage == null) {
            inAllPage = "";
        }

        modelMap.addAttribute("inAllPage", inAllPage);
        if(StringUtils.isNotEmpty(queryString)) {
            modelMap.addAttribute("queryString", queryString);
            modelMap.addAttribute("allQueryString", "&"+queryString);
            String[] queryArr = queryString.split("&");
            modelMap.addAttribute("queryArr", queryArr);
        }else {

        }
    }


    /**
     * 处理分页信息， 设置页数 和每页记录数
     * @param pages
     * @param request
     */
    public void processPages(Pages pages , HttpServletRequest request){

    }










}
