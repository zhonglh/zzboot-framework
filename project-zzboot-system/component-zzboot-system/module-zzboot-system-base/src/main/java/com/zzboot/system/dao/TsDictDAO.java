package com.zzboot.system.dao;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.TsDictBO;
import org.springframework.stereotype.Repository;

/**
* 字典信息 操作DAO
* @author Administrator
* @date 2019-4-10 11:08:53
*/
@Repository
public interface TsDictDAO extends BaseDAO<TsDictBO , String> {

}
