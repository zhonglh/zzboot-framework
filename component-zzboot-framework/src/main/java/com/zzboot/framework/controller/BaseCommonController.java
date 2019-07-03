package com.zzboot.framework.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzboot.framework.DefaultUtil;
import com.zzboot.framework.PermissionList;
import com.zzboot.framework.core.Constant;
import com.zzboot.framework.core.db.entity.BaseBusinessEntity;
import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumDefaultType;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.framework.core.ui.Pages;
import com.zzboot.framework.core.ui.TreeModel;
import com.zzboot.framework.core.ui.easyui.EasyUiDataGrid;
import com.zzboot.framework.core.ui.easyui.EasyUiTreeGrid;
import com.zzboot.framework.core.ui.easyui.EasyUiUtil;
import com.zzboot.util.base.BaseValidateUtils;
import com.zzboot.util.base.data.SerializableUtil;
import com.zzboot.util.base.data.StringUtil;
import com.zzboot.util.config.annotaions.*;
import com.zzboot.util.config.util.AnnotaionEntityUtil;
import com.zzboot.util.spring.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */
public class BaseCommonController<PK extends Serializable> extends BaseController<PK>  {


    /**
     * 判断列表是否需要设置一些公共信息
     */
    protected boolean listAlsoSetCommonData = true;

    /**
     * 本功能总的权限列表
     */
    protected PermissionList permissionList = null;

    /**
     * 本功能的资源名称
     */
    private String resourceIdentity = null;







    /**
     * 列表也设置common data
     */
    public void setListAlsoSetCommonData(boolean listAlsoSetCommonData) {
        this.listAlsoSetCommonData = listAlsoSetCommonData;
    }



    /**
     * 权限前缀：如sys:user
     * 则生成的新增权限为 sys:user:create
     */
    protected void setResourceIdentity(String resourceIdentity) {
        if (!org.springframework.util.StringUtils.isEmpty(resourceIdentity)) {
            this.resourceIdentity = resourceIdentity ;
            if(permissionList == null) {
                permissionList = PermissionList.newPermissionList(resourceIdentity);
            }else {
                PermissionList temp = PermissionList.newPermissionList(resourceIdentity);
                if(temp != null && temp.getResourcePermissions() != null && !temp.getResourcePermissions().isEmpty()) {
                    for(Map.Entry<String, String> entry :  temp.getResourcePermissions().entrySet()) {
                        permissionList.getResourcePermissions().put(entry.getKey(),entry.getValue());
                    }
                }
            }
        }
    }




    protected BaseEntity<PK> newModel(Class<BaseEntity<PK>> clz) {
        try {
            return clz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can not instantiated model : " + clz, e);
        }
    }



    /**
     * 设置初始值 ,  一般用于新增界面
     * @param be
     */
    public void setInit(BaseEntity be){
        if(be == null){
            return ;
        }

        try {
            List<Field> fields = ReflectionUtil.getBusinessFields(be.getClass() , EntityAttrPageAnnotation.class);
            for(Field field : fields){
                EntityAttrPageAnnotation pageAnnotation = field.getAnnotation(EntityAttrPageAnnotation.class);
                if(StringUtils.isNotEmpty(pageAnnotation.defaultType())){
                    EnumDefaultType defaultType = EnumDefaultType.getEnumByValue(pageAnnotation.defaultType());
                    if (defaultType != null && defaultType != EnumDefaultType.CUSTOM) {
                        field.setAccessible(true);
                        Object fieldVal = ReflectionUtil.getField(field , be);
                        if(!BaseValidateUtils.isEmpty(fieldVal)) {
                            Object obj = DefaultUtil.getDefaultValue(defaultType, this.getSessionUser());
                            field.setAccessible(true);
                            field.set(be, obj);
                        }
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 插入对象加上插入信息
     * @param be
     * @param sessionUserVO
     */
    public void setInsertInfo(BaseEntity be , ILoginUserEntity sessionUserVO){
        EntityUtil.autoSetInsertEntity(be, sessionUserVO);
    }


    /**
     * 修改对象 将 加上修改信息
     * @param be
     * @param sessionUserVO
     */
    public void setUpdateInfo(BaseEntity be , ILoginUserEntity sessionUserVO){
        EntityUtil.autoSetUpdateEntity(be, sessionUserVO);
    }


    /**
     * 将列表数据组织为子属性中
     * @param list
     * @param treeModel
     * @return
     */
    public EasyUiTreeGrid toTreeList(List list , TreeModel treeModel){
        if(list == null) {
            return new EasyUiTreeGrid();
        }
        return toTreeList(list , treeModel , null);
    }



    /**
     * 将列表数据组织为子属性中
     * @param list
     * @param treeModel
     * @return
     */
    public EasyUiTreeGrid toTreeList(List list , TreeModel treeModel , List footer){
        if(list == null) {
            return new EasyUiTreeGrid();
        }
        return EasyUiUtil.toTreeList(list , treeModel , footer);
    }

    /**
     * 将公共的列表信息转换为UI中需要的格式
     * @param pages
     * @return
     */
    public EasyUiDataGrid toList(Pages pages ){
        return EasyUiUtil.toDataGrid(pages);
    }

    public EasyUiDataGrid toList(Page page ){
        return EasyUiUtil.toDataGrid(page);
    }








    public void checkInsertInfo(BaseEntity entity){
        if(entity == null || SerializableUtil.isEmpty(entity.getId())){
            throw EnumErrorMsg.code_error.toException();
        }


        if(entity instanceof BaseBusinessEntity){
            BaseBusinessEntity businessEntity = (BaseBusinessEntity)entity;
            if(SerializableUtil.isEmpty(businessEntity.getCreateUserId())){
                throw EnumErrorMsg.code_error.toException();
            }
            if(businessEntity.getCreateTime() == null){
                throw EnumErrorMsg.code_error.toException();
            }
            if(businessEntity.getVersionNo() == null){
                businessEntity.setVersionNo(Constant.INIT_VERSION);
            }
        }
    }


    /**
     * 检查实体数据的合法性
     * @param entity            需要检查的实体
     * @param checkRequired     是否检查必填
     * @param checkLength       是否检查长度
     * @param checkRule         是否检查规则
     */
    public void checkEntityLegality(BaseEntity entity , boolean checkRequired , boolean checkLength , boolean checkRule){




        List<Field> list = ReflectionUtil.getBusinessFields(entity.getClass(),BaseEntity.class);

        if(list != null && !list.isEmpty()){

            for (Field field : list) {

                //不存在的列(扩展列)不校验
                TableField tableField = field.getAnnotation(TableField.class);
                if(tableField != null && !tableField.exist()){
                    continue;
                }

                EntityAttrDBAnnotation dbAnnotation = field.getAnnotation(EntityAttrDBAnnotation.class);
                EntityAttrPageAnnotation pageAnnotation = field.getAnnotation(EntityAttrPageAnnotation.class);
                EntityAttrDictAnnotation dictAnnotation = field.getAnnotation(EntityAttrDictAnnotation.class);
                EntityAttrFkAnnotation fkAnnotation = field.getAnnotation(EntityAttrFkAnnotation.class);

                String name = this.getMessage(entity.getClass().getName() + "." + field.getName() , pageAnnotation.title());

                ReflectionUtil.makeAccessible(field);
                Object val = ReflectionUtil.getField(field, entity);
                boolean isEmpty = StringUtil.isEmpty(val);

                //检查必填
                if(checkRequired) {
                    if (isEmpty && AnnotaionEntityUtil.isRequired(dbAnnotation, fkAnnotation, dictAnnotation, pageAnnotation)) {
                        String msg = this.getMessage(EnumErrorMsg.check_data_required.getI18n(),name+" 为必填项", name);
                        throw new BizException(EnumErrorMsg.check_data_required.getCode(), msg);
                    }
                }

                if(checkLength && !isEmpty) {
                    //检查长度
                    int maxLength = AnnotaionEntityUtil.maxLength(dbAnnotation, fkAnnotation, dictAnnotation, pageAnnotation);

                    if ( maxLength > 0) {

                        String msg = this.getMessage(EnumErrorMsg.check_data_too.getI18n(), name+" 数据太长", name);

                        if (val instanceof String) {
                            if (((String) val).length() > maxLength) {
                                throw new BizException(EnumErrorMsg.check_data_too.getCode(), msg);
                            }
                        }

                        if (val instanceof Double || val instanceof BigDecimal) {
                            String str = val.toString();
                            int count = str.length();
                            boolean havePoint = (str.indexOf("\\.") > 0);
                            if ((str.length() - 1) > maxLength) {
                                throw new BizException(EnumErrorMsg.check_data_too.getCode(), msg);
                            } else {
                                int integerCount = count;
                                if(str.indexOf("\\.") > 0){
                                    integerCount = str.indexOf("\\.");
                                }
                                if (integerCount > maxLength - AnnotaionEntityUtil.decimalsLength(dbAnnotation, fkAnnotation)) {
                                    msg = this.getMessage(EnumErrorMsg.check_decimal_too.getI18n(),name+" 小数位太长", name);
                                    throw new BizException(EnumErrorMsg.check_decimal_too.getCode(), msg);
                                }
                            }
                        }
                    }
                }


                if(checkRule) {
                    //检查规则
                    EntityAttrCheckAnnotation eac = field.getAnnotation(EntityAttrCheckAnnotation.class);

                    if (eac != null && eac.checkRule() != null && eac.checkRule().length > 0 && val != null) {
                        for (String cr : eac.checkRule()) {
                            if (!Pattern.matches(cr, val.toString())) {
                                String msg = this.getMessage(EnumErrorMsg.check_data_format.getI18n(), name+" 格式错误", name);
                                throw new BizException(EnumErrorMsg.check_data_format.getCode(), msg);
                            }
                        }
                    }
                }

            }

        }

    }













    public void assertHasCreatePermission() {
        permissionList.assertHasCreatePermission();
    }

    public void assertHasUpdatePermission() {
        permissionList.assertHasUpdatePermission();
    }

    public void assertHasDeletePermission() {
        permissionList.assertHasDeletePermission();
    }

    public void assertHasViewPermission() {
        permissionList.assertHasViewPermission();
    }


    public void assertHasImportPermission() {
        permissionList.assertHasImportPermission();
    }

    public void assertHasExportPermission() {
        permissionList.assertHasExportPermission();
    }


    /**
     * 即增删改中的任何一个即可
     */
    public void assertHasEditPermission() {
        permissionList.assertHasEditPermission();
    }


    /**
     * 增加或者查看
     */
    public void assertHasAddorViewPermission() {
        permissionList.assertHasAddorViewPermission();
    }

    public void assertHasPermission(String permission) {
        permissionList.assertHasPermission(permission);
    }

    public void assertHasPermission(String permission, String errorCode) {
        permissionList.assertHasPermission(permission,errorCode);
    }



}
