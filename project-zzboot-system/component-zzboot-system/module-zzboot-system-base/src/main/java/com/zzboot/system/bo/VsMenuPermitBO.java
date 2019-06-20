package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.framework.core.ui.easyui.EasyUiTree;
import com.zzboot.system.domain.VsMenuPermitEntity;


import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



/**
* 菜单许可关联 BO , 扩展 VsMenuPermitEntity 对象
* @author Administrator
* @date 2019-4-10 20:58:07
*/
@EntityAnnotation(value="菜单许可关联" , resource = "system.menupermit"  ,businessName = ""    ,businessKey = { "" }    )
@TableName(value="vs_menu_permit" , resultMap = "VsMenuPermitResultMap")
public class VsMenuPermitBO extends VsMenuPermitEntity implements Serializable , IBoEntity {





    public EasyUiTree toEasyUiTree(){
        EasyUiTree tree = new EasyUiTree();
        tree.setId(this.getPermitId());
        tree.setPid(this.getMenuId());
        tree.setText(this.getPermitName());
        Map<String,String> attributes = new HashMap<String,String>();
        attributes.put( EasyUiTree.TREE_TYPE , "permit");
        tree.setAttributes(attributes);
        return tree;
    }





    public boolean isTable() {


        return false;

    }


    @Override
    public String toString() {


        return super.toString();

    }
}
