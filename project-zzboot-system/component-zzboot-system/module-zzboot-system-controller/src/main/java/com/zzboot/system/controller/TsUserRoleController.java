package com.zzboot.system.controller;


import com.zzboot.framework.PermissionList;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.vo.AjaxJson;
import com.zzboot.system.bo.TsRoleBO;
import com.zzboot.system.bo.TsUserBO;
import com.zzboot.system.bo.TsUserRoleBO;
import com.zzboot.system.query.impl.TsUserRoleQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsRoleService;
import com.zzboot.system.service.TsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户角色关联 控制层
* @author Administrator
* @date 2019-4-11 15:21:25
 */
@RequestMapping("/system/userrole")
@Controller


public class TsUserRoleController extends ZzDefaultSimpleController<TsUserRoleBO, String , TsUserRoleQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;


	@Autowired
	private TsUserService tsUserService;


	@Autowired
	private TsRoleService tsRoleService;


	public TsUserRoleController(){
		super();
		this.setResourceIdentity("system.role");
		this.setResourceIdentity("system.user");
	}

	@Override
	public void assertHasCreatePermission() {
		permissionList.assertHasAnyPermission(new String[]{
				"system.role:"+ PermissionList.UPDATE_PERMISSION,
				"system.user:"+ PermissionList.UPDATE_PERMISSION
		});
	}

	@Override
	public void assertHasUpdatePermission() {
		permissionList.assertHasAnyPermission(new String[]{
				"system.role:"+ PermissionList.UPDATE_PERMISSION,
				"system.user:"+ PermissionList.UPDATE_PERMISSION
		});
	}

	@Override
	public void assertHasDeletePermission() {
		permissionList.assertHasAnyPermission(new String[]{
				"system.role:"+ PermissionList.UPDATE_PERMISSION,
				"system.user:"+ PermissionList.UPDATE_PERMISSION
		});

	}



	/**
	 * 到关联表类型的列表界面
	 * @param m
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toRelevanceList" , method = RequestMethod.GET )
	public String toRelevanceList(TsUserRoleBO m, ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) {

		this.assertHasViewPermission();

		this.baseRwService.processResult(m);
		modelMap.put("entity" ,m);
		modelMap.put("m" ,m);

		if (listAlsoSetCommonData) {
			setCommonData(m,modelMap);
		}

		processQueryString(modelMap,request);

		processPath(modelMap);

		String pageName = this.getRelevanceListPageName();
		if(StringUtils.isEmpty(pageName)){
			pageName = defaultRelevanceListPageName;
		}
		return viewName(pageName);
	}


	/**
	 * 新增操作
	 * @param ms
	 * @param m
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveRelevanceList", method = RequestMethod.POST , consumes = "application/json")
	@ResponseBody
	public Object saveRelevanceList(@RequestBody List<TsUserRoleBO> ms, TsUserRoleBO m, TsUserRoleQueryWebImpl Q, ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) {


		this.assertHasCreatePermission();

		ILoginUserEntity<String> sessionUserVO = getSessionUser();

		if(!EntityUtil.isEmpty(m.getUserId())){
			TsUserBO userBO = tsUserService.getById(m.getUserId());
			if(userBO == null){
				throw EnumErrorMsg.no_auth.toException();
			}


		}else if(!EntityUtil.isEmpty(m.getRoleId())){
			TsRoleBO roleBO = tsRoleService.getById(m.getRoleId());
			if(roleBO == null){
				throw EnumErrorMsg.no_auth.toException();
			}

		}else {
			throw EnumErrorMsg.param_format_error.toException();
		}

		this.baseRwService.saveBatchRelevance(ms , m );

		AjaxJson result =  AjaxJson.ok();
		return result;
	}



	@Override
	public void setCustomInfoByInsert(TsUserRoleBO bo , ILoginUserEntity<String> sessionUser){

	}





}
