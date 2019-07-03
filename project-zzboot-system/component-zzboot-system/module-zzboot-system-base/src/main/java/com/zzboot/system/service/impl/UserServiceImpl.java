package com.zzboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzboot.framework.core.db.entity.ILoginPermitEntity;
import com.zzboot.framework.core.db.entity.ILoginRoleEntity;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumYesNo;
import com.zzboot.framework.shiro.ILoginUserService;
import com.zzboot.system.domain.TsUserEntity;
import com.zzboot.system.enums.EnumUserStatus;
import com.zzboot.system.query.TsPermitQuery;
import com.zzboot.system.query.TsUserQuery;
import com.zzboot.system.query.TsUserRoleQuery;
import com.zzboot.system.query.VsUserPermitQuery;
import com.zzboot.system.query.impl.TsPermitQueryImpl;
import com.zzboot.system.query.impl.TsUserQueryImpl;
import com.zzboot.system.query.impl.TsUserRoleQueryImpl;
import com.zzboot.system.query.impl.VsUserPermitQueryImpl;
import com.zzboot.system.service.TsPermitService;
import com.zzboot.system.service.TsUserRoleService;
import com.zzboot.system.service.TsUserService;
import com.zzboot.system.service.VsUserPermitService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录用户服务实现类
 * @author Administrator
 */
@Service
public class UserServiceImpl implements ILoginUserService<String> {


    @Autowired
    private TsUserService tsUserService ;


    @Autowired
    private TsPermitService tsPermitService ;


    @Autowired
    private TsUserRoleService tsUserRoleService ;


    @Autowired
    private VsUserPermitService vsUserPermitService;



    @Override
    public ILoginUserEntity getUserEntityByLoginName(String loginName) {

        TsUserQuery userQuery = new TsUserQueryImpl();
        userQuery.loginName(loginName);
        userQuery.email(loginName);
        userQuery.phone(loginName);
        ILoginUserEntity loginUserEntity =  tsUserService.getOne(userQuery.buildWrapper(true)  );
        if(loginUserEntity instanceof TsUserEntity) {
            TsUserEntity tsUserEntity = (TsUserEntity)loginUserEntity;
            if (StringUtils.isEmpty(tsUserEntity.getAvatarImage())) {
                tsUserEntity.setAvatarImage(tsUserEntity.getId());
            }
        }
        return loginUserEntity;

    }

    @Override
    public List<ILoginRoleEntity> getUserRoleInfo(ILoginUserEntity loginUserEntity) {
        TsUserRoleQuery query = new TsUserRoleQueryImpl();
        query.userId(loginUserEntity.getId());
        QueryWrapper qw = query.buildWrapper();
        return tsUserRoleService.list(qw);
    }

    @Override
    public List<ILoginPermitEntity> getUserPermitInfo(ILoginUserEntity loginUserEntity) {
        if(EnumYesNo.YES.getCode().equals(loginUserEntity.isSystemUser())) {
            //系统管理人查询所有的许可
            TsPermitQuery query = new TsPermitQueryImpl();
            query.deleteFlag(EnumYesNo.NO.getCode());
            QueryWrapper qw = query.buildWrapper();
            return (List<ILoginPermitEntity>)tsPermitService.list(qw);
        }else {
            VsUserPermitQuery query = new VsUserPermitQueryImpl();
            query.userId(loginUserEntity.getId());
            return (List<ILoginPermitEntity>)vsUserPermitService.list(query.buildWrapper());
        }
    }

    @Override
    public void checkUserStatus(ILoginUserEntity loginUserEntity) {

        if(EnumUserStatus.forbidden.getVal().equals(loginUserEntity.getUserStatus())){
            throw new LockedAccountException("帐号被禁用,请联系管理员!");
        }
    }
}



