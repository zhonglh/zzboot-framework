package com.zzboot.system.dao;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.TsLoginLogBO;
import org.springframework.stereotype.Repository;

/**
* 登陆日志 操作DAO
* @author Administrator
* @date 2019-4-10 11:08:55
*/
@Repository
public interface TsLoginLogDAO extends BaseDAO<TsLoginLogBO , String> {

}
