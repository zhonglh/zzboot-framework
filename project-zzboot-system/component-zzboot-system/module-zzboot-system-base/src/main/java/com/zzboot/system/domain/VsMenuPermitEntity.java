package com.zzboot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.util.config.annotaions.*;

import java.lang.String;


/**
 * 菜单许可关联 实体类
 * @author Administrator
 * @date 2019-4-10 20:58:07
 */
public class VsMenuPermitEntity extends BaseEntity<String> implements java.io.Serializable{

    @TableField(exist=false)
    private static final long serialVersionUID = 1L;







    @EntityAttrDBAnnotation(attrName="菜单" ,attrColumn="menu_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "菜单",sort = 200  , pageElement = "text"            , maxLength = 32        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
	private String  menuId ;



    @EntityAttrDBAnnotation(attrName="许可" ,attrColumn="permit_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "许可",sort = 300  , pageElement = "text"            , maxLength = 32        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
	private String  permitId ;



    @EntityAttrDBAnnotation(attrName="许可编号" ,attrColumn="permit_code"  , type = "VARCHAR"      ,  attrLength = 50 , notNull = true )
    @EntityAttrPageAnnotation(title = "许可编号",sort = 400  , pageElement = "text"            , maxLength = 50        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
	private String  permitCode ;



    @EntityAttrDBAnnotation(attrName="许可名称" ,attrColumn="permit_name"  , type = "VARCHAR"      ,  attrLength = 20 , notNull = true )
    @EntityAttrPageAnnotation(title = "许可名称",sort = 500  , pageElement = "text"            , maxLength = 20        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
	private String  permitName ;



    @EntityAttrDBAnnotation(attrName="菜单名称" ,attrColumn="menu_name"  , type = "VARCHAR"      ,  attrLength = 30 , notNull = true )
    @EntityAttrPageAnnotation(title = "菜单名称",sort = 600  , pageElement = "text"            , maxLength = 30        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
	private String  menuName ;



    @EntityAttrDBAnnotation(attrName="菜单编号" ,attrColumn="menu_code"  , type = "VARCHAR"      ,  attrLength = 50 , notNull = true )
    @EntityAttrPageAnnotation(title = "菜单编号",sort = 700  , pageElement = "text"            , maxLength = 50        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
	private String  menuCode ;





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


	public void setPermitCode(String permitCode){
		this.permitCode = permitCode;
	}

    public String getPermitCode(){
    	return this.permitCode;
    }


	public void setPermitName(String permitName){
		this.permitName = permitName;
	}

    public String getPermitName(){
    	return this.permitName;
    }


	public void setMenuName(String menuName){
		this.menuName = menuName;
	}

    public String getMenuName(){
    	return this.menuName;
    }


	public void setMenuCode(String menuCode){
		this.menuCode = menuCode;
	}

    public String getMenuCode(){
    	return this.menuCode;
    }



}