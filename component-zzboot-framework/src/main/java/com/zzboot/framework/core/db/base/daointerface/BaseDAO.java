package com.zzboot.framework.core.db.base.daointerface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzboot.framework.core.db.entity.BaseEntity;

import java.io.Serializable;

/**
 * DAO基础接口
 * @author Administrator
 */
public interface BaseDAO<T extends BaseEntity, PK extends Serializable> extends BaseMapper<T> {

    public T selectCheck(T t) ;
}
