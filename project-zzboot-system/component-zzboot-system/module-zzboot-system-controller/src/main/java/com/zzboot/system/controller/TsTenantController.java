package com.zzboot.system.controller;

import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.system.bo.TsTenantBO;
import com.zzboot.system.query.impl.TsTenantQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 企业 控制层
* @author Administrator
* @date 2019-4-11 15:21:25
 */
@RequestMapping("/system/tenant")
@Controller


public class TsTenantController extends ZzDefaultSimpleController<TsTenantBO, String , TsTenantQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;

	@Override
	public void setCustomInfoByInsert(TsTenantBO bo , ILoginUserEntity<String> sessionUser){
	}





}
