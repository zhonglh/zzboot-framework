package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.system.domain.TsRolePermitEntity;

import com.baomidou.mybatisplus.annotation.TableField;



import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;



/**
* 角色许可关联 BO , 扩展 TsRolePermitEntity 对象
* @author Administrator
* @date 2019-4-10 11:08:53
*/
@EntityAnnotation(value="角色许可关联" , resource = "system.rolepermit"  ,businessName = ""    ,businessKey = { "" }    )
@TableName(value="ts_role_permit" , resultMap = "TsRolePermitResultMap")
public class TsRolePermitBO extends TsRolePermitEntity implements Serializable , IBoEntity {




    @TableField(exist = false)
    @EntityAttrFkAnnotation(group = "roleId",  groupName = "角色" ,   dbColumnName = "role_name" , dbColumnType = "VARCHAR" , dbColumnLength = 50   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsRoleBO.class)
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "角色",sort = 201                      ,required=true )
    private String roleName ;



    @TableField(exist = false)
    @EntityAttrFkAnnotation(group = "permitId",  groupName = "许可" ,   dbColumnName = "permit_name" , dbColumnType = "VARCHAR" , dbColumnLength = 20   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsPermitBO.class)
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "许可",sort = 301                      ,required=true )
    private String permitName ;



    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return this.roleName;
    }

    public void setPermitName(String permitName){
        this.permitName = permitName;
    }

    public String getPermitName(){
        return this.permitName;
    }






    public boolean isTable() {

        return true;


    }


    @Override
    public String toString() {


        return super.toString();

    }
}
