package com.zzboot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.util.config.annotaions.*;

import java.lang.String;

/**
 * 用户许可 实体类
 * @author Administrator
 * @date 2019-4-10 20:58:06
 */
public class VsUserPermitEntity extends BaseEntity<String> implements java.io.Serializable{

    @TableField(exist=false)
    private static final long serialVersionUID = 1L;







    @EntityAttrDBAnnotation(attrName="PERMIT_CODE" ,attrColumn="permit_code"  , type = "VARCHAR"      ,  attrLength = 50 , notNull = true )
    @EntityAttrPageAnnotation(title = "PERMIT_CODE",sort = 200  , pageElement = "text"            , maxLength = 50        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "2")
    
    
	private String  permitCode ;



    @EntityAttrDBAnnotation(attrName="PERMIT_NAME" ,attrColumn="permit_name"  , type = "VARCHAR"      ,  attrLength = 20 , notNull = true )
    @EntityAttrPageAnnotation(title = "PERMIT_NAME",sort = 300  , pageElement = "text"            , maxLength = 20        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "2")
    
	private String  permitName ;



    @EntityAttrDBAnnotation(attrName="USER_NAME" ,attrColumn="user_name"  , type = "VARCHAR"      ,  attrLength = 50 , notNull = true )
    @EntityAttrPageAnnotation(title = "USER_NAME",sort = 400  , pageElement = "text"            , maxLength = 50        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "2")
    
	private String  userName ;



    @EntityAttrDBAnnotation(attrName="LOGIN_NAME" ,attrColumn="login_name"  , type = "VARCHAR"      ,  attrLength = 20 , notNull = true )
    @EntityAttrPageAnnotation(title = "LOGIN_NAME",sort = 500  , pageElement = "text"            , maxLength = 20        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "2")
    
	private String  loginName ;



    @EntityAttrFkAnnotation(group = "userId",  groupName = "USER_ID" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.VsUserBO.class)
    @EntityAttrDBAnnotation(attrName="USER_ID" ,attrColumn="user_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "USER_ID",sort = 600  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
	private String  userId ;





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


	public void setUserName(String userName){
		this.userName = userName;
	}

    public String getUserName(){
    	return this.userName;
    }


	public void setLoginName(String loginName){
		this.loginName = loginName;
	}

    public String getLoginName(){
    	return this.loginName;
    }


	public void setUserId(String userId){
		this.userId = userId;
	}

    public String getUserId(){
    	return this.userId;
    }



}