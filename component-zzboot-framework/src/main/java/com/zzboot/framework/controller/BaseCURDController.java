package com.zzboot.framework.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzboot.framework.core.db.entity.BaseBusinessEntity;
import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.db.mybatis.query.Query;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.enums.EnumSymbol;
import com.zzboot.framework.core.enums.EnumTreeState;
import com.zzboot.framework.core.exceptions.DbException;
import com.zzboot.framework.core.ui.Pages;
import com.zzboot.framework.core.vo.AjaxJson;
import com.zzboot.util.base.java.ReflectionSuper;
import com.zzboot.util.config.annotaions.EntityAnnotation;
import com.zzboot.util.web.PaginationContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理数据库基础的增加 修改 读取 删除 功能
 * @author Administrator
 */
@Slf4j
public abstract class BaseCURDController<
        RwModel extends BaseEntity<PK>,
        QueryModel extends RwModel,
        PK extends Serializable,
        RwQuery extends Query,
        OnlyQuery extends Query
        >
        extends BaseBusinessController<RwModel,QueryModel,PK,RwQuery,OnlyQuery>
        implements ICURDController<RwModel , QueryModel , PK>

{

    protected BaseCURDController() {
        super();
        setViewPrefix(defaultViewPrefix());
        setResourceIdentity(this.getViewPrefix().replaceAll("/","\\."));
    }






    /**
     * 到列表界面
     * @param m
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/toList" , method = RequestMethod.GET )
    public String toList(QueryModel m, ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) {

        this.assertHasViewPermission();

        this.baseRwService.processResult(m);
        modelMap.put("entity" ,m);
        modelMap.put("m" ,m);

        if (listAlsoSetCommonData) {
            setCommonData(m,modelMap);
        }

        processQueryString(modelMap,request);


        processPath(modelMap);

        String pageName = this.getListPageName();
        if(StringUtils.isEmpty(pageName)){
            pageName = defaultListPageName;
        }
        return viewName(pageName);
    }


    @RequestMapping(value = "/list" , method={ RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object list(QueryModel m , OnlyQuery query, Pages<QueryModel> pages , ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) {

        this.assertHasViewPermission();


        if(pages.getPageNum() == 0) {
            pages.setPageNum(PaginationContext.getPageNum());
        }

        if(pages.getPageSize() == 0) {
            pages.setPageSize(PaginationContext.getPageSize());
        }

        processPages(pages , request);

        Page<QueryModel> page = new Page<QueryModel>(pages.getPageNum(), pages.getPageSize());

        ILoginUserEntity<PK> sessionUserVO = getSessionUser();
        processOnlyQuery(query , m , sessionUserVO);

        Wrapper wrapper = buildQueryWrapper(query , m);



        page = (Page<QueryModel>)baseQueryService.page(page , wrapper );

        processResult(page.getRecords());

        return toList(page);

    }






    @RequestMapping(value = "/toTree" , method={ RequestMethod.POST, RequestMethod.GET})
    public String toTree(QueryModel m, ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) {

        this.assertHasViewPermission();

        this.baseRwService.processResult(m);
        modelMap.put("entity" ,m);
        modelMap.put("m" ,m);

        if (listAlsoSetCommonData) {
            setCommonData(m,modelMap);
        }


        processQueryString(modelMap,request);


        processPath(modelMap);

        String pageName = this.getTreePageName();
        if(StringUtils.isEmpty(pageName)){
            pageName = defaultTreePageName;
        }
        return viewName(pageName);

    }

    @RequestMapping(value = "/tree" , method={ RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object tree(QueryModel m , OnlyQuery query, Pages<QueryModel> pages , ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) {

        this.assertHasViewPermission();


        PK id = m.getId();
        ReflectionSuper.setFieldValue(query , "id" , null);

        pages.setPageNum(1);
        pages.setPageSize(Integer.MAX_VALUE);

        processPages(pages , request);

        Page<QueryModel> page = new Page<QueryModel>(pages.getPageNum(), pages.getPageSize());

        ILoginUserEntity<PK> sessionUserVO = getSessionUser();
        processOnlyQuery(query , m , sessionUserVO);


        QueryWrapper<QueryModel> wrapper = (QueryWrapper<QueryModel>)buildQueryWrapper(query , m);

        EntityAnnotation ea = m.getClass().getAnnotation(EntityAnnotation.class);
        if(ea == null || StringUtils.isEmpty(ea.parentColumnName())){
            throw EnumErrorMsg.code_error.toException();
        }


        if(id == null){
            //增加查询条件，用括号包住
            wrapper.nested((qw)->{
                qw.eq(ea.parentColumnName() , "" );
                qw.or();
                qw.isNull(ea.parentColumnName());
                return qw;
            });

        }else {
            wrapper.eq(ea.parentColumnName() , id );
        }


        page = (Page<QueryModel>)baseQueryService.page(page , wrapper );

        processResult(page.getRecords());


        List footer = buildFooter(page);

        List<QueryModel> list =  page.getRecords() ;

        if(list != null && !list.isEmpty()) {
            for (QueryModel temp : list){
                QueryWrapper<QueryModel> queryWrapper = new QueryWrapper<QueryModel>();
                queryWrapper.eq( ea.parentColumnName()  , temp.getId());
                int count = this.baseQueryService.count(queryWrapper);
                if(count == 0){
                    temp.setState(EnumTreeState.OPEN.getTheValue());
                }
            }
        }

        return list ;

    }



    /**
     * 设置树的页脚
     * @param page
     * @return
     */
    protected List buildFooter(Page<QueryModel> page){
        return null;
    }



    /**
     * 查看界面
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
    public String showViewForm(ModelMap modelMap, @PathVariable("id") PK id, HttpServletRequest request, HttpServletResponse response) {

        this.assertHasViewPermission();


        QueryWrapper<RwModel> wrapper = new QueryWrapper<RwModel>();
        wrapper.eq("id" , id);
        RwModel m = baseRwService.getOne(wrapper);
        if(m == null){
            throw EnumErrorMsg.no_auth.toException();
        }
        processQueryString(modelMap,request);
        setCommonData(m,modelMap);
        customInfoByViewForm(m , modelMap);
        modelMap.addAttribute("m", m);
        modelMap.addAttribute("entity", m);


        processPath(modelMap);
        String pageName = this.getViewPageName();
        if(StringUtils.isEmpty(pageName)){
            pageName = defaultViewPageName;
        }
        return viewName(pageName);
    }


    /**
     * 显示创建页面
     * @param m
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(RwModel m , ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        this.assertHasCreatePermission();
        processQueryString(modelMap,request);
        setCommonData(m,modelMap);

        this.baseRwService.processResult(m);

        setInit(m);
        customInfoByCreateForm(m , modelMap);
        modelMap.addAttribute("m",  m);
        modelMap.addAttribute("entity", m);

        processPath(modelMap);
        String pageName = this.getAddPageName();
        if(StringUtils.isEmpty(pageName)){
            pageName = defaultAddPageName;
        }
        return viewName(pageName);
    }


    /**
     * 显示更新页面
     * @param modelMap
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(ModelMap modelMap, @PathVariable("id") PK id, HttpServletRequest request, HttpServletResponse response) {

        this.assertHasUpdatePermission();


        QueryWrapper<RwModel> wrapper = new QueryWrapper<RwModel>();
        wrapper.eq("id" , id);
        RwModel m = baseRwService.getOne(wrapper);
        if(m == null){
            throw EnumErrorMsg.no_auth.toException();
        }
        processQueryString(modelMap,request);
        setCommonData(m,modelMap);
        customInfoByUpdateForm(m , modelMap);
        modelMap.addAttribute("m", m);
        modelMap.addAttribute("entity", m);

        processPath(modelMap);
        String pageName = this.getEditPageName();
        if(StringUtils.isEmpty(pageName)){
            pageName = defaultEditPageName;
        }
        return viewName(pageName);
    }


    /**
     * 显示更新或者新增页面
     * @param modelMap
     * @param m
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.GET)
    public String showAddOrUpdateForm(ModelMap modelMap, RwModel m, RwQuery q, HttpServletRequest request, HttpServletResponse response) {

        this.assertHasEditPermission();


        QueryWrapper<RwModel> wrapper = q.buildWrapper();
        this.buildRwWrapper(q, m) ;
        List<RwModel> list = baseRwService.list(wrapper);
        if(list != null && list.size() > 1){
            throw EnumErrorMsg.code_error.toException();
        }

        boolean isInsert = true;
        RwModel entity = m;
        if(list != null && list.size() == 1){
            entity = list.get(0);
            isInsert = false;
        }
        processQueryString(modelMap,request);
        setCommonData(entity,modelMap);

        if(isInsert) {
            customInfoByCreateForm(entity, modelMap);
        }else{
            customInfoByUpdateForm(entity, modelMap);
        }

        entity = this.baseRwService.processResult(entity);

        modelMap.addAttribute("m", entity);
        modelMap.addAttribute("entity", entity);


        processPath(modelMap);
        String pageName = null;
        if(isInsert) {
            pageName = this.getAddPageName();
            if (StringUtils.isEmpty(pageName)) {
                pageName = defaultAddPageName;
            }
        }else {
            pageName = this.getEditPageName();
            if (StringUtils.isEmpty(pageName)) {
                pageName = defaultEditPageName;
            }
        }
        return viewName(pageName);
    }



    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET)
    public String showAllPage(ModelMap modelMap, @PathVariable("id") PK id, HttpServletRequest request, HttpServletResponse response) {
        RwModel m = baseRwService.getById(id , false);


        customInfoByAllPage(m , modelMap);
        processQueryString(modelMap,request);

        modelMap.addAttribute("m",  m);
        modelMap.addAttribute("entity", m);

        processPath(modelMap);
        String pageName = this.getAllPageName();
        if(StringUtils.isEmpty(pageName)){
            pageName = defaultAllPageName;
        }
        return viewName(pageName);
    }


    /**
     * 新增操作
     * @param m
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(RwModel m, ModelMap modelMap , HttpServletRequest request, HttpServletResponse response) {

        this.assertHasCreatePermission();

        ILoginUserEntity<PK> sessionUserVO = getSessionUser();


        this.gatherCreateInformation( m,  modelMap , sessionUserVO, request,  response);

        //插入信息
        insertInfo(m, sessionUserVO);

        AjaxJson result =  AjaxJson.ok();
        result.setId(m.getId());
        return result;
    }



    @Override
    protected void insertInfo(RwModel m, ILoginUserEntity<PK> sessionUserVO) {
        insertInfo(m , sessionUserVO , true , true);
    }

    @Override
    public void insertInfo(RwModel m, ILoginUserEntity<PK> sessionUserVO , boolean saveFlag , boolean processBOFlag) {
        //设置创建附加信息，如创建时间， 创建人
        this.setInsertInfo(m, sessionUserVO);

        //初始化默认值
        setInit(m);

        //创建时定制的数据，如状态 等
        this.setCustomInfoByInsert(m,sessionUserVO);

        //处理创建的数据， 如反填状态名称，外键信息等
        if(processBOFlag) {
            this.processBO(m);
        }



        boolean success = false;
        try{
            //检查新增附加信息
            checkInsertInfo(m);
            //检查字段的合法性
            checkEntityLegality(m , true , true , true);

            //检查业务上是否允许增加
            this.checkCanInsert(m,sessionUserVO);

            //检查重复数据
            this.baseRwService.isExist(m);

            if(saveFlag) {
                success = baseRwService.save(m);
            }else {
                success = true;
            }

        }catch(RuntimeException e){
            log.error(e.getMessage() , e);
            throw e;
        }catch(Exception e){
            log.error(e.getMessage() , e);
            throw DbException.DB_SAVE_SAME;
        }

        if(!success){
            throw DbException.DB_INSERT_RESULT_0;
        }
    }


    @RequestMapping(value = "/{id}/update", method = {RequestMethod.POST , RequestMethod.PUT})
    @ResponseBody
    public Object update(@PathVariable("id") PK id, ModelMap modelMap, RwModel m , HttpServletRequest request, HttpServletResponse response) {

        //检查功能权限
        this.assertHasUpdatePermission();


        ILoginUserEntity<PK> sessionUserVO = getSessionUser();

        this.gatherUpdateInformation( m,  modelMap , sessionUserVO, request,  response);

        QueryWrapper<RwModel> wrapper = new QueryWrapper<>();
        wrapper.eq("id" , id);
        RwModel temp = baseRwService.getOne(wrapper);
        if(temp == null){
            throw EnumErrorMsg.no_auth.toException();
        }

        if(m instanceof BaseBusinessEntity) {
            BaseBusinessEntity bbe = (BaseBusinessEntity)m;
            bbe.setVersionNo(((BaseBusinessEntity)temp).getVersionNo());
        }

        //设置一些旧的值
        m = setOldValue(m , temp);

        //处理更新附加信息，如更新时间  更新人等
        this.setUpdateInfo(m, sessionUserVO);

        //设置更新时的一些属性信息
        setCustomInfoByUpdate(m , sessionUserVO);


        //处理创建的数据， 如反填状态名称，外键信息等
        this.processBO(m);




        boolean success = false;
        try {

            //检查数据合法性
            checkEntityLegality(m , false , true , true);
            Assert.notNull(m.getId(),"出现内部错误");


            //检查重复数据
            this.baseRwService.isExist(m);

            //检查业务上是否允许修改
            this.checkCanUpdate(m,sessionUserVO);

            success = baseRwService.updateById(m);

        }catch(RuntimeException e){
            log.error(e.getMessage() , e);
            throw e;
        }catch(Exception e){
            log.error(e.getMessage() , e);
            throw DbException.DB_SAVE_SAME;
        }

        if(!success){
            throw DbException.DB_UPDATE_RESULT_0;
        }else {

            AjaxJson result =  AjaxJson.ok();
            result.setId(m.getId());
            return result;
        }
    }


    /**
     * 显示更新或者新增页面
     * @param modelMap
     * @param m
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addOrUpdate", method = {RequestMethod.POST , RequestMethod.PUT})
    public String addOrUpdate(ModelMap modelMap, RwModel m, RwQuery q, HttpServletRequest request, HttpServletResponse response) {

        this.assertHasEditPermission();


        QueryWrapper<RwModel> wrapper = q.buildWrapper();
        this.buildRwWrapper(q, m) ;
        List<RwModel> list = baseRwService.list(wrapper);
        if(list != null && list.size() > 1){
            throw EnumErrorMsg.code_error.toException();
        }

        boolean isInsert = true;
        RwModel entity = m;
        if(list != null && list.size() == 1){
            entity = list.get(0);
            isInsert = false;
        }

        processQueryString(modelMap, request);
        setCommonData(entity,modelMap);

        if(isInsert) {
            customInfoByCreateForm(entity, modelMap);
        }else{
            customInfoByUpdateForm(entity, modelMap);
        }

        entity = this.baseRwService.processResult(entity);

        modelMap.addAttribute("m", entity);
        modelMap.addAttribute("entity", entity);


        String pageName = null;
        if(isInsert) {
            pageName = this.getAddPageName();
            if (StringUtils.isEmpty(pageName)) {
                pageName = defaultAddPageName;
            }
        }else {
            pageName = this.getEditPageName();
            if (StringUtils.isEmpty(pageName)) {
                pageName = defaultEditPageName;
            }
        }
        return viewName(pageName);
    }




    /**
     * 单条删除
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    @ResponseBody
    public Object delete(@PathVariable("id") PK id , HttpServletRequest request, HttpServletResponse response) {


        this.assertHasDeletePermission();


        ILoginUserEntity<PK> sessionUserVO = getSessionUser();

        QueryWrapper<RwModel> wrapper = new QueryWrapper<>();
        wrapper.eq("id" , id);
        setCustomInfoByDelete(wrapper , sessionUserVO);
        RwModel m = baseRwService.getOne(wrapper);
        if(m == null){
            throw EnumErrorMsg.no_auth.toException();
        }

        baseRwService.specialHandler(m);

        boolean success = false;
        try {
            checkCanDelete(m, sessionUserVO);
            success = baseRwService.deleteById(m);
        }catch(RuntimeException e){
            log.error(e.getMessage() , e);
            throw e;
        }catch(Exception e){
            log.error(e.getMessage() , e);
            throw DbException.DB_DELETE_RESULT_0;
        }

        if(!success){
            throw EnumErrorMsg.no_auth.toException();
        }
        return AjaxJson.successAjax;
    }



    /**
     * 批量删除
     * @param ids
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/batch/delete" ,method = {RequestMethod.POST, RequestMethod.DELETE})
    @ResponseBody
    public Object deleteInBatch(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request, HttpServletResponse response) {

        this.assertHasDeletePermission();

        if(ids == null || ids.isEmpty()){
            throw EnumErrorMsg.not_select_todelete.toException();
        }

        ILoginUserEntity<PK> sessionUserVO = getSessionUser();


        QueryWrapper<RwModel> wrapper = new QueryWrapper<>();
        String idList[] = ids.split( EnumSymbol.COMMA.getCode() );
        wrapper.nested((qw)-> {
            int index = 0;
            for (String id : idList) {
                if (index > 0) {
                    qw.or();
                }
                qw.eq("id", id);
                index++;
            }
            return qw;
        });

        setCustomInfoByDelete(wrapper , sessionUserVO);

        List<RwModel> list = baseRwService.list(wrapper);

        if(list == null && list.isEmpty()){
            throw EnumErrorMsg.no_auth.toException();
        }


        List<RwModel> deleteList = new ArrayList<RwModel>();

        for(RwModel m : list){
            try{
                this.checkCanDelete(m , sessionUserVO);
                deleteList.add(m);
            }catch(Exception e){

            }
        }

        for(RwModel m : deleteList){
            baseRwService.specialHandler(m);
        }


        boolean success = false;
        try {
            if(deleteList != null && !deleteList.isEmpty()) {
                success = baseRwService.deletesByIds(deleteList);
            }
        }catch(RuntimeException e){
            log.error(e.getMessage() , e);
            throw e;
        }catch(Exception e){
            log.error(e.getMessage() , e);
            throw DbException.DB_DELETE_RESULT_0;
        }



        if(!success){
            throw DbException.DB_DELETE_RESULT_0;
        }
        return AjaxJson.successAjax;

    }



























}
