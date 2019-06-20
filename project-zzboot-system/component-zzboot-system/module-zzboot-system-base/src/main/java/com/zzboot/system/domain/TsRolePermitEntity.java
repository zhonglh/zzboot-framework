package com.zzboot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.IRelevanceEntity;
import com.zzboot.util.config.annotaions.*;

import java.lang.String;


/**
 * 角色许可关联 实体类
 * @author Administrator
 * @date 2019-4-10 11:08:53
 */
public class TsRolePermitEntity extends BaseEntity<String> implements java.io.Serializable , IRelevanceEntity {

    @TableField(exist=false)
    private static final long serialVersionUID = 1L;



    @EntityAttrFkAnnotation(group = "roleId",  groupName = "角色" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsRoleBO.class)
    @EntityAttrDBAnnotation(attrName="角色" ,attrColumn="role_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "角色",sort = 200  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
    
	private String  roleId ;



    @EntityAttrFkAnnotation(group = "permitId",  groupName = "许可" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsPermitBO.class)
    @EntityAttrDBAnnotation(attrName="许可" ,attrColumn="permit_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "许可",sort = 300  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
    
	private String  permitId ;





	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

    public String getRoleId(){
    	return this.roleId;
    }


	public void setPermitId(String permitId){
		this.permitId = permitId;
	}

    public String getPermitId(){
    	return this.permitId;
    }


	
}