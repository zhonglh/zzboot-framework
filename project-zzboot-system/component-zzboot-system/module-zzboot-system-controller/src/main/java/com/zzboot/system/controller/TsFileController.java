package com.zzboot.system.controller;

import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsFileBO;
import com.zzboot.system.enums.EnumSystemDictType;
import com.zzboot.system.query.impl.TsFileQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 文件 控制层
* @author Administrator
* @date 2019-4-11 16:32:00
 */
@RequestMapping("/system/file")
@Controller


public class TsFileController extends ZzDefaultSimpleController<TsFileBO, String , TsFileQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;

	@Override
	public void setCustomInfoByInsert(TsFileBO bo , ILoginUserEntity<String> sessionUser){
	}


	@Override
	protected void setCommonData(TsFileBO tsFileBO , ModelMap model) {
    	Map<String , List<TsDictBO>> dictMap = tsDictService.allDicts(EnumSystemDictType.FILE_ENGINE.getVal());
        for(Map.Entry<String , List<TsDictBO>> dictObj : dictMap.entrySet()){
        	model.put(dictObj.getKey()+"_dicts", dictObj.getValue());
        }
	}



}
