package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.system.bo.VsMenuPermitBO;
import com.zzboot.system.dao.VsMenuPermitDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.VsMenuPermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单许可关联 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:45:43
 */
@Service
public class VsMenuPermitServiceImpl extends SystemBaseServiceImpl<VsMenuPermitBO,String> implements VsMenuPermitService {



	@Autowired
	private TsDictService tsDictService;





	@Autowired
	private VsMenuPermitDAO vsMenuPermitDAO ;



	@Override
	public BaseDAO getDAO() {
		return vsMenuPermitDAO ;
	}







	@Override
	public void isExist(VsMenuPermitBO vsMenuPermitBO) {

		VsMenuPermitBO ckBO ;
		VsMenuPermitBO temp = null ;

		ckBO = new VsMenuPermitBO();
		ckBO.setId( vsMenuPermitBO.getId() );
		ckBO.setMenuId(vsMenuPermitBO.getMenuId());
		ckBO.setPermitId(vsMenuPermitBO.getPermitId());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"    ");
		}


	}


}