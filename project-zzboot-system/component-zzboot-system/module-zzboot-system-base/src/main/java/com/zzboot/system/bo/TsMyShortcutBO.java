package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;
import com.zzboot.system.domain.TsMyShortcutEntity;

import com.baomidou.mybatisplus.annotation.TableField;



import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;



/**
* 置顶快捷菜单 BO , 扩展 TsMyShortcutEntity 对象
* @author Administrator
* @date 2019-4-10 11:08:51
*/
@EntityAnnotation(value="置顶快捷菜单" , resource = "system.myshortcut"  ,businessName = ""    ,businessKey = { "" }    )
@TableName(value="ts_my_shortcut" , resultMap = "TsMyShortcutResultMap")
public class TsMyShortcutBO extends TsMyShortcutEntity implements Serializable , IBoEntity {









    public boolean isTable() {

        return true;


    }


    @Override
    public String toString() {


        return super.toString();

    }
}
