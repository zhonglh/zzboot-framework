package com.zzboot.system.query.impl;


import com.zzboot.framework.core.db.mybatis.query.CommonQueryImpl;
import com.zzboot.system.domain.TsRolePermitEntity;

import java.io.Serializable;
import java.lang.String;

/**
 * 角色许可关联 查询抽象类
 * 用于链式查询
 * @author Administrator
 * @date 2019-4-11 14:06:16
 */
public abstract class TsRolePermitAbstractQueryImpl<PK extends Serializable> extends CommonQueryImpl<TsRolePermitEntity,PK> {

            protected PK id;
            protected PK id_NE;

            protected PK roleId;
            protected PK roleId_NE;

            protected PK permitId;
            protected PK permitId_NE;


	
}