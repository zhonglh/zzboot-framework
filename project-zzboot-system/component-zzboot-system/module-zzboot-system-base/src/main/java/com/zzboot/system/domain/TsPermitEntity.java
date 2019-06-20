package com.zzboot.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.util.config.annotaions.*;

import java.lang.String;


/**
 * 许可 实体类
 * @author Administrator
 * @date 2019-4-10 11:08:50
 */
public class TsPermitEntity extends BaseEntity<String> implements java.io.Serializable{

    @TableField(exist=false)
    private static final long serialVersionUID = 1L;



    @EntityAttrDBAnnotation(attrName="许可名称" ,attrColumn="permit_name"  , type = "VARCHAR"      ,  attrLength = 20 , notNull = true )
    @EntityAttrPageAnnotation(title = "许可名称",sort = 200  , pageElement = "text"            , maxLength = 20        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
    
    
	private String  permitName ;



    @EntityAttrDBAnnotation(attrName="许可编号" ,attrColumn="permit_code"  , type = "VARCHAR"      ,  attrLength = 50 , notNull = true )
    @EntityAttrPageAnnotation(title = "许可编号",sort = 300  , pageElement = "text"            , maxLength = 50        ,required=true )
	@EntityAttrExcelAnnotation(excelProcess= "3")
    
    
	private String  permitCode ;



    @EntityAttrDBAnnotation(attrName="备注" ,attrColumn="remark"  , type = "VARCHAR"      ,  attrLength = 200 , notNull = false )
    @EntityAttrPageAnnotation(title = "备注",sort = 400  , pageElement = "text"            , maxLength = 200        ,required=false )
	@EntityAttrExcelAnnotation(excelProcess= "3")
    
    
	private String  remark ;


	/**
	 * 删除标志
	 * 1: 已删除    0：正常
	 */
	@TableLogic
	@TableField(value = "delete_flag")
	private String deleteFlag ;







	public void setPermitName(String permitName){
		this.permitName = permitName;
	}

    public String getPermitName(){
    	return this.permitName;
    }


	public void setPermitCode(String permitCode){
		this.permitCode = permitCode;
	}

    public String getPermitCode(){
    	return this.permitCode;
    }


	public void setRemark(String remark){
		this.remark = remark;
	}

    public String getRemark(){
    	return this.remark;
    }

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}