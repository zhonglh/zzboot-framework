package com.zzboot.framework.controller;

import com.zz.bms.core.db.entity.BaseEntity;
import com.zz.bms.core.db.entity.ILoginUserEntity;
import com.zz.bms.util.configs.annotaions.EntityAttrDictAnnotation;
import com.zz.bms.util.configs.annotaions.EntityAttrFkAnnotation;
import com.zz.bms.util.poi.enums.EnumExcelFileType;
import com.zz.bms.util.poi.export.ExcelExport;
import com.zz.bms.util.poi.imports.ExcelImport;
import com.zz.bms.util.poi.vo.Column;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface IExcelConttroller<RwModel extends BaseEntity<PK>,
        QueryModel extends RwModel,
        PK extends Serializable>
        extends ICURDController<RwModel , QueryModel , PK> {










    public String[] getExcelHeaderInfo() ;


    public void exportTitles(ExcelExport<QueryModel> aee, int header, QueryModel m, boolean isAddNumber);


    public boolean isAddNumberByExport() ;


    public void exportHeader(ExcelExport<QueryModel> aee, String[] headerInfo);

    public void exportContent(ExcelExport<QueryModel> aee, List<QueryModel> contents, int rowIndex, boolean isAddNumber) ;

    public void exportXls(ExcelExport<QueryModel> aee, HttpServletResponse response) throws RuntimeException ;










    public void customExcelInsert(QueryModel m, ILoginUserEntity<PK> sessionUserVO, int index);


    /**
     * 解析数据
     * @param list
     */
    public void analysis(List<QueryModel> list)  ;


    public void analysisFk(List<QueryModel> list,
                           Column column,
                           EntityAttrFkAnnotation fkAnnotation,
                           Map<Class, Map<String, Object>> fkKeyInfoMaps,
                           Map<Class, Map<String, Object>> fkNameInfoMaps,
                           Map<Class, Map<String, Object>> fkErrorKeyInfoMaps,
                           Map<Class, Map<String, Object>> fkErrorNameInfoMaps,
                           Map<String, Map<Field, List<Field>>> fkFieldMap) ;

    public void analysisDict(List<QueryModel> list,
                             Column column,
                             EntityAttrDictAnnotation dictAnnotation,
                             Map<String, ?> dictInfoMaps,
                             Map<String, Map<Field, Field>> dictFieldMap) ;


    public void analysisOther(List<QueryModel> list, Column column) ;



    public <QueryModel> List<QueryModel> getExcelData(MultipartFile file, EnumExcelFileType excelFileType) ;


    public List<List<Object[]>> readExcel(ExcelImport ei) ;


    public int getExcelImportHeaderRowNum() ;


    public List<Column> getImportColumns() ;


    public List<Field> getImportAllFields() ;


    public void setDictNames() ;

    public boolean isAddNumberByImport() ;




}
