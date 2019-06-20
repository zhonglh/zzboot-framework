package com.zzboot.system.query.impl;


import com.zzboot.framework.core.db.mybatis.query.CommonQueryImpl;
import com.zzboot.system.domain.TsMenuPermitEntity;

import java.io.Serializable;
import java.lang.String;

/**
 * 菜单许可关联 查询抽象类
 * 用于链式查询
 * @author Administrator
 * @date 2019-4-11 14:06:15
 */
public abstract class TsMenuPermitAbstractQueryImpl<PK extends Serializable> extends CommonQueryImpl<TsMenuPermitEntity,PK> {

            protected PK id;
            protected PK id_NE;

            protected PK menuId;
            protected PK menuId_NE;

            protected PK permitId;
            protected PK permitId_NE;


	
}