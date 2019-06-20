package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.system.bo.TsPermitBO;
import com.zzboot.system.dao.TsPermitDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsPermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 许可 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:25:11
 */
@Service
public class TsPermitServiceImpl extends SystemBaseServiceImpl<TsPermitBO,String> implements TsPermitService {



	@Autowired
	private TsDictService tsDictService;





	@Autowired
	private TsPermitDAO tsPermitDAO ;



	@Override
	public BaseDAO getDAO() {
		return tsPermitDAO ;
	}







	@Override
	public void isExist(TsPermitBO tsPermitBO) {

		TsPermitBO ckBO ;
		TsPermitBO temp = null ;

		ckBO = new TsPermitBO();
		ckBO.setId( tsPermitBO.getId() );
		ckBO.setPermitCode(tsPermitBO.getPermitCode());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"许可编码已使用");
		}


	}


}