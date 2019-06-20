package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.system.bo.TsTenantBO;
import com.zzboot.system.dao.TsTenantDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 企业 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:45:41
 */
@Service
public class TsTenantServiceImpl extends SystemBaseServiceImpl<TsTenantBO,String> implements TsTenantService {



	@Autowired
	private TsDictService tsDictService;





	@Autowired
	private TsTenantDAO tsTenantDAO ;



	@Override
	public BaseDAO getDAO() {
		return tsTenantDAO ;
	}







	@Override
	public void isExist(TsTenantBO tsTenantBO) {

		TsTenantBO ckBO ;
		TsTenantBO temp = null ;

		ckBO = new TsTenantBO();
		ckBO.setId( tsTenantBO.getId() );
		ckBO.setTenantName(tsTenantBO.getTenantName());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"    ");
		}

		ckBO = new TsTenantBO();
		ckBO.setId( tsTenantBO.getId() );
		ckBO.setTenantCode(tsTenantBO.getTenantCode());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"    ");
		}


	}


}