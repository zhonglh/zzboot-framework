package com.zzboot.system.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.zzboot.framework.controller.BaseExcelController;
import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.mybatis.query.Query;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsMenuBO;
import com.zzboot.system.query.impl.TsMenuQueryImpl;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsMenuService;
import com.zzboot.util.web.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class ZzDefaultController<
        RwModel extends BaseEntity<PK>,
        QueryModel extends RwModel,
        PK extends Serializable,
        RwQuery extends Query,
        OnlyQuery extends Query
        >
        extends BaseExcelController<RwModel,QueryModel,PK,RwQuery,OnlyQuery> {


    @Autowired
    private TsDictService tsDictService;

    @Autowired
    private TsMenuService menuService ;

    private String addPageName = "addForm";



    @Override
    protected String computeBreadcrumb(){
        HttpServletRequest request = RequestUtil.getRequest();
        String uri = request.getRequestURI();
        int contextLength = request.getContextPath().length() ;
        uri = uri.substring(contextLength);
        List<TsMenuBO> list =  computeMenu(uri);
        if(list == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(TsMenuBO menu : list){
            sb.append(menu.getMenuName()).append(">");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }



    /**
     * 计算对应的菜单
     * @param uri
     * @return
     */
    protected List<TsMenuBO> computeMenu(String uri){
        //todo 缓存
        TsMenuQueryImpl<TsMenuBO> menuQuery = new TsMenuQueryImpl<TsMenuBO>();
        menuQuery.menuUrl(uri);
        Wrapper wrapper = menuQuery.buildWrapper();
        TsMenuBO menuBO = menuService.getOne(wrapper);
        if(menuBO == null){
            return null;
        }
        List list = menuService.getAllMenu(menuBO);
        if(list == null || list.isEmpty()){
            return null;
        }
        return list;
    }


    /**
     * 获取增加界面的 jsp 名称 , 不包括路径
     * 增加和修改页面不是用同一个页面，所以 重写了 getAddPageName
     * @return
     */
    @Override
    protected String getAddPageName(){
        return addPageName;
    }

    @Override
    public Map<String, ?> getDictMaps(String... dictTypes) {
        return tsDictService.allDict(dictTypes);
    }

    @Override
    public String getDictVal(Object dict) {
        TsDictBO dictBO = (TsDictBO)dict;
        return dictBO.getDictVal();
    }

    @Override
    public String getDictName(Object dict) {
        TsDictBO dictBO = (TsDictBO)dict;
        return dictBO.getDictName();
    }
}
