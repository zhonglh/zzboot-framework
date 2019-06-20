package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.framework.core.ui.easyui.EasyUiTree;
import com.zzboot.system.domain.VsUserMenuEntity;

import com.baomidou.mybatisplus.annotation.TableField;


import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



/**
* 用户菜单信息 BO , 扩展 VsUserMenuEntity 对象
* @author Administrator
* @date 2019-4-10 20:58:06
*/
@EntityAnnotation(value="用户菜单信息" , resource = "system.usermenu"  ,businessName = "menu_name"    ,businessKey = { "" }   ,parentColumnName="pid" ,textColumnName="menu_name"  )
@TableName(value="vs_user_menu" , resultMap = "VsUserMenuResultMap")
public class VsUserMenuBO extends VsUserMenuEntity implements Serializable , IBoEntity {




    @TableField(exist = false)
    @EntityAttrDictAnnotation(group = "shortcut", groupName = "SHORTCUT" ,  dbColumnName = "dict_name" , dbColumnLength = 50 , isNameField = true , dictType = "shortcut")
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "SHORTCUT",sort = 801                      ,required=true )
    private String shortcutName ;



    @TableField(exist = false)
    @EntityAttrDictAnnotation(group = "leaf", groupName = "LEAF" ,  dbColumnName = "dict_name" , dbColumnLength = 50 , isNameField = true , dictType = "leaf")
    @EntityAttrExcelAnnotation(excelProcess= "3")
    @EntityAttrPageAnnotation(title = "LEAF",sort = 1101                      ,required=true )
    private String leafName ;


    //排序编号
    @TableField(exist = false)
    private int sortIndex;



    public void setShortcutName(String shortcutName){
        this.shortcutName = shortcutName;
    }

    public String getShortcutName(){
        return this.shortcutName;
    }

    public void setLeafName(String leafName){
        this.leafName = leafName;
    }

    public String getLeafName(){
        return this.leafName;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public EasyUiTree toEasyUiTree(){
        EasyUiTree tree = new EasyUiTree();
        tree.setId(this.getId());
        tree.setPid(StringUtils.isEmpty(this.getPid())? null : this.getPid());
        tree.setText(this.getTitle());
        Map<String,String> attributes = new HashMap<String,String>();
        tree.setAttributes(attributes);
        return tree;
    }




    public boolean isTable() {
        return false;
    }


    @Override
    public String toString() {


            return this.getTitle();
        
    }
}
