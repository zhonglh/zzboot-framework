package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;

import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;



/**
* 用户 BO , 扩展 TsUserBO 对象
* @author Administrator
* @date 2019-4-10 20:58:04
*/
@EntityAnnotation(value="用户" , resource = "system.nodeuser"  ,businessName = "user_name"    ,businessKey = { "" }    )
@TableName(value="vs_node_user" , resultMap = "VsNodeUserResultMap")
public class VsNodeUserBO extends TsUserBO implements Serializable , IBoEntity {



    @Override
    public boolean isTable() {


        return false;

    }


    @Override
    public String toString() {

        return super.toString();

    }
}
