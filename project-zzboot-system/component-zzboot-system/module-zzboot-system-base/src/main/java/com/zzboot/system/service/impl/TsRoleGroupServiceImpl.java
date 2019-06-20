package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.service.impl.BaseGroupServiceImpl;
import com.zzboot.framework.core.db.base.service.BaseService;


import com.zzboot.system.bo.TsRoleGroupBO;
import com.zzboot.system.service.TsRoleGroupService;

import com.zzboot.system.service.TsRolePermitService;

import com.zzboot.system.service.TsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:45:41
 */
@Service
public class TsRoleGroupServiceImpl extends BaseGroupServiceImpl<TsRoleGroupBO,String> implements TsRoleGroupService {

    @Autowired
    private TsRoleService roleService;


    @Autowired
    private TsRolePermitService rolePermitService;


    private BaseService[] bss = null;

    /**
     * 此数组需要和 TsRoleGroupBO 对象对应
     * @return
     */
    @Override
    public BaseService[] getServices() {
        if(bss == null) {
            bss = new BaseService[]{roleService,rolePermitService};
        }
        return bss;
    }



}