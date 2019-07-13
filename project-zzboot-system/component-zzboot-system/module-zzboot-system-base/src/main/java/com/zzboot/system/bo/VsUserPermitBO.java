package com.zzboot.system.bo;


import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.framework.core.db.entity.ILoginPermitEntity;
import com.zzboot.system.domain.VsUserPermitEntity;



import com.zzboot.util.config.annotaions.*;

import com.baomidou.mybatisplus.annotation.TableName;




import java.io.Serializable;




/**
* 用户许可 BO , 扩展 VsUserPermitEntity 对象
* @author Administrator
* @date 2019-4-10 20:58:06
*/
@EntityAnnotation(value="用户许可" , resource = "system.userpermit"  ,businessName = ""    ,businessKey = { "" }    )
@TableName(value="vs_user_permit" , resultMap = "VsUserPermitResultMap")
public class VsUserPermitBO extends VsUserPermitEntity implements Serializable , IBoEntity, ILoginPermitEntity<String> {


    public boolean isTable() {
        return false;

    }


    @Override
    public String toString() {


        return super.toString();

    }

    @Override
    public String getPermissionName() {
        return this.getPermitName();
    }

    @Override
    public String getPermissionCode() {
        return this.getPermitCode();
    }
}
