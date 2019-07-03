package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.enums.EnumSystemDictType;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.VsUserMenuBO;
import com.zzboot.system.dao.VsUserDAO;
import com.zzboot.system.dao.VsUserMenuDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.VsUserMenuService;
import com.zzboot.util.config.Global;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:56:24
 */
@Service
public class VsUserMenuServiceImpl extends SystemBaseServiceImpl<VsUserMenuBO,String> implements VsUserMenuService {



	@Autowired
	private TsDictService tsDictService;



	@Autowired
	private VsUserDAO vsUserDAO;


	@Autowired
	private VsUserMenuDAO vsUserMenuDAO ;



	@Override
	public BaseDAO getDAO() {
		return vsUserMenuDAO ;
	}



	@Override
	public VsUserMenuBO processResult(VsUserMenuBO vsUserMenuBO) {

		try {
			if(StringUtils.isEmpty(vsUserMenuBO.getShortcutName()) && StringUtils.isNotEmpty(vsUserMenuBO.getShortcut()) ) {
				String dictName = tsDictService.getDictName(vsUserMenuBO.getShortcut(), EnumSystemDictType.SHORTCUT.getVal());
				vsUserMenuBO.setShortcutName(dictName);
			}
		}catch(Exception e){

		}

		return vsUserMenuBO;

	}





	@Override
	public List<VsUserMenuBO> processResult(List<VsUserMenuBO> vsUserMenuBOs) {
		if(vsUserMenuBOs == null || vsUserMenuBOs.isEmpty()){
			return vsUserMenuBOs;
		}

		int n = Global.getAppConfig().getMenuMaxLevel();
		Map<String , VsUserMenuBO> map = new HashMap<String , VsUserMenuBO>();
		vsUserMenuBOs.forEach(vsUserMenuBO -> {
			int count = (n - vsUserMenuBO.getLevel())*2;
			StringBuilder s = new StringBuilder(vsUserMenuBO.getSortno().toString());
			for(int index = 0 ; index < count ; index ++){
				s.append("0");
			}
			vsUserMenuBO.setSortIndex(Integer.parseInt(s.toString()));

			if(StringUtils.isNotEmpty(vsUserMenuBO.getPid())){
				VsUserMenuBO parent = map.get(vsUserMenuBO.getPid());
				if(parent != null){
					vsUserMenuBO.setSortIndex(vsUserMenuBO.getSortIndex() + parent.getSortIndex());
				}
			}
			map.put(vsUserMenuBO.getId() , vsUserMenuBO);

		});



		String[] dictTypes = new String[]{EnumSystemDictType.SHORTCUT.getVal()};
		Map<String , TsDictBO> dictMap = tsDictService.allDict(dictTypes);

		vsUserMenuBOs.forEach(vsUserMenuBO -> {

			if(StringUtils.isEmpty(vsUserMenuBO.getShortcutName()) && StringUtils.isNotEmpty(vsUserMenuBO.getShortcut()) ) {
				TsDictBO dict = dictMap.get(EnumSystemDictType.SHORTCUT.getVal() + vsUserMenuBO.getShortcut());
				if(dict != null) {
					vsUserMenuBO.setShortcutName(dict.getDictName());
				}
			}
		});



		return vsUserMenuBOs;
	}




	@Override
	public void isExist(VsUserMenuBO vsUserMenuBO) {
	}


}