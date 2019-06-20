package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.VsFileUseBO;
import com.zzboot.system.dao.VsFileUseDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.VsFileUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件使用 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:45:43
 */
@Service
public class VsFileUseServiceImpl extends SystemBaseServiceImpl<VsFileUseBO,String> implements VsFileUseService {



	@Autowired
	private TsDictService tsDictService;





	@Autowired
	private VsFileUseDAO vsFileUseDAO ;



	@Override
	public BaseDAO getDAO() {
		return vsFileUseDAO ;
	}



	@Override
	public VsFileUseBO processResult(VsFileUseBO vsFileUseBO) {



		return vsFileUseBO;

	}





	@Override
	public List<VsFileUseBO> processResult(List<VsFileUseBO> vsFileUseBOs) {
		if(vsFileUseBOs == null || vsFileUseBOs.isEmpty()){
			return vsFileUseBOs;
		}

		return vsFileUseBOs;
	}




	@Override
	public void isExist(VsFileUseBO vsFileUseBO) {
	}


}