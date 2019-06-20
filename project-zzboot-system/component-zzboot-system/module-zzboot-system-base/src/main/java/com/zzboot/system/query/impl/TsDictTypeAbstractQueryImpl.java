package com.zzboot.system.query.impl;


import com.zzboot.framework.core.db.mybatis.query.CommonQueryImpl;
import com.zzboot.system.domain.TsDictTypeEntity;

import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
 * 字典类型 查询抽象类
 * 用于链式查询
 * @author Administrator
 * @date 2019-4-11 14:06:17
 */
public abstract class TsDictTypeAbstractQueryImpl<PK extends Serializable> extends CommonQueryImpl<TsDictTypeEntity,PK> {

            protected PK id;
            protected PK id_NE;

            protected PK pid;
            protected PK pid_NE;

            protected String dictTypeCode;
            protected String dictTypeCode_NE;
            protected String dictTypeCode_LIKE;
            protected String dictTypeCode_NOTLIKE;

            protected String dictTypeName;
            protected String dictTypeName_NE;
            protected String dictTypeName_LIKE;
            protected String dictTypeName_NOTLIKE;

            protected String dictTypeShowable;
            protected String dictTypeShowable_NE;

            protected String dictAddable;
            protected String dictAddable_NE;

            protected String dictUpdate;
            protected String dictUpdate_NE;

            protected String dictDelete;
            protected String dictDelete_NE;

        protected Integer orderby;
        protected Integer orderby_NE;
        protected Integer orderby_GT;
        protected Integer orderby_GE;
        protected Integer orderby_LT;
        protected Integer orderby_LE;


	
}