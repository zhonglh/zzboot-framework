package com.zzboot.framework.shiro;

import com.zzboot.framework.core.db.entity.ILoginPermitEntity;
import com.zzboot.framework.core.db.entity.ILoginRoleEntity;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息接口
 * @author Administrator
 */
public interface ILoginUserService<PK extends Serializable> {

    /**
     * 通过登录名获取用户信息
     * @param loginName
     * @return
     */
    public ILoginUserEntity getUserEntityByLoginName(String loginName);

    /**
     * 获取用户所有的角色信息
     * @param loginUserEntity
     * @return
     */
    public List<ILoginRoleEntity> getUserRoleInfo(ILoginUserEntity loginUserEntity) ;


    /**
     * 获取用户所有的许可信息
     * @param loginUserEntity
     * @return
     */
    public List<ILoginPermitEntity> getUserPermitInfo(ILoginUserEntity loginUserEntity) ;

}
