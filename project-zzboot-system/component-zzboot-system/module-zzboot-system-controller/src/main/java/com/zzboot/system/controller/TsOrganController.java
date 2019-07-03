package com.zzboot.system.controller;


import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsOrganBO;
import com.zzboot.system.enums.EnumRoleStatus;
import com.zzboot.system.enums.EnumSystemDictType;
import com.zzboot.system.query.impl.TsOrganQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 机构 控制层
* @author Administrator
* @date 2019-4-11 15:21:24
 */
@RequestMapping("/system/organ")
@Controller


public class TsOrganController extends ZzDefaultSimpleController<TsOrganBO, String , TsOrganQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;

	@Override
	public void setCustomInfoByInsert(TsOrganBO bo , ILoginUserEntity<String> sessionUser){

		bo.setOrganStatus(EnumRoleStatus.normal.getVal());
		bo.setOrganStatusName(EnumRoleStatus.normal.getLabel());

	}


	@Override
	protected void setCommonData(TsOrganBO tsOrganBO , ModelMap model) {
    	Map<String , List<TsDictBO>> dictMap = tsDictService.allDicts(EnumSystemDictType.ORGAN_TYPE.getVal(),EnumSystemDictType.ORGAN_STATUS.getVal());
        for(Map.Entry<String , List<TsDictBO>> dictObj : dictMap.entrySet()){
        	model.put(dictObj.getKey()+"_dicts", dictObj.getValue());
        }
	}



}
