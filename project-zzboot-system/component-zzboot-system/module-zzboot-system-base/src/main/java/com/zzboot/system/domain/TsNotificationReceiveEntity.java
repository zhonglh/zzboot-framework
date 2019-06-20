package com.zzboot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.util.config.annotaions.*;

import java.sql.Timestamp;
import java.lang.String;

/**
 * 通知接收人 实体类
 * @author Administrator
 * @date 2019-4-10 11:08:54
 */
public class TsNotificationReceiveEntity extends BaseEntity<String> implements java.io.Serializable{

    @TableField(exist=false)
    private static final long serialVersionUID = 1L;



    @EntityAttrDBAnnotation(attrName="通知" ,attrColumn="notify_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "通知",sort = 200  , pageElement = "text"            , maxLength = 32        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
    
    
	private String  notifyId ;



    @EntityAttrFkAnnotation(group = "receiveUserId",  groupName = "接收人" ,   dbColumnName = "id" , dbColumnType = "CHAR" , dbColumnLength = 32   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsUserBO.class)
    @EntityAttrDBAnnotation(attrName="接收人" ,attrColumn="receive_user_id"  , type = "CHAR"      ,  attrLength = 32 , notNull = true )
    @EntityAttrPageAnnotation(title = "接收人",sort = 300  , pageElement = "text"            , maxLength = 32        ,required=true )
	
    
    
	private String  receiveUserId ;



    @EntityAttrDictAnnotation(group = "isRead", groupName = "已读" ,  dbColumnName = "dict_val" , dbColumnLength = 2 , isValueField = true , dictType = "yes_no")
    @EntityAttrDBAnnotation(attrName="已读" ,attrColumn="is_read"  , type = "CHAR"      ,  attrLength = 1 , notNull = true )
    @EntityAttrPageAnnotation(title = "已读",sort = 400  , pageElement = "select"            , maxLength = 1        ,required=true )
	

	private String  isRead ;



    @EntityAttrDBAnnotation(attrName="读取时间" ,attrColumn="read_time"  , type = "TIMESTAMP"       , notNull = true )
    @EntityAttrPageAnnotation(title = "读取时间",sort = 500  , pageElement = "timestamp"                  ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
    
    
	private Timestamp  readTime ;





	public void setNotifyId(String notifyId){
		this.notifyId = notifyId;
	}

    public String getNotifyId(){
    	return this.notifyId;
    }


	public void setReceiveUserId(String receiveUserId){
		this.receiveUserId = receiveUserId;
	}

    public String getReceiveUserId(){
    	return this.receiveUserId;
    }


	public void setIsRead(String isRead){
		this.isRead = isRead;
	}

    public String getIsRead(){
    	return this.isRead;
    }


	public void setReadTime(Timestamp readTime){
		this.readTime = readTime;
	}

    public Timestamp getReadTime(){
    	return this.readTime;
    }


	
}