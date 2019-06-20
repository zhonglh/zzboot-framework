package com.zzboot.system.query;


import com.zzboot.framework.core.db.mybatis.query.Query;
import com.zzboot.system.domain.TsUserEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.lang.String;
import java.lang.Integer;

/**
 * 用户 查询抽象类
 *
 * @author Administrator
 * @date 2019-4-11 14:06:19
 */
public interface TsUserQuery<PK extends Serializable> extends Query<TsUserEntity,PK> {

        public TsUserQuery id(PK id);
        public TsUserQuery idNot(PK idNot);
        public TsUserQuery idIn(PK idIn);
        public TsUserQuery idNotIn(PK idNotIn);
        public TsUserQuery idIsNull();
        public TsUserQuery idIsNotNull();





        public TsUserQuery userName(String userName);
        public TsUserQuery userNameNot(String userNameNot);
        public TsUserQuery userNameIn(String userNameIn);
        public TsUserQuery userNameNotIn(String userNameNotIn);
        public TsUserQuery userNameLike(String userNameLike);
        public TsUserQuery userNameNotLike(String userNameNotLike);
        public TsUserQuery userNameIsNull();
        public TsUserQuery userNameIsNotNull();





        public TsUserQuery loginName(String loginName);
        public TsUserQuery loginNameNot(String loginNameNot);
        public TsUserQuery loginNameIn(String loginNameIn);
        public TsUserQuery loginNameNotIn(String loginNameNotIn);
        public TsUserQuery loginNameLike(String loginNameLike);
        public TsUserQuery loginNameNotLike(String loginNameNotLike);
        public TsUserQuery loginNameIsNull();
        public TsUserQuery loginNameIsNotNull();





        public TsUserQuery loginPassword(String loginPassword);
        public TsUserQuery loginPasswordNot(String loginPasswordNot);
        public TsUserQuery loginPasswordIn(String loginPasswordIn);
        public TsUserQuery loginPasswordNotIn(String loginPasswordNotIn);
        public TsUserQuery loginPasswordLike(String loginPasswordLike);
        public TsUserQuery loginPasswordNotLike(String loginPasswordNotLike);
        public TsUserQuery loginPasswordIsNull();
        public TsUserQuery loginPasswordIsNotNull();





        public TsUserQuery salt(String salt);
        public TsUserQuery saltNot(String saltNot);
        public TsUserQuery saltIn(String saltIn);
        public TsUserQuery saltNotIn(String saltNotIn);
        public TsUserQuery saltLike(String saltLike);
        public TsUserQuery saltNotLike(String saltNotLike);
        public TsUserQuery saltIsNull();
        public TsUserQuery saltIsNotNull();





        public TsUserQuery userStatus(String userStatus);
        public TsUserQuery userStatusNot(String userStatusNot);
        public TsUserQuery userStatusIn(String userStatusIn);
        public TsUserQuery userStatusNotIn(String userStatusNotIn);
        public TsUserQuery userStatusIsNull();
        public TsUserQuery userStatusIsNotNull();





        public TsUserQuery leadUserId(PK leadUserId);
        public TsUserQuery leadUserIdNot(PK leadUserIdNot);
        public TsUserQuery leadUserIdIn(PK leadUserIdIn);
        public TsUserQuery leadUserIdNotIn(PK leadUserIdNotIn);
        public TsUserQuery leadUserIdIsNull();
        public TsUserQuery leadUserIdIsNotNull();





        public TsUserQuery phone(String phone);
        public TsUserQuery phoneNot(String phoneNot);
        public TsUserQuery phoneIn(String phoneIn);
        public TsUserQuery phoneNotIn(String phoneNotIn);
        public TsUserQuery phoneLike(String phoneLike);
        public TsUserQuery phoneNotLike(String phoneNotLike);
        public TsUserQuery phoneIsNull();
        public TsUserQuery phoneIsNotNull();





        public TsUserQuery email(String email);
        public TsUserQuery emailNot(String emailNot);
        public TsUserQuery emailIn(String emailIn);
        public TsUserQuery emailNotIn(String emailNotIn);
        public TsUserQuery emailLike(String emailLike);
        public TsUserQuery emailNotLike(String emailNotLike);
        public TsUserQuery emailIsNull();
        public TsUserQuery emailIsNotNull();





        public TsUserQuery avatarImage(String avatarImage);
        public TsUserQuery avatarImageNot(String avatarImageNot);
        public TsUserQuery avatarImageIn(String avatarImageIn);
        public TsUserQuery avatarImageNotIn(String avatarImageNotIn);
        public TsUserQuery avatarImageLike(String avatarImageLike);
        public TsUserQuery avatarImageNotLike(String avatarImageNotLike);
        public TsUserQuery avatarImageIsNull();
        public TsUserQuery avatarImageIsNotNull();





        public TsUserQuery openId(String openId);
        public TsUserQuery openIdNot(String openIdNot);
        public TsUserQuery openIdIn(String openIdIn);
        public TsUserQuery openIdNotIn(String openIdNotIn);
        public TsUserQuery openIdLike(String openIdLike);
        public TsUserQuery openIdNotLike(String openIdNotLike);
        public TsUserQuery openIdIsNull();
        public TsUserQuery openIdIsNotNull();





        public TsUserQuery unionId(String unionId);
        public TsUserQuery unionIdNot(String unionIdNot);
        public TsUserQuery unionIdIn(String unionIdIn);
        public TsUserQuery unionIdNotIn(String unionIdNotIn);
        public TsUserQuery unionIdLike(String unionIdLike);
        public TsUserQuery unionIdNotLike(String unionIdNotLike);
        public TsUserQuery unionIdIsNull();
        public TsUserQuery unionIdIsNotNull();





        public TsUserQuery systemAdmin(String systemAdmin);
        public TsUserQuery systemAdminNot(String systemAdminNot);
        public TsUserQuery systemAdminIn(String systemAdminIn);
        public TsUserQuery systemAdminNotIn(String systemAdminNotIn);
        public TsUserQuery systemAdminIsNull();
        public TsUserQuery systemAdminIsNotNull();





        public TsUserQuery depId(PK depId);
        public TsUserQuery depIdNot(PK depIdNot);
        public TsUserQuery depIdIn(PK depIdIn);
        public TsUserQuery depIdNotIn(PK depIdNotIn);
        public TsUserQuery depIdIsNull();
        public TsUserQuery depIdIsNotNull();





        public TsUserQuery organId(String organId);
        public TsUserQuery organIdNot(String organIdNot);
        public TsUserQuery organIdIn(String organIdIn);
        public TsUserQuery organIdNotIn(String organIdNotIn);
        public TsUserQuery organIdLike(String organIdLike);
        public TsUserQuery organIdNotLike(String organIdNotLike);
        public TsUserQuery organIdIsNull();
        public TsUserQuery organIdIsNotNull();





        public TsUserQuery pageLimit(Integer pageLimit);
        public TsUserQuery pageLimitNot(Integer pageLimitNot);
        public TsUserQuery pageLimitGreaterThan(Integer pageLimitGreaterThan);
        public TsUserQuery pageLimitGreaterEqual(Integer pageLimitGreaterEqual);
        public TsUserQuery pageLimitLessThan(Integer pageLimitLessThan);
        public TsUserQuery pageLimitLessEqual(Integer pageLimitLessEqual);





        public TsUserQuery remark(String remark);
        public TsUserQuery remarkNot(String remarkNot);
        public TsUserQuery remarkIn(String remarkIn);
        public TsUserQuery remarkNotIn(String remarkNotIn);
        public TsUserQuery remarkLike(String remarkLike);
        public TsUserQuery remarkNotLike(String remarkNotLike);
        public TsUserQuery remarkIsNull();
        public TsUserQuery remarkIsNotNull();





        public TsUserQuery deleteFlag(String deleteFlag);
        public TsUserQuery deleteFlagNot(String deleteFlagNot);
        public TsUserQuery deleteFlagIn(String deleteFlagIn);
        public TsUserQuery deleteFlagNotIn(String deleteFlagNotIn);
        public TsUserQuery deleteFlagLike(String deleteFlagLike);
        public TsUserQuery deleteFlagNotLike(String deleteFlagNotLike);
        public TsUserQuery deleteFlagIsNull();
        public TsUserQuery deleteFlagIsNotNull();





        public TsUserQuery createUserId(String createUserId);
        public TsUserQuery createUserIdNot(String createUserIdNot);
        public TsUserQuery createUserIdIn(String createUserIdIn);
        public TsUserQuery createUserIdNotIn(String createUserIdNotIn);
        public TsUserQuery createUserIdLike(String createUserIdLike);
        public TsUserQuery createUserIdNotLike(String createUserIdNotLike);
        public TsUserQuery createUserIdIsNull();
        public TsUserQuery createUserIdIsNotNull();





        public TsUserQuery createUserName(String createUserName);
        public TsUserQuery createUserNameNot(String createUserNameNot);
        public TsUserQuery createUserNameIn(String createUserNameIn);
        public TsUserQuery createUserNameNotIn(String createUserNameNotIn);
        public TsUserQuery createUserNameLike(String createUserNameLike);
        public TsUserQuery createUserNameNotLike(String createUserNameNotLike);
        public TsUserQuery createUserNameIsNull();
        public TsUserQuery createUserNameIsNotNull();





        public TsUserQuery createTime(Timestamp createTime);
        public TsUserQuery createTimeNot(Timestamp createTimeNot);
        public TsUserQuery createTimeGreaterThan(Timestamp createTimeGreaterThan);
        public TsUserQuery createTimeGreaterEqual(Timestamp createTimeGreaterEqual);
        public TsUserQuery createTimeLessThan(Timestamp createTimeLessThan);
        public TsUserQuery createTimeLessEqual(Timestamp createTimeLessEqual);





        public TsUserQuery updateUserId(String updateUserId);
        public TsUserQuery updateUserIdNot(String updateUserIdNot);
        public TsUserQuery updateUserIdIn(String updateUserIdIn);
        public TsUserQuery updateUserIdNotIn(String updateUserIdNotIn);
        public TsUserQuery updateUserIdLike(String updateUserIdLike);
        public TsUserQuery updateUserIdNotLike(String updateUserIdNotLike);
        public TsUserQuery updateUserIdIsNull();
        public TsUserQuery updateUserIdIsNotNull();





        public TsUserQuery updateUserName(String updateUserName);
        public TsUserQuery updateUserNameNot(String updateUserNameNot);
        public TsUserQuery updateUserNameIn(String updateUserNameIn);
        public TsUserQuery updateUserNameNotIn(String updateUserNameNotIn);
        public TsUserQuery updateUserNameLike(String updateUserNameLike);
        public TsUserQuery updateUserNameNotLike(String updateUserNameNotLike);
        public TsUserQuery updateUserNameIsNull();
        public TsUserQuery updateUserNameIsNotNull();





        public TsUserQuery updateTime(Timestamp updateTime);
        public TsUserQuery updateTimeNot(Timestamp updateTimeNot);
        public TsUserQuery updateTimeGreaterThan(Timestamp updateTimeGreaterThan);
        public TsUserQuery updateTimeGreaterEqual(Timestamp updateTimeGreaterEqual);
        public TsUserQuery updateTimeLessThan(Timestamp updateTimeLessThan);
        public TsUserQuery updateTimeLessEqual(Timestamp updateTimeLessEqual);





        public TsUserQuery versionNo(Integer versionNo);
        public TsUserQuery versionNoNot(Integer versionNoNot);
        public TsUserQuery versionNoGreaterThan(Integer versionNoGreaterThan);
        public TsUserQuery versionNoGreaterEqual(Integer versionNoGreaterEqual);
        public TsUserQuery versionNoLessThan(Integer versionNoLessThan);
        public TsUserQuery versionNoLessEqual(Integer versionNoLessEqual);





        public TsUserQuery tenantId(String tenantId);
        public TsUserQuery tenantIdNot(String tenantIdNot);
        public TsUserQuery tenantIdIn(String tenantIdIn);
        public TsUserQuery tenantIdNotIn(String tenantIdNotIn);
        public TsUserQuery tenantIdLike(String tenantIdLike);
        public TsUserQuery tenantIdNotLike(String tenantIdNotLike);
        public TsUserQuery tenantIdIsNull();
        public TsUserQuery tenantIdIsNotNull();






	
}