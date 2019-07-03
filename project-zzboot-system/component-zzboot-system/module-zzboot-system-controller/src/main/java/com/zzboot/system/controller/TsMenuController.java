package com.zzboot.system.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsMenuBO;
import com.zzboot.system.enums.EnumSystemDictType;
import com.zzboot.system.query.impl.TsMenuQueryWebImpl;
import com.zzboot.system.service.TsDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 菜单 控制层
* @author Administrator
* @date 2019-4-11 15:21:26
 */
@RequestMapping("/system/menu")
@Controller


public class TsMenuController extends ZzDefaultSimpleController<TsMenuBO, String , TsMenuQueryWebImpl> {

	@Autowired
	private TsDictService tsDictService;

	@Override
	public void setCustomInfoByInsert(TsMenuBO bo , ILoginUserEntity<String> sessionUser){
	}

	@Override
	protected Wrapper buildQueryWrapper(TsMenuQueryWebImpl query , TsMenuBO m) {
		QueryWrapper wrapper =   query.buildWrapper();
		wrapper.orderByAsc(" menu_sort " );
		return wrapper;
	}


	@Override
	protected void setCommonData(TsMenuBO tsMenuBO , ModelMap model) {
    	Map<String , List<TsDictBO>> dictMap = tsDictService.allDicts(EnumSystemDictType.YES_NO.getVal());
        for(Map.Entry<String , List<TsDictBO>> dictObj : dictMap.entrySet()){
        	model.put(dictObj.getKey()+"_dicts", dictObj.getValue());
        }
	}



}
