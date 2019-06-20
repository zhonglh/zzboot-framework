package com.zzboot.system.query;


import com.zzboot.framework.core.db.mybatis.query.Query;
import com.zzboot.system.domain.TsUserRoleEntity;

import java.io.Serializable;
import java.lang.String;

/**
 * 用户角色关联 查询抽象类
 *
 * @author Administrator
 * @date 2019-4-11 14:06:17
 */
public interface TsUserRoleQuery<PK extends Serializable> extends Query<TsUserRoleEntity,PK> {

        public TsUserRoleQuery id(PK id);
        public TsUserRoleQuery idNot(PK idNot);
        public TsUserRoleQuery idIn(PK idIn);
        public TsUserRoleQuery idNotIn(PK idNotIn);
        public TsUserRoleQuery idIsNull();
        public TsUserRoleQuery idIsNotNull();





        public TsUserRoleQuery userId(PK userId);
        public TsUserRoleQuery userIdNot(PK userIdNot);
        public TsUserRoleQuery userIdIn(PK userIdIn);
        public TsUserRoleQuery userIdNotIn(PK userIdNotIn);
        public TsUserRoleQuery userIdIsNull();
        public TsUserRoleQuery userIdIsNotNull();





        public TsUserRoleQuery roleId(PK roleId);
        public TsUserRoleQuery roleIdNot(PK roleIdNot);
        public TsUserRoleQuery roleIdIn(PK roleIdIn);
        public TsUserRoleQuery roleIdNotIn(PK roleIdNotIn);
        public TsUserRoleQuery roleIdIsNull();
        public TsUserRoleQuery roleIdIsNotNull();






	
}