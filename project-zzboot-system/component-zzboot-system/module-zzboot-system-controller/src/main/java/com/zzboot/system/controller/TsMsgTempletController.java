package com.zzboot.system.controller;


import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumYesNo;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsMsgTempletBO;
import com.zzboot.system.enums.EnumSystemDictType;
import com.zzboot.system.query.impl.TsMsgTempletQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 消息模板 控制层
* @author Administrator
* @date 2019-4-11 15:21:26
 */
@RequestMapping("/system/msgtemplet")
@Controller


public class TsMsgTempletController extends ZzDefaultSimpleController<TsMsgTempletBO, String , TsMsgTempletQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;

	@Override
	public void setCustomInfoByInsert(TsMsgTempletBO bo , ILoginUserEntity<String> sessionUser){
		bo.setDepId((String)sessionUser.getDepId());
		bo.setDepName(sessionUser.getDepName());
		bo.setOrganId((String)sessionUser.getOrganId());
		bo.setTempletEffective(EnumYesNo.YES.getCode());
        bo.setTempletEffectiveName(EnumYesNo.YES.getName());
	}


	@Override
	protected void setCommonData(TsMsgTempletBO tsMsgTempletBO , ModelMap model) {
    	Map<String , List<TsDictBO>> dictMap = tsDictService.allDicts(EnumSystemDictType.MSG_LANGUAGE.getVal(),EnumSystemDictType.YES_NO.getVal(),EnumSystemDictType.MSG_TEMPLET_TYPE.getVal());
        for(Map.Entry<String , List<TsDictBO>> dictObj : dictMap.entrySet()){
        	model.put(dictObj.getKey()+"_dicts", dictObj.getValue());
        }
	}



}
