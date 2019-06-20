package com.zzboot.system.logic;

import com.zzboot.system.bo.TsRoleBO;
import com.zzboot.system.bo.TsUserRoleBO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RbacLogic {
    //todo 数据权限处理
    public void checkData(){
        /*if(BusinessConfig.USE_TENANT && BusinessConfig.IS_CHECK_Tenant){
            if(!userBO.getTenantId().equals(sessionUserVO.getId())){
                throw EnumErrorMsg.no_auth.toException();
            }

            if(ms != null && !ms.isEmpty()){
                List<String> roleIds = new ArrayList<>();
                for(TsUserRoleBO userRoleBO : ms){
                    userRoleBO.setUserId(m.getUserId());
                    roleIds.add(userRoleBO.getRoleId());
                }
                Collection<TsRoleBO> roles = this.tsRoleService.listByIds(roleIds);
                for(TsRoleBO role : roles){
                    if(!role.getTenantId().equals(sessionUserVO.getId())){
                        throw EnumErrorMsg.no_auth.toException();
                    }
                }
            }
        }*/
    }

}
