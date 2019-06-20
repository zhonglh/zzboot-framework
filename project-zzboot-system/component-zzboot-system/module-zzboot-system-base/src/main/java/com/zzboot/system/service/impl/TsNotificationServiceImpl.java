package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.TsNotificationBO;
import com.zzboot.system.dao.TsNotificationDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统通知 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:25:11
 */
@Service
public class TsNotificationServiceImpl extends SystemBaseServiceImpl<TsNotificationBO,String> implements TsNotificationService {



	@Autowired
	private TsDictService tsDictService;





	@Autowired
	private TsNotificationDAO tsNotificationDAO ;



	@Override
	public BaseDAO getDAO() {
		return tsNotificationDAO ;
	}



	@Override
	public List<Map<String,Object>> getUserNotReadCount(String[] userIds){
		if(userIds == null || userIds.length == 0){
			return null;
		}
		return this.tsNotificationDAO.getUserNotReadCount(userIds);
	}





	@Override
	public void isExist(TsNotificationBO tsNotificationBO) {
	}


}