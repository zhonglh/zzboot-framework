package com.zzboot.system.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.zzboot.framework.controller.BaseController;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.vo.AjaxJson;
import com.zzboot.framework.core.enums.EnumOperationType;
import com.zzboot.framework.events.LoginLogEvent;
import com.zzboot.framework.shiro.utils.ShiroUtils;
import com.zzboot.util.web.IpUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;


/**
 * 处理登录信息
 * @author Administrator
 */
@RequestMapping("/login")
@Controller
public class LoginController extends BaseController {

    @Autowired
    ApplicationContext applicationContext;

    //@Autowired
    //private Producer producer;




    @RequestMapping(value = "/toLogin" ,method = RequestMethod.GET)
    public String toLogin() {
        try {
            if ((null != ShiroUtils.getSubject() && ShiroUtils.getSubject().isAuthenticated()) || ShiroUtils.getSubject().isRemembered()) {
                return "redirect:/main/home";
            } else {
                return "login/login";
            }
        }catch (Exception e){

            return "login/login";
        }
    }

    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        //String text = producer.createText();

        //生成图片验证码
        //BufferedImage image = producer.createImage(text);

        //保存到shiro session
        //ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        //ImageIO.write(image, "jpg", out);
    }




    @RequestMapping("/login")
    @ResponseBody
    public Object login(String loginName, String loginPassword , HttpServletRequest request) {
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginName, loginPassword);
            subject.login(token);


            try {
                ILoginUserEntity loginUser = (ILoginUserEntity)subject.getPrincipal();
                if (loginUser != null) {
                    LoginLogEvent le = new LoginLogEvent(new Date());
                    le.setIp(IpUtil.getIpAddr(request));
                    le.setLoginType(EnumOperationType.LOGIN.getVal());
                    le.setUserId(loginUser.getId());
                    applicationContext.publishEvent(le);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return AjaxJson.successAjax;
        }catch (UnknownAccountException e) {
            return AjaxJson.fail(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return AjaxJson.fail("账号或密码不正确");
        }catch (LockedAccountException e) {
            return AjaxJson.fail("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return AjaxJson.fail(e.getMessage());
        }catch (Exception e) {
            return AjaxJson.fail(e.getMessage());
        }
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        try{
            Subject subject = ShiroUtils.getSubject();

            try {
                ILoginUserEntity loginUser = (ILoginUserEntity)subject.getPrincipal();
                if (loginUser != null) {
                    LoginLogEvent le = new LoginLogEvent(new Date());
                    le.setIp(IpUtil.getIpAddr(request));
                    le.setLoginType(EnumOperationType.LOGOUT.getVal());
                    le.setUserId(loginUser.getId());
                    applicationContext.publishEvent(le);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            subject.logout();
        }catch (Exception e) {

        }
        return "redirect:/login/toLogin";
    }


}
