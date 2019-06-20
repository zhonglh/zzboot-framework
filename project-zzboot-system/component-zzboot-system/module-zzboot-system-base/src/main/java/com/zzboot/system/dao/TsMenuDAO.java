package com.zzboot.system.dao;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.system.bo.TsMenuBO;
import org.springframework.stereotype.Repository;

/**
* 菜单 操作DAO
* @author Administrator
* @date 2019-4-10 11:08:55
*/
@Repository
public interface TsMenuDAO extends BaseDAO<TsMenuBO , String> {

}
