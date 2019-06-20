package com.zzboot.system.query.impl;


import com.zzboot.framework.core.db.mybatis.query.CommonQueryImpl;
import com.zzboot.system.domain.TsMsgTempletEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;

/**
 * 消息模板 查询抽象类
 * 用于链式查询
 * @author Administrator
 * @date 2019-4-11 14:06:18
 */
public abstract class TsMsgTempletAbstractQueryImpl<PK extends Serializable> extends CommonQueryImpl<TsMsgTempletEntity,PK> {

            protected PK id;
            protected PK id_NE;

            protected String msgTempletName;
            protected String msgTempletName_NE;
            protected String msgTempletName_LIKE;
            protected String msgTempletName_NOTLIKE;

            protected String msgTempletType;
            protected String msgTempletType_NE;

            protected String msgTitle;
            protected String msgTitle_NE;
            protected String msgTitle_LIKE;
            protected String msgTitle_NOTLIKE;

            protected String msgContent;
            protected String msgContent_NE;
            protected String msgContent_LIKE;
            protected String msgContent_NOTLIKE;

            protected String msgLanguage;
            protected String msgLanguage_NE;

            protected String templetEffective;
            protected String templetEffective_NE;

            protected PK depId;
            protected PK depId_NE;

            protected String organId;
            protected String organId_NE;
            protected String organId_LIKE;
            protected String organId_NOTLIKE;

            protected String createUserId;
            protected String createUserId_NE;
            protected String createUserId_LIKE;
            protected String createUserId_NOTLIKE;

            protected String createUserName;
            protected String createUserName_NE;
            protected String createUserName_LIKE;
            protected String createUserName_NOTLIKE;

        protected Timestamp createTime;
        protected Timestamp createTime_NE;
        protected Timestamp createTime_GT;
        protected Timestamp createTime_GE;
        protected Timestamp createTime_LT;
        protected Timestamp createTime_LE;

            protected String updateUserId;
            protected String updateUserId_NE;
            protected String updateUserId_LIKE;
            protected String updateUserId_NOTLIKE;

            protected String updateUserName;
            protected String updateUserName_NE;
            protected String updateUserName_LIKE;
            protected String updateUserName_NOTLIKE;

        protected Timestamp updateTime;
        protected Timestamp updateTime_NE;
        protected Timestamp updateTime_GT;
        protected Timestamp updateTime_GE;
        protected Timestamp updateTime_LT;
        protected Timestamp updateTime_LE;

        protected Integer versionNo;
        protected Integer versionNo_NE;
        protected Integer versionNo_GT;
        protected Integer versionNo_GE;
        protected Integer versionNo_LT;
        protected Integer versionNo_LE;

            protected String tenantId;
            protected String tenantId_NE;
            protected String tenantId_LIKE;
            protected String tenantId_NOTLIKE;


	
}