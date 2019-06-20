package com.zzboot.system.query;


import com.zzboot.framework.core.db.mybatis.query.Query;
import com.zzboot.system.domain.TsRoleEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;

/**
 * 角色 查询抽象类
 *
 * @author Administrator
 * @date 2019-4-11 14:06:16
 */
public interface TsRoleQuery<PK extends Serializable> extends Query<TsRoleEntity,PK> {

        public TsRoleQuery id(PK id);
        public TsRoleQuery idNot(PK idNot);
        public TsRoleQuery idIn(PK idIn);
        public TsRoleQuery idNotIn(PK idNotIn);
        public TsRoleQuery idIsNull();
        public TsRoleQuery idIsNotNull();





        public TsRoleQuery roleName(String roleName);
        public TsRoleQuery roleNameNot(String roleNameNot);
        public TsRoleQuery roleNameIn(String roleNameIn);
        public TsRoleQuery roleNameNotIn(String roleNameNotIn);
        public TsRoleQuery roleNameLike(String roleNameLike);
        public TsRoleQuery roleNameNotLike(String roleNameNotLike);
        public TsRoleQuery roleNameIsNull();
        public TsRoleQuery roleNameIsNotNull();





        public TsRoleQuery roleCode(String roleCode);
        public TsRoleQuery roleCodeNot(String roleCodeNot);
        public TsRoleQuery roleCodeIn(String roleCodeIn);
        public TsRoleQuery roleCodeNotIn(String roleCodeNotIn);
        public TsRoleQuery roleCodeLike(String roleCodeLike);
        public TsRoleQuery roleCodeNotLike(String roleCodeNotLike);
        public TsRoleQuery roleCodeIsNull();
        public TsRoleQuery roleCodeIsNotNull();





        public TsRoleQuery roleType(String roleType);
        public TsRoleQuery roleTypeNot(String roleTypeNot);
        public TsRoleQuery roleTypeIn(String roleTypeIn);
        public TsRoleQuery roleTypeNotIn(String roleTypeNotIn);
        public TsRoleQuery roleTypeIsNull();
        public TsRoleQuery roleTypeIsNotNull();





        public TsRoleQuery depId(PK depId);
        public TsRoleQuery depIdNot(PK depIdNot);
        public TsRoleQuery depIdIn(PK depIdIn);
        public TsRoleQuery depIdNotIn(PK depIdNotIn);
        public TsRoleQuery depIdIsNull();
        public TsRoleQuery depIdIsNotNull();





        public TsRoleQuery organId(String organId);
        public TsRoleQuery organIdNot(String organIdNot);
        public TsRoleQuery organIdIn(String organIdIn);
        public TsRoleQuery organIdNotIn(String organIdNotIn);
        public TsRoleQuery organIdLike(String organIdLike);
        public TsRoleQuery organIdNotLike(String organIdNotLike);
        public TsRoleQuery organIdIsNull();
        public TsRoleQuery organIdIsNotNull();





        public TsRoleQuery roleStatus(String roleStatus);
        public TsRoleQuery roleStatusNot(String roleStatusNot);
        public TsRoleQuery roleStatusIn(String roleStatusIn);
        public TsRoleQuery roleStatusNotIn(String roleStatusNotIn);
        public TsRoleQuery roleStatusIsNull();
        public TsRoleQuery roleStatusIsNotNull();





        public TsRoleQuery remark(String remark);
        public TsRoleQuery remarkNot(String remarkNot);
        public TsRoleQuery remarkIn(String remarkIn);
        public TsRoleQuery remarkNotIn(String remarkNotIn);
        public TsRoleQuery remarkLike(String remarkLike);
        public TsRoleQuery remarkNotLike(String remarkNotLike);
        public TsRoleQuery remarkIsNull();
        public TsRoleQuery remarkIsNotNull();





        public TsRoleQuery createUserId(String createUserId);
        public TsRoleQuery createUserIdNot(String createUserIdNot);
        public TsRoleQuery createUserIdIn(String createUserIdIn);
        public TsRoleQuery createUserIdNotIn(String createUserIdNotIn);
        public TsRoleQuery createUserIdLike(String createUserIdLike);
        public TsRoleQuery createUserIdNotLike(String createUserIdNotLike);
        public TsRoleQuery createUserIdIsNull();
        public TsRoleQuery createUserIdIsNotNull();





        public TsRoleQuery createUserName(String createUserName);
        public TsRoleQuery createUserNameNot(String createUserNameNot);
        public TsRoleQuery createUserNameIn(String createUserNameIn);
        public TsRoleQuery createUserNameNotIn(String createUserNameNotIn);
        public TsRoleQuery createUserNameLike(String createUserNameLike);
        public TsRoleQuery createUserNameNotLike(String createUserNameNotLike);
        public TsRoleQuery createUserNameIsNull();
        public TsRoleQuery createUserNameIsNotNull();





        public TsRoleQuery createTime(Timestamp createTime);
        public TsRoleQuery createTimeNot(Timestamp createTimeNot);
        public TsRoleQuery createTimeGreaterThan(Timestamp createTimeGreaterThan);
        public TsRoleQuery createTimeGreaterEqual(Timestamp createTimeGreaterEqual);
        public TsRoleQuery createTimeLessThan(Timestamp createTimeLessThan);
        public TsRoleQuery createTimeLessEqual(Timestamp createTimeLessEqual);





        public TsRoleQuery updateUserId(String updateUserId);
        public TsRoleQuery updateUserIdNot(String updateUserIdNot);
        public TsRoleQuery updateUserIdIn(String updateUserIdIn);
        public TsRoleQuery updateUserIdNotIn(String updateUserIdNotIn);
        public TsRoleQuery updateUserIdLike(String updateUserIdLike);
        public TsRoleQuery updateUserIdNotLike(String updateUserIdNotLike);
        public TsRoleQuery updateUserIdIsNull();
        public TsRoleQuery updateUserIdIsNotNull();





        public TsRoleQuery updateUserName(String updateUserName);
        public TsRoleQuery updateUserNameNot(String updateUserNameNot);
        public TsRoleQuery updateUserNameIn(String updateUserNameIn);
        public TsRoleQuery updateUserNameNotIn(String updateUserNameNotIn);
        public TsRoleQuery updateUserNameLike(String updateUserNameLike);
        public TsRoleQuery updateUserNameNotLike(String updateUserNameNotLike);
        public TsRoleQuery updateUserNameIsNull();
        public TsRoleQuery updateUserNameIsNotNull();





        public TsRoleQuery updateTime(Timestamp updateTime);
        public TsRoleQuery updateTimeNot(Timestamp updateTimeNot);
        public TsRoleQuery updateTimeGreaterThan(Timestamp updateTimeGreaterThan);
        public TsRoleQuery updateTimeGreaterEqual(Timestamp updateTimeGreaterEqual);
        public TsRoleQuery updateTimeLessThan(Timestamp updateTimeLessThan);
        public TsRoleQuery updateTimeLessEqual(Timestamp updateTimeLessEqual);





        public TsRoleQuery versionNo(Integer versionNo);
        public TsRoleQuery versionNoNot(Integer versionNoNot);
        public TsRoleQuery versionNoGreaterThan(Integer versionNoGreaterThan);
        public TsRoleQuery versionNoGreaterEqual(Integer versionNoGreaterEqual);
        public TsRoleQuery versionNoLessThan(Integer versionNoLessThan);
        public TsRoleQuery versionNoLessEqual(Integer versionNoLessEqual);





        public TsRoleQuery tenantId(String tenantId);
        public TsRoleQuery tenantIdNot(String tenantIdNot);
        public TsRoleQuery tenantIdIn(String tenantIdIn);
        public TsRoleQuery tenantIdNotIn(String tenantIdNotIn);
        public TsRoleQuery tenantIdLike(String tenantIdLike);
        public TsRoleQuery tenantIdNotLike(String tenantIdNotLike);
        public TsRoleQuery tenantIdIsNull();
        public TsRoleQuery tenantIdIsNotNull();






	
}