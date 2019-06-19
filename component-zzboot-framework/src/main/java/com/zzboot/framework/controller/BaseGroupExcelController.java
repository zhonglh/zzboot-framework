package com.zzboot.framework.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.db.mybatis.query.Query;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.ui.Pages;
import com.zzboot.framework.core.vo.AjaxJson;
import com.zzboot.util.config.Global;
import com.zzboot.util.config.annotaions.EntityAnnotation;
import com.zzboot.util.config.annotaions.EntityAttrDictAnnotation;
import com.zzboot.util.config.annotaions.EntityAttrFkAnnotation;
import com.zzboot.util.file.base.FileKit;
import com.zzboot.util.poi.enums.EnumExcelFileType;
import com.zzboot.util.poi.exceptions.ExcelAbsenceException;
import com.zzboot.util.poi.exceptions.ExcelFormatException;
import com.zzboot.util.poi.exceptions.ExcelTypeMatchingException;
import com.zzboot.util.poi.export.ExcelExport;
import com.zzboot.util.poi.export.excelype.BaseXlsExport;
import com.zzboot.util.poi.export.excelype.CsvXlsExport;
import com.zzboot.util.poi.export.filetype.CsvExport;
import com.zzboot.util.poi.export.filetype.HssfExport;
import com.zzboot.util.poi.export.filetype.SxssfExport;
import com.zzboot.util.poi.imports.ExcelImport;
import com.zzboot.util.poi.util.ColumnUtil;
import com.zzboot.util.poi.vo.Column;
import com.zzboot.util.web.PaginationContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 处理数据库基础的Excel 功能
 * 包括导出，导入 支持 Excel2003  Excel2007  CSV 格式
 * @author Administrator
 */
@Slf4j
public abstract class BaseGroupExcelController <
        RwModel extends BaseEntity<PK>,
        QueryModel extends RwModel,
        PK extends Serializable,
        RwQuery extends Query,
        OnlyQuery extends RwQuery>
        extends BaseGroupCURDController<RwModel,QueryModel,PK,RwQuery,OnlyQuery>
        implements IExcelConttroller<RwModel,QueryModel,PK>{


    public BaseGroupExcelController(){
        super();
    }

    /**
     * 下载模板
     * @param excelType
     * @param m
     * @param query
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{excelType}/download", method = RequestMethod.GET)
    protected void download(@PathVariable("excelType") String excelType, QueryModel m , RwQuery query, HttpServletRequest request, HttpServletResponse response) {


        this.permissionList.assertHasImportPermission();

        if(StringUtils.isEmpty(excelType)){
            throw EnumErrorMsg.code_error.toException();
        }

        Page<QueryModel> page = new Page<QueryModel>(1, 1);
        ILoginUserEntity<PK> sessionUserVO = getSessionUser();
        processRwQuery(query , m , sessionUserVO);
        Wrapper wrapper = buildRwWrapper(query , m);
        page = (Page<QueryModel>)baseQueryService.page(page , wrapper );

        QueryModel topDate = null;
        if(page.getRecords() != null && !page.getRecords().isEmpty()){
            topDate = page.getRecords().get(0);
        }else {
            topDate  = newQueryModel();
        }




        ExcelHelper.download(excelType, topDate, this , response);


    }


    @RequestMapping(value = "/{excelType}/export", method = RequestMethod.GET)
    protected void export(@PathVariable("excelType") String excelType, QueryModel m , OnlyQuery query, HttpServletRequest request, HttpServletResponse response) {

        if(StringUtils.isEmpty(excelType)){
            throw EnumErrorMsg.code_error.toException();
        }

        this.permissionList.assertHasExportPermission();


        BaseXlsExport<QueryModel> bxe = null;
        ExcelExport<QueryModel> aee = null;
        excelType = excelType.toLowerCase();

        switch (excelType) {
            case "csv":
                bxe = new CsvXlsExport<QueryModel>();
                bxe.setEntityClz(this.getQueryEntityClass());
                break;
            case "hssf":
                bxe = new BaseXlsExport<QueryModel>();
                bxe.setEntityClz(this.getQueryEntityClass());
                break;
            case "sxssf":
                bxe = new BaseXlsExport<QueryModel>();
                bxe.setEntityClz(this.getQueryEntityClass());
                break;
        }



        switch (excelType) {
            case "sxssf":
                aee = new SxssfExport<QueryModel>(bxe);
                break;
            case "hssf":
                aee = new HssfExport<QueryModel>(bxe);
                break;
            case "csv":
                aee = new CsvExport(bxe);
                break;

        }

        Wrapper<QueryModel> wrapper = buildQueryWrapper(query , m);
        List<QueryModel> all =  baseQueryService.list(wrapper);

        ExcelHelper.exportExcel(response, aee, all , this);

    }



    /**
     * 导出标题
     * @param aee
     * @param header
     * @param m
     * @param isAddNumber
     */
    @Override
    public void exportTitles(ExcelExport<QueryModel> aee , int header, QueryModel m, boolean isAddNumber){
        aee.exportTitles(header ,  m  , isAddNumber );
    }


    /**
     * 导出头信息
     * @param aee
     */
    @Override
    public void exportHeader(ExcelExport<QueryModel> aee , String[] headerInfo ){
        if(headerInfo != null && headerInfo.length > 0) {
            aee.exportHeaders(Arrays.asList(headerInfo), getHeaderCellLength());
        }

    }


    /**
     * 导出内容
     * @param aee
     * @param contents
     * @param rowIndex
     * @param isAddNumber
     */
    @Override
    public void exportContent(ExcelExport<QueryModel> aee , List<QueryModel> contents, int rowIndex, boolean isAddNumber){
        aee.exportContent(contents ,  rowIndex  , isAddNumber );
    }


    /**
     * 计算出头信息 ， 如果有特殊需求， 可以重载
     * @return
     */
    @Override
    public String[] getExcelHeaderInfo(){
        if(Global.getUserConfig().getExcelExportHeader()) {
            EntityAnnotation ea = this.getRwEntityClass().getAnnotation(EntityAnnotation.class);
            if (ea != null) {
                return new String[]{ea.value()};
            } else {
                return null;
            }
        }else {
            return null;
        }
    }


    protected int getHeaderCellLength(){
        return Global.getUserConfig().getExcelHeaderMaxCells();
    }

    /**
     * 下载 Excel信息
     * @param aee
     * @param response
     * @throws RuntimeException
     */
    @Override
    public void exportXls(ExcelExport<QueryModel> aee , HttpServletResponse response) throws RuntimeException {
        aee.exportXls(response);
    }


    @Override
    public boolean isAddNumberByExport(){
        return Global.getUserConfig().getExcelAddNumber();
    }






    /**
     * 导入Excel
     * @param file
     * @param res
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doExcel" , method = {RequestMethod.GET , RequestMethod.POST} )
    @ResponseBody
    public AjaxJson doExcel(MultipartFile file, HttpServletResponse res, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {

        this.permissionList.assertHasImportPermission();



        EnumExcelFileType excelFileType = null;
        try{
            String fileType = FileKit.getFileTypeByFileName(file.getOriginalFilename());
            excelFileType = EnumExcelFileType.getEnumByFileType(fileType);
            if(excelFileType == null){
                return new AjaxJson(false , "请选择Excel文件" );
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }



        ILoginUserEntity<PK> sessionUserVO = getSessionUser();


        List<QueryModel> list  = null;

        try {
            list  =this.getExcelData(file , excelFileType);
        }catch(ExcelAbsenceException e){
            return new AjaxJson(false , e.toString());
        }catch(ExcelTypeMatchingException e){
            return new AjaxJson(false , e.toString());
        }catch(ExcelFormatException e){
            return new AjaxJson(false , e.toString());
        }catch(RuntimeException e){
            return new AjaxJson(false , e.getMessage());
        }catch(Exception e){
            log.error(e.getMessage() , e);
            return new AjaxJson(false ,  "未知错误");
        }

        List<QueryModel> result = ExcelHelper.doExcelData(list , sessionUserVO , this);



        if(result == null) {
            return AjaxJson.ok();
        }else {
            request.getSession().setAttribute(this.getRwEntityClass().getName(), list);
            return AjaxJson.fail("导入的信息中有些问题");
        }


    }


    @RequestMapping(value = "/excelResultPage" , method = {RequestMethod.GET } )
    public String excelResultPage(ModelMap modelMap , HttpServletResponse res, HttpServletRequest request)  {


        processPath(modelMap);
        List<Column> columns = ColumnUtil.getExcelColumn(this.getQueryEntityClass(), true);
        request.setAttribute("columns" , columns);
        return viewName("excelPage");
    }

    @RequestMapping(value = "/excelList" , method = {RequestMethod.GET  , RequestMethod.POST } )
    @ResponseBody
    public Object excelList(HttpServletResponse res, HttpServletRequest request , Pages<QueryModel> pages)  {

        List<QueryModel> list = (List<QueryModel>)request.getSession().getAttribute(this.getRwEntityClass().getName());



        if(pages.getPageNum() == 0) {
            pages.setPageNum(PaginationContext.getPageNum());
        }

        if(pages.getPageSize() == 0) {
            pages.setPageSize(PaginationContext.getPageSize());
        }


        Page<QueryModel> page = new Page<QueryModel>(pages.getPageNum(), pages.getPageSize());

        ILoginUserEntity<PK> sessionUserVO = getSessionUser();


        page.setTotal(list.size());
        List<QueryModel> allList= list.subList( (int) ((page.getCurrent() - 1) * pages.getPageSize())  , (int) ((page.getCurrent() ) * pages.getPageSize()) );
        page.setRecords(allList);

        //processResult(page.getRecords());

        return toList(page);


    }




    /**
     * Excel导入数据的自定义方法
     * 可以将导入顺序插入的数据库中等
     * @param m
     * @param sessionUserVO
     * @param index
     */
    @Override
    public void customExcelInsert(QueryModel m, ILoginUserEntity<PK> sessionUserVO, int index){
        //m.setImportOrder(index);
    }

    /**
     * 解析数据
     * @param list
     */
    @Override
    public void analysis(List<QueryModel> list) {
        ExcelHelper.analysis(list , this);
    }



    /**
     * 分析处理外键属性数据
     * @param list
     * @param column
     * @param fkAnnotation
     */

    @Override
    public void analysisFk(List<QueryModel> list,
                           Column column,
                           EntityAttrFkAnnotation fkAnnotation,
                           Map<Class, Map<String, Object>> fkKeyInfoMaps,
                           Map<Class, Map<String, Object>> fkNameInfoMaps,
                           Map<Class, Map<String, Object>> fkErrorKeyInfoMaps,
                           Map<Class, Map<String, Object>> fkErrorNameInfoMaps,
                           Map<String, Map<Field, List<Field>>> fkFieldMap) {

        ExcelHelper.analysisFk(list , column , fkAnnotation , fkKeyInfoMaps, fkNameInfoMaps ,fkErrorKeyInfoMaps , fkErrorNameInfoMaps  ,fkFieldMap , this);

    }







    /**
     * 分析处理字典类属性
     * @param list
     * @param column
     * @param dictAnnotation
     * @param dictInfoMaps
     * @param dictFieldMap
     */
    @Override
    public void analysisDict(List<QueryModel> list,
                             Column column,
                             EntityAttrDictAnnotation dictAnnotation,
                             Map<String, ?> dictInfoMaps,
                             Map<String, Map<Field, Field>> dictFieldMap) {
        ExcelHelper.analysisDict(list , column , dictAnnotation , dictInfoMaps , dictFieldMap , this);

    }

    /**
     * 分析处理普通属性数据
     * @param list
     * @param column
     */
    @Override
    public void analysisOther(List<QueryModel> list,Column column) {
        ExcelHelper.analysisOther(list , column , this);
    }








    @Override
    public List<QueryModel> getExcelData(MultipartFile file, EnumExcelFileType excelFileType){

        return ExcelHelper.getExcelData(file , excelFileType , this) ;
    }

    @Override
    public boolean isAddNumberByImport(){
        return Global.getUserConfig().getExcelAddNumber();
    }

    @Override
    public int getExcelImportHeaderRowNum(){
        if(Global.getUserConfig().getExcelExportHeader()) {
            return 2;
        }else {
            return 1;
        }
    }

    /**
     * 读取Excel数据
     * @param ei
     * @return
     */
    @Override
    public List<List<Object[]>> readExcel(ExcelImport ei){
        List<List<Object[]>> result = null;
        if(Global.getUserConfig().getExcelImportAllSheet()){
            List<List<Object[]>> list = ei.readExcelAllSheet();
            result = list;
        }else {
            result = new ArrayList<List<Object[]>>();
            result.add(ei.readExcel());
        }
        return result;
    }


    @Override
    public List<Column> getImportColumns(){
        return ColumnUtil.getExcelColumn(this.getRwEntityClass(), true);
    }


    /**
     * 获取所有的业务属性
     * @return
     */
    @Override
    public List<Field> getImportAllFields(){
        return ColumnUtil.getBusinessAllFields(this.getRwEntityClass(),BaseEntity.class);
    }



    @Override
    public void setDictNames() {

    }




}
