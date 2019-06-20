package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.framework.core.ui.easyui.EasyUiTree;
import com.zzboot.system.domain.TsOrganEntity;

import com.baomidou.mybatisplus.annotation.TableField;



import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.StringUtils;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
* 机构 BO , 扩展 TsOrganEntity 对象
* @author Administrator
* @date 2019-4-10 11:08:52
*/
@EntityAnnotation(value="机构" , resource = "system.organ"  ,businessName = "organ_name"    ,businessKey = { "organ_code" }   ,parentColumnName="pid" ,textColumnName="organ_name" ,  haveTenant = true )
@TableName(value="ts_organ" , resultMap = "TsOrganResultMap")
public class TsOrganBO extends TsOrganEntity implements Serializable , IBoEntity {




    @TableField(exist = false)
    @EntityAttrDictAnnotation(group = "organType", groupName = "机构类型" ,  dbColumnName = "dict_name" , dbColumnLength = 50 , isNameField = true , dictType = "organ_type")
    @EntityAttrExcelAnnotation(excelProcess= "2")
    @EntityAttrPageAnnotation(title = "机构类型",sort = 401 )
    private String organTypeName ;




    @TableField(exist = false)
    @EntityAttrDictAnnotation(group = "organStatus", groupName = "机构状态" ,  dbColumnName = "dict_name" , dbColumnLength = 50 , isNameField = true , dictType = "organ_status")
    @EntityAttrExcelAnnotation(excelProcess= "2")
    @EntityAttrPageAnnotation(title = "机构状态",sort = 701                      ,required=true )
    private String organStatusName;



    @TableField(exist = false)
    @EntityAttrFkAnnotation(group = "leadUserId",  groupName = "负责人" ,   dbColumnName = "user_name" , dbColumnType = "VARCHAR" , dbColumnLength = 50   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsUserBO.class)
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "负责人",sort = 801)
    private String leadUserName ;



    @TableField(exist = false)
    @EntityAttrFkAnnotation(group = "pid",  groupName = "上级机构" ,   dbColumnName = "organ_name" , dbColumnType = "VARCHAR" , dbColumnLength = 50   , dbColumnNotNull = true , fkClass=com.zzboot.system.bo.TsOrganBO.class)
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "上级机构",sort = 501 )
    private String pname ;



    public void setOrganTypeName(String organTypeName){
        this.organTypeName = organTypeName;
    }

    public String getOrganTypeName(){
        return this.organTypeName;
    }

    public void setLeadUserName(String leadUserName){
        this.leadUserName = leadUserName;
    }

    public String getLeadUserName(){
        return this.leadUserName;
    }

    public String getOrganStatusName() {
        return organStatusName;
    }

    public void setOrganStatusName(String organStatusName) {
        this.organStatusName = organStatusName;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public EasyUiTree toEasyUiTree(){
        EasyUiTree tree = new EasyUiTree();
        tree.setId(this.getId());
        tree.setPid(StringUtils.isEmpty(this.getPid())? null : this.getPid());
        tree.setText(this.getOrganName());
        Map<String,String> attributes = new HashMap<String,String>();
        tree.setAttributes(attributes);
        return tree;
    }




    public boolean isTable() {

        return true;


    }


    @Override
    public String toString() {


            return this.getOrganName();
        
    }
}
