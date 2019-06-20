package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.system.domain.TsTenantEntity;


import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;



/**
* 企业 BO , 扩展 TsTenantEntity 对象
* @author Administrator
* @date 2019-4-10 11:08:53
*/
@EntityAnnotation(value="企业" , resource = "system.tenant"  ,businessName = "tenant_name"    ,businessKey = { "tenant_code" }    )
@TableName(value="ts_tenant" , resultMap = "TsTenantResultMap")
public class TsTenantBO extends TsTenantEntity implements Serializable , IBoEntity {









    public boolean isTable() {

        return true;


    }


    @Override
    public String toString() {


            return this.getTenantName();
        
    }
}
