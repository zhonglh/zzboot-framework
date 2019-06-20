package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.system.domain.TsMsgTempletEntity;

import com.baomidou.mybatisplus.annotation.TableField;




import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;



/**
* 消息模板 BO , 扩展 TsMsgTempletEntity 对象
* @author Administrator
* @date 2019-4-10 11:08:55
*/
@EntityAnnotation(value="消息模板" , resource = "system.msgtemplet"  ,businessName = "msg_templet_name"    ,businessKey = { "msg_templet_name" } , haveOrgan =  true , haveTenant = true   )
@TableName(value="ts_msg_templet" , resultMap = "TsMsgTempletResultMap")
public class TsMsgTempletBO extends TsMsgTempletEntity implements Serializable , IBoEntity {




    @TableField(exist = false)
    @EntityAttrDictAnnotation(group = "msgTempletType", groupName = "消息模板类型" ,  dbColumnName = "dict_name" , dbColumnLength = 50 , isNameField = true , dictType = "msg_templet_type")
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "消息模板类型",sort = 301                      ,required=true )
    private String msgTempletTypeName ;



    @TableField(exist = false)
    @EntityAttrDictAnnotation(group = "msgLanguage", groupName = "语言" ,  dbColumnName = "dict_name" , dbColumnLength = 50 , isNameField = true , dictType = "msg_language")
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "语言",sort = 601                      ,required=true )
    private String msgLanguageName ;



    @TableField(exist = false)
    @EntityAttrDictAnnotation(group = "templetEffective", groupName = "模板是否有效" ,  dbColumnName = "dict_name" , dbColumnLength = 50 , isNameField = true , dictType = "yes_no")
    @EntityAttrPageAnnotation(title = "模板是否有效",sort = 701                      ,required=true )
    @EntityAttrExcelAnnotation(excelProcess= "2")
    private String templetEffectiveName ;



    @TableField(exist = false)
    @EntityAttrFkAnnotation(group = "depId",  groupName = "部门" ,   dbColumnName = "dep_name" , dbColumnType = "VARCHAR" , dbColumnLength = 100   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsDepBO.class)
    @EntityAttrPageAnnotation(title = "部门",sort = 801                      ,required=true )
    private String depName ;



    public void setMsgTempletTypeName(String msgTempletTypeName){
        this.msgTempletTypeName = msgTempletTypeName;
    }

    public String getMsgTempletTypeName(){
        return this.msgTempletTypeName;
    }

    public void setMsgLanguageName(String msgLanguageName){
        this.msgLanguageName = msgLanguageName;
    }

    public String getMsgLanguageName(){
        return this.msgLanguageName;
    }

    public void setTempletEffectiveName(String templetEffectiveName){
        this.templetEffectiveName = templetEffectiveName;
    }

    public String getTempletEffectiveName(){
        return this.templetEffectiveName;
    }

    public void setDepName(String depName){
        this.depName = depName;
    }

    public String getDepName(){
        return this.depName;
    }






    public boolean isTable() {

        return true;


    }


    @Override
    public String toString() {


        return super.toString();

    }
}
