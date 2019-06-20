package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.enums.EnumDictType;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsMenuBO;
import com.zzboot.system.dao.TsMenuDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 菜单 ServiceImpl
* @author Administrator
* @date 2019-5-8 16:10:21
*/
@Service
public class TsMenuServiceImpl extends SystemBaseServiceImpl<TsMenuBO,String> implements TsMenuService {



	@Autowired
	private TsDictService tsDictService;





    @Autowired
    private TsMenuDAO tsMenuDAO ;



    @Override
    public BaseDAO getDAO() {
    	return tsMenuDAO ;
    }



	@Override
	public List<TsMenuBO> getAllMenu(TsMenuBO tsMenuBO){
    	if(tsMenuBO == null){
    		return null;
		}

		List<TsMenuBO> list = new ArrayList<TsMenuBO>();
    	list.add(tsMenuBO);
    	if(StringUtils.isNotEmpty(tsMenuBO.getPid())) {
			getParent(list);
		}

    	return list;

	}

	private void getParent(List<TsMenuBO> list){
    	if(list == null || list.isEmpty()){
    		return ;
		}

		TsMenuBO curr = list.get(0);
		TsMenuBO parent = this.tsMenuDAO.selectById(curr.getPid());
		list.add(0,parent);

		if(StringUtils.isNotEmpty(parent.getPid())){
			getParent(list);
		}

	}

	@Override
	public TsMenuBO processResult(TsMenuBO tsMenuBO) {

		try {
			if(StringUtils.isEmpty(tsMenuBO.getLeafName()) && StringUtils.isNotEmpty(tsMenuBO.getLeaf()) ) {
				String dictName = tsDictService.getDictName(tsMenuBO.getLeaf(),EnumDictType.YES_NO.getVal());
				tsMenuBO.setLeafName(dictName);
			}
		}catch(Exception e){

		}

		if(StringUtils.isNotEmpty( tsMenuBO.getPid())){
			TsMenuBO temp = tsMenuDAO.selectById( tsMenuBO.getPid() );
			if(temp != null){
				tsMenuBO.setPname(temp.getMenuName());
			}
		}
		try {
			if(StringUtils.isEmpty(tsMenuBO.getHiddenName()) && StringUtils.isNotEmpty(tsMenuBO.getHidden()) ) {
				String dictName = tsDictService.getDictName(tsMenuBO.getHidden(),EnumDictType.YES_NO.getVal());
				tsMenuBO.setHiddenName(dictName);
			}
		}catch(Exception e){

		}
		try {
			if(StringUtils.isEmpty(tsMenuBO.getShortcutName()) && StringUtils.isNotEmpty(tsMenuBO.getShortcut()) ) {
				String dictName = tsDictService.getDictName(tsMenuBO.getShortcut(),EnumDictType.YES_NO.getVal());
				tsMenuBO.setShortcutName(dictName);
			}
		}catch(Exception e){

		}

		return tsMenuBO;

	}





	@Override
	public List<TsMenuBO> processResult(List<TsMenuBO> tsMenuBOs) {
		if(tsMenuBOs == null || tsMenuBOs.isEmpty()){
			return tsMenuBOs;
		}

		List<String> pidList = new ArrayList<String>();

		for(TsMenuBO bo : tsMenuBOs)		{

			if(StringUtils.isNotEmpty( bo.getPid())){
				pidList.add(bo.getPid());
			}
		}


		if(!pidList.isEmpty()){
			List<TsMenuBO> list =  tsMenuDAO.selectBatchIds(pidList);
			Map<String,TsMenuBO> map = EntityUtil.list2Map(list);

			tsMenuBOs.forEach(tsMenuBO -> {
				if(StringUtils.isNotEmpty( tsMenuBO.getPid())){
					TsMenuBO temp = map.get( tsMenuBO.getPid() );
					if(temp != null){
							tsMenuBO.setPname(temp.getMenuName());
					}
				}
			});
		}





		String[] dictTypes = new String[]{EnumDictType.YES_NO.getVal(),EnumDictType.YES_NO.getVal(),EnumDictType.YES_NO.getVal()};
		Map<String , TsDictBO> dictMap = tsDictService.allDict(dictTypes);

		tsMenuBOs.forEach(tsMenuBO -> {
			if(StringUtils.isEmpty(tsMenuBO.getLeafName()) && StringUtils.isNotEmpty(tsMenuBO.getLeaf()) ) {
				TsDictBO dict = dictMap.get(EnumDictType.YES_NO.getVal() + tsMenuBO.getLeaf());
				if(dict != null) {
					tsMenuBO.setLeafName(dict.getDictName());
				}
			}
			if(StringUtils.isEmpty(tsMenuBO.getHiddenName()) && StringUtils.isNotEmpty(tsMenuBO.getHidden()) ) {
				TsDictBO dict = dictMap.get(EnumDictType.YES_NO.getVal() + tsMenuBO.getHidden());
				if(dict != null) {
					tsMenuBO.setHiddenName(dict.getDictName());
				}
			}
			if(StringUtils.isEmpty(tsMenuBO.getShortcutName()) && StringUtils.isNotEmpty(tsMenuBO.getShortcut()) ) {
				TsDictBO dict = dictMap.get(EnumDictType.YES_NO.getVal() + tsMenuBO.getShortcut());
				if(dict != null) {
					tsMenuBO.setShortcutName(dict.getDictName());
				}
			}
		});
		return tsMenuBOs;
	}


	@Override
	public void isExist(TsMenuBO tsMenuBO) {
	}





}