package com.zzboot.system.controller;

import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.system.bo.TsFileUseBO;
import com.zzboot.system.bo.VsFileUseBO;
import com.zzboot.system.query.impl.TsFileUseQueryWebImpl;
import com.zzboot.system.query.impl.VsFileUseQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文件使用 控制层
* @author Administrator
* @date 2019-4-11 16:31:59
 */
@RequestMapping("/system/fileuse")
@Controller


public class TsFileUseController extends ZzDefaultController<TsFileUseBO, VsFileUseBO, String , TsFileUseQueryWebImpl, VsFileUseQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;

	@Override
	public void setCustomInfoByInsert(TsFileUseBO bo , ILoginUserEntity<String> sessionUser){
	}





}
