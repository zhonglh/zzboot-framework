package com.zzboot.system.bo;

import com.zzboot.framework.core.db.entity.IBoEntity;


import com.zzboot.util.config.annotaions.*;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;



/**
* 文件使用 BO , 扩展 TsFileUseBO 对象
* @author Administrator
* @date 2019-4-10 20:58:06
*/
@EntityAnnotation(value="文件使用" , resource = "system.fileuse"  ,businessName = "show_name"   )
@TableName(value="vs_file_use" , resultMap = "VsFileUseResultMap")
public class VsFileUseBO extends TsFileUseBO implements Serializable , IBoEntity {



    @Override
    public boolean isTable() {
        return false;
    }


    @Override
    public String toString() {
        return super.toString();
    }


}
