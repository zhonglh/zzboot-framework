package com.zzboot.system.controller;


import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.system.bo.TsNotificationBO;
import com.zzboot.system.query.impl.TsNotificationQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统通知 控制层
* @author Administrator
* @date 2019-4-11 16:31:59
 */
@RequestMapping("/system/notification")
@Controller


public class TsNotificationController extends ZzDefaultSimpleController<TsNotificationBO, String , TsNotificationQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;

	@Override
	public void setCustomInfoByInsert(TsNotificationBO bo , ILoginUserEntity<String> sessionUser){
	}





}
