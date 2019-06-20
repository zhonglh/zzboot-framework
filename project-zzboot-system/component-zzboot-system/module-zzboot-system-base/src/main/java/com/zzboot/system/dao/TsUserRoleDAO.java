package com.zzboot.system.dao;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.TsUserRoleBO;
import org.springframework.stereotype.Repository;

/**
* 用户角色关联 操作DAO
* @author Administrator
* @date 2019-4-10 11:08:54
*/
@Repository
public interface TsUserRoleDAO extends BaseDAO<TsUserRoleBO , String> {

}
