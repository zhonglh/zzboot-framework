package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.framework.core.ui.easyui.EasyUiTree;
import com.zzboot.system.domain.TsDictTypeEntity;



import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.StringUtils;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
* 字典类型 BO , 扩展 TsDictTypeEntity 对象
* @author Administrator
* @date 2019-4-10 11:08:54
*/
@EntityAnnotation(value="字典类型" , resource = "system.dicttype"  ,businessName = "dict_type_name"    ,businessKey = { "dict_type_code" }   ,parentColumnName="pid" ,textColumnName="dict_type_name"  )
@TableName(value="ts_dict_type" , resultMap = "TsDictTypeResultMap")
public class TsDictTypeBO extends TsDictTypeEntity implements Serializable , IBoEntity {







    public EasyUiTree toEasyUiTree(){
        EasyUiTree tree = new EasyUiTree();
        tree.setId(this.getId());
        tree.setPid(StringUtils.isEmpty(this.getPid())? null : this.getPid());
        tree.setText(this.getDictTypeName());
        Map<String,String> attributes = new HashMap<String,String>();
        tree.setAttributes(attributes);
        return tree;
    }




    public boolean isTable() {

        return true;


    }


    @Override
    public String toString() {


            return this.getDictTypeName();
        
    }
}
