package com.zzboot.system.dao;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.VsUserBO;
import org.springframework.stereotype.Repository;

/**
* 用户 操作DAO
* @author Administrator
* @date 2019-4-10 11:08:52
*/
@Repository
public interface VsUserDAO extends BaseDAO<VsUserBO , String> {

}
