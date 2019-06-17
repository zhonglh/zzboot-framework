package com.zzboot.framework.core.ui.easyui;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.ui.Pages;
import com.zzboot.framework.core.ui.TreeModel;

import java.io.Serializable;
import java.util.List;

/**
 * EasyUI 的工具类
 * @author Administrator
 */
public class EasyUiUtil {



    public static  EasyUiTreeGrid toTreeList(List list , TreeModel treeModel ,List footer){

        return new EasyUiTreeGrid(list.size() , list , footer );
    }


    public static EasyUiDataGrid toDataGrid(Pages pages){
        return new EasyUiDataGrid(pages.getTotal() , pages.getResults());
    }


    public static EasyUiDataGrid toDataGrid(Page page){
        return new EasyUiDataGrid(page.getTotal() ,page.getRecords());
    }



}
