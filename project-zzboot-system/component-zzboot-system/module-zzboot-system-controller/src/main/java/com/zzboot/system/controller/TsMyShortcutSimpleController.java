package com.zzboot.system.controller;


import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumSymbol;
import com.zzboot.framework.core.vo.AjaxJson;
import com.zzboot.system.bo.TsMyShortcutBO;
import com.zzboot.system.query.impl.TsMyShortcutQueryWebImpl;
import com.zzboot.system.service.TsMyShortcutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 置顶快捷菜单 控制层
* @author Administrator
* @date 2018-9-8 12:42:41
 */
@RequestMapping("/system/myshortcut")
@Controller
public class TsMyShortcutSimpleController extends ZzDefaultSimpleController<TsMyShortcutBO, String , TsMyShortcutQueryWebImpl> {

    @Autowired
    private TsMyShortcutService tsMyShortcutService;

    @RequestMapping(value = "/saveAll" ,method = RequestMethod.POST)
    public Object saveAll(String menuIds, ModelMap model , HttpServletRequest request , HttpServletResponse response){

        ILoginUserEntity loginUser = getSessionUser();

        tsMyShortcutService.saveAll(menuIds.split( EnumSymbol.COMMA.getCode() ) , (String)loginUser.getId());

        return AjaxJson.successAjax;
    }

}
