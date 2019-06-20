package com.zzboot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.IRelevanceEntity;
import com.zzboot.util.config.annotaions.*;


import java.lang.String;

/**
 * 用户角色关联 实体类
 * @author Administrator
 * @date 2019-4-10 11:08:54
 */
public class TsUserRoleEntity extends BaseEntity<String> implements java.io.Serializable , IRelevanceEntity {

    @TableField(exist=false)
    private static final long serialVersionUID = 1L;



    @EntityAttrFkAnnotation(group = "userId",  groupName = "用户" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsUserBO.class)
    @EntityAttrDBAnnotation(attrName="用户" ,attrColumn="user_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "用户",sort = 200  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
    
	private String  userId ;



    @EntityAttrFkAnnotation(group = "roleId",  groupName = "角色" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsRoleBO.class)
    @EntityAttrDBAnnotation(attrName="角色" ,attrColumn="role_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "角色",sort = 300  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
    
	private String  roleId ;





	public void setUserId(String userId){
		this.userId = userId;
	}

    public String getUserId(){
    	return this.userId;
    }


	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

    public String getRoleId(){
    	return this.roleId;
    }


	
}