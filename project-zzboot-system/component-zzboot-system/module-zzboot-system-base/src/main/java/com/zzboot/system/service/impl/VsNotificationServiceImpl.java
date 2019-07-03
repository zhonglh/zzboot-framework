package com.zzboot.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.enums.EnumSystemDictType;

import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.VsFileUseService;

import com.zzboot.system.bo.VsFileUseBO;
import com.zzboot.system.bo.TsDictBO;


import com.zzboot.system.bo.VsNotificationBO;
import com.zzboot.system.dao.VsNotificationDAO;
import com.zzboot.system.service.VsNotificationService;

import com.zzboot.system.dao.VsUserDAO;




import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* 系统通知 ServiceImpl
* @author Administrator
* @date 2019-6-8 22:01:58
*/
@Service
public class VsNotificationServiceImpl extends SystemBaseServiceImpl<VsNotificationBO,String> implements VsNotificationService {



	@Autowired
	private TsDictService tsDictService;


	@Autowired
	private VsFileUseService vsFileUseService;


    @Autowired
    private VsUserDAO vsUserDAO;


    @Autowired
    private VsNotificationDAO vsNotificationDAO ;



    @Override
    public BaseDAO getDAO() {
    	return vsNotificationDAO ;
    }



	@Override
	public VsNotificationBO processResult(VsNotificationBO vsNotificationBO) {

		try {
			if(StringUtils.isEmpty(vsNotificationBO.getIsReadName()) && StringUtils.isNotEmpty(vsNotificationBO.getIsRead()) ) {
				String dictName = tsDictService.getDictName(vsNotificationBO.getIsRead(), EnumSystemDictType.YES_NO.getVal());
				vsNotificationBO.setIsReadName(dictName);
			}
		}catch(Exception e){

		}


		try{
			if(StringUtils.isNotEmpty(vsNotificationBO.getNotifyFiles()) && vsNotificationBO.getNotifyFilesList() == null){
			QueryWrapper<VsFileUseBO> qw = new QueryWrapper<VsFileUseBO>();
			qw.lambda().eq(VsFileUseBO::getBusinessId , vsNotificationBO.getId());
			qw.lambda().eq(VsFileUseBO::getBusinessTempId , vsNotificationBO.getNotifyFiles());
			List<VsFileUseBO> list = vsFileUseService.list(qw);
				vsNotificationBO.setNotifyFilesList(list);
			}
		}catch(Exception e){}



		return vsNotificationBO;

	}





	@Override
	public List<VsNotificationBO> processResult(List<VsNotificationBO> vsNotificationBOs) {
		if(vsNotificationBOs == null || vsNotificationBOs.isEmpty()){
			return vsNotificationBOs;
		}






		String[] dictTypes = new String[]{EnumSystemDictType.YES_NO.getVal()};
		Map<String , TsDictBO> dictMap = tsDictService.allDict(dictTypes);

		vsNotificationBOs.forEach(vsNotificationBO -> {
			if(StringUtils.isEmpty(vsNotificationBO.getIsReadName()) && StringUtils.isNotEmpty(vsNotificationBO.getIsRead()) ) {
				TsDictBO dict = dictMap.get(EnumSystemDictType.YES_NO.getVal() + vsNotificationBO.getIsRead());
				if(dict != null) {
					vsNotificationBO.setIsReadName(dict.getDictName());
				}
			}
		});
		return vsNotificationBOs;
	}


	@Override
	public void isExist(VsNotificationBO vsNotificationBO) {
	}





}