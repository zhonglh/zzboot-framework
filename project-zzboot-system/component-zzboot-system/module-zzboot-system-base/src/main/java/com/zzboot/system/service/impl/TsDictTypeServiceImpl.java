package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.system.bo.TsDictTypeBO;
import com.zzboot.system.dao.TsDictTypeDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 字典类型 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:25:14
 */
@Service
public class TsDictTypeServiceImpl extends SystemBaseServiceImpl<TsDictTypeBO,String> implements TsDictTypeService {



	@Autowired
	private TsDictService tsDictService;





	@Autowired
	private TsDictTypeDAO tsDictTypeDAO ;



	@Override
	public BaseDAO getDAO() {
		return tsDictTypeDAO ;
	}







	@Override
	public void isExist(TsDictTypeBO tsDictTypeBO) {

		TsDictTypeBO ckBO ;
		TsDictTypeBO temp = null ;

		ckBO = new TsDictTypeBO();
		ckBO.setId( tsDictTypeBO.getId() );
		ckBO.setDictTypeName(tsDictTypeBO.getDictTypeName());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"字典类型名称已经使用");
		}

		ckBO = new TsDictTypeBO();
		ckBO.setId( tsDictTypeBO.getId() );
		ckBO.setDictTypeCode(tsDictTypeBO.getDictTypeCode());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"字典类型编码已经使用");
		}


	}


}