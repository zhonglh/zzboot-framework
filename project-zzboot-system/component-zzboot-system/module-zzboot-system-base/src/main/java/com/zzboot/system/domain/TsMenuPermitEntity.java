package com.zzboot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.IRelevanceEntity;
import com.zzboot.util.config.annotaions.*;

import java.lang.String;
/**
 * 菜单许可关联 实体类
 * @author Administrator
 * @date 2019-4-10 11:08:52
 */
public class TsMenuPermitEntity extends BaseEntity<String> implements java.io.Serializable , IRelevanceEntity {

    @TableField(exist=false)
    private static final long serialVersionUID = 1L;



    @EntityAttrFkAnnotation(group = "menuId",  groupName = "菜单" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsMenuBO.class)
    @EntityAttrDBAnnotation(attrName="菜单" ,attrColumn="menu_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "菜单",sort = 200  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
    
	private String  menuId ;



    @EntityAttrFkAnnotation(group = "permitId",  groupName = "许可" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsPermitBO.class)
    @EntityAttrDBAnnotation(attrName="许可" ,attrColumn="permit_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "许可",sort = 300  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
    
	private String  permitId ;





	public void setMenuId(String menuId){
		this.menuId = menuId;
	}

    public String getMenuId(){
    	return this.menuId;
    }


	public void setPermitId(String permitId){
		this.permitId = permitId;
	}

    public String getPermitId(){
    	return this.permitId;
    }


	
}