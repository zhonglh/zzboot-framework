package com.zzboot.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzboot.framework.controller.BaseController;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumYesNo;
import com.zzboot.framework.core.vo.AjaxJson;
import com.zzboot.framework.shiro.utils.ShiroUtils;
import com.zzboot.system.bo.TsMenuBO;
import com.zzboot.system.bo.TsMyShortcutBO;
import com.zzboot.system.bo.VsFileUseBO;
import com.zzboot.system.bo.VsUserMenuBO;
import com.zzboot.system.domain.TsUserEntity;
import com.zzboot.system.logic.MenuLogic;
import com.zzboot.system.query.TsMyShortcutQuery;
import com.zzboot.system.query.VsNotificationQuery;
import com.zzboot.system.query.VsUserMenuQuery;
import com.zzboot.system.query.impl.TsMyShortcutQueryImpl;
import com.zzboot.system.query.impl.VsNotificationQueryImpl;
import com.zzboot.system.query.impl.VsUserMenuQueryImpl;
import com.zzboot.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 处理首页信息
 * @author Administrator
 */
@RequestMapping("/main")
@Controller
public class MainController extends BaseController {


    @Autowired
    private VsFileUseService vsFileUseService ;

    @Autowired
    private VsUserMenuService vsUserMenuService;


    @Autowired
    private TsMenuService tsMenuService;

    @Autowired
    private TsMyShortcutService myShortcutService;

    @Autowired
    private VsNotificationService vsNotificationService;



    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String home2(HttpServletRequest request , HttpServletResponse response){
        return "main/welcome";
    }


    @RequestMapping(value = "/setTheme", method = {RequestMethod.GET , RequestMethod.POST})
    @ResponseBody
    public AjaxJson setTheme(String theme , HttpServletRequest request , HttpServletResponse response) {
        this.addCookie(response , "theme" , theme , 0);
        request.setAttribute("theme" , theme);
        return AjaxJson.successAjax;
    }


    /**
     * 主页
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model , HttpServletRequest request , HttpServletResponse response) {
        final ILoginUserEntity loginUser = this.getSessionUser();
        if (loginUser instanceof TsUserEntity) {
            ((TsUserEntity) loginUser).setLoginPassword(null);
        }

        model.put("loginUser", loginUser);
        model.put("userSessoinId", ShiroUtils.getSession().getId());

        String userImageUrl = request.getContextPath() +"/statics2/image/default.png";
        if(StringUtils.isNotEmpty(loginUser.getAvatarImage())){
            VsFileUseBO vsFileUseBO = null;
            try {
                QueryWrapper<VsFileUseBO> qw = new QueryWrapper<VsFileUseBO>();
                qw.lambda().eq(VsFileUseBO::getBusinessId , loginUser.getId());
                qw.lambda().eq(VsFileUseBO::getBusinessTempId , loginUser.getAvatarImage());
                qw.lambda().orderByDesc(VsFileUseBO::getCreateTime);
                List<VsFileUseBO> list = vsFileUseService.list(qw);
                vsFileUseBO = list.get(0);
            }catch (Exception e){

            }
            if(vsFileUseBO != null) {
                //EnumFileEngine.FILESYSTEM.getVal()
                if ("1".equals(vsFileUseBO.getFileEngine())) {
                    userImageUrl = request.getContextPath() + vsFileUseBO.getAccessUrl() + vsFileUseBO.getId();
                } else {
                    userImageUrl = vsFileUseBO.getAccessUrl();
                }
            }
        }


        model.put("userImageUrl", userImageUrl);



        try {
            //处理菜单
            VsUserMenuQuery query = new VsUserMenuQueryImpl();
            query.userId(loginUser.getId());
            QueryWrapper<VsUserMenuBO> qw = query.buildWrapper();
            qw.orderByAsc("level");
            List<VsUserMenuBO> menus = vsUserMenuService.list(qw);

            if(menus != null && !menus.isEmpty() && !loginUser.isSystemUser()) {

                List<TsMenuBO> parentMenus = MenuLogic.getAllParentMenusByView(tsMenuService.list() , menus);
                if(parentMenus != null && !parentMenus.isEmpty()) {
                    menus.addAll(MenuLogic.toVsUserMenu(parentMenus));
                    menus.sort ((o1, o2) -> o1.getLevel() - o2.getLevel() );
                    vsUserMenuService.processResult(menus);
                }
            }

            if(menus != null && !menus.isEmpty()) {
                //menus = MenuLogic.sortMenu(menus);
                menus.sort ((o1, o2) -> o1.getSortIndex() - o2.getSortIndex() );
            }else {
                menus = new ArrayList<VsUserMenuBO>();
            }
            model.put("menus", menus);

        //处理快捷菜单
        List<VsUserMenuBO> shortcutMenus = new ArrayList<VsUserMenuBO>();
        for (VsUserMenuBO menu : menus) {
            if (EnumYesNo.YES.getCode().equals(menu.getLeaf()) && EnumYesNo.YES.getCode().equals(menu.getShortcut())) {
                shortcutMenus.add(menu);
            }
        }
        TsMyShortcutQuery shortcutQuery = new TsMyShortcutQueryImpl();
        shortcutQuery.userId(loginUser.getId());
        QueryWrapper shortcutQ = shortcutQuery.buildWrapper();
        shortcutQ.orderByAsc("orderby");
        List<TsMyShortcutBO> shortcuts = myShortcutService.list(shortcutQ);
        Map<String, String> myShortcutMap = new HashMap<String, String>();
        if (shortcuts != null && !shortcuts.isEmpty()) {
            for (TsMyShortcutBO shortcut : shortcuts) {
                myShortcutMap.put(shortcut.getMenuId(), shortcut.getMenuId());
            }
        }
        List<VsUserMenuBO> topShortcutMenus = new ArrayList<VsUserMenuBO>();
        List<VsUserMenuBO> moreShortcutMenus = new ArrayList<VsUserMenuBO>();
        for (VsUserMenuBO shortcutMenu : shortcutMenus) {
            if (myShortcutMap.containsKey(shortcutMenu.getId())) {
                topShortcutMenus.add(shortcutMenu);
            } else {
                moreShortcutMenus.add(shortcutMenu);
            }
        }
        model.put("topShortcutMenus", topShortcutMenus);
        model.put("moreShortcutMenus", moreShortcutMenus);


        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        //处理未读通知数
        VsNotificationQuery notifyQuery = new VsNotificationQueryImpl();
        notifyQuery.receiveUserId(loginUser.getId());
        notifyQuery.isRead(EnumYesNo.NO.getCode());
        int count = vsNotificationService.count(notifyQuery.buildWrapper());
        model.put("notReadNotifyCount", count);

        return "main/home";
    }




}
