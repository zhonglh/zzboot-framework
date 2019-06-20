package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.VsUserPermitBO;
import com.zzboot.system.dao.VsUserDAO;
import com.zzboot.system.dao.VsUserPermitDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.VsUserPermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户许可 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:56:24
 */
@Service
public class VsUserPermitServiceImpl extends SystemBaseServiceImpl<VsUserPermitBO,String> implements VsUserPermitService {



	@Autowired
	private TsDictService tsDictService;



	@Autowired
	private VsUserDAO vsUserDAO;


	@Autowired
	private VsUserPermitDAO vsUserPermitDAO ;



	@Override
	public BaseDAO getDAO() {
		return vsUserPermitDAO ;
	}







	@Override
	public void isExist(VsUserPermitBO vsUserPermitBO) {
	}


}