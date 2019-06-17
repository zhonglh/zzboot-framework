package com.zzboot.framework.core.generics;

import com.zzboot.util.spring.ReflectionUtil;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.util.config.annotaions.EntityAttrCheckAnnotation;
import com.zzboot.util.config.annotaions.EntityAttrDBAnnotation;
import com.zzboot.util.config.annotaions.EntityAttrPageAnnotation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 处理类属性中的所有注解
 * 将所有的注解汇聚在 AnnotaionEntity 类里
 * @author Administrator
 */
@Component
public class AnnotaionEntityManager {



    public List<AnnotaionEntity>  takeAnnotaions(Class clz){
        List<AnnotaionEntity> list = new ArrayList<AnnotaionEntity>();
        List<Field> fields = ReflectionUtil.getBusinessFields(clz , EntityAttrPageAnnotation.class);
        if(fields == null || fields.isEmpty()){
            return list;
        }

        //用于处理同组的序号， 将同组的列放在一起
        Map<String,List<AnnotaionEntity>> groupMap = new HashMap<String,List<AnnotaionEntity>>();

        for(Field field : fields ){

            Class<?> ft = field.getType();

            AnnotaionEntity ae = new AnnotaionEntity();

            if(!field.isAnnotationPresent(EntityAttrDBAnnotation.class)){
                ae.setDB(false);
                throw EnumErrorMsg.code_error.toException();
            }else {
                EntityAttrDBAnnotation dbAnnotation = field.getAnnotation(EntityAttrDBAnnotation.class);
                ae.setAttrName(dbAnnotation.attrName());
                ae.setAttrLength(dbAnnotation.attrLength());
                ae.setAttrDecimals(dbAnnotation.attrDecimals());
                ae.setNotNull(dbAnnotation.notNull());
                ae.setSort(dbAnnotation.sort());
            }

            if(field.isAnnotationPresent(EntityAttrCheckAnnotation.class)){
                EntityAttrCheckAnnotation checkAnnotation = field.getAnnotation(EntityAttrCheckAnnotation.class);
                ae.setCheck(true);
                ae.setCheckRule(checkAnnotation.checkRule());
                ae.setCheckTipMsg(checkAnnotation.checkTipMsg());
            }

            if(field.isAnnotationPresent(EntityAttrPageAnnotation.class)){
                EntityAttrPageAnnotation pageAnnotation = field.getAnnotation(EntityAttrPageAnnotation.class);
                ae.setPage(true);

                if(StringUtils.isNotEmpty(ae.getGroup())){
                    List aes = groupMap.get(ae.getGroup());
                    if(aes == null){
                        aes = new ArrayList<AnnotaionEntity>();
                        groupMap.put(ae.getGroup() , aes) ;
                    }
                    aes.add(ae);
                }
                ae.setPageElement(pageAnnotation.pageElement());
                ae.setHidden(pageAnnotation.hidden());
                ae.setReadonly(pageAnnotation.readonly());


                ae.setDefaultType(pageAnnotation.defaultType());
                ae.setDefaultVal(pageAnnotation.defaultValue());

                if(ft.isAssignableFrom(Number.class)) {
                    ae.setMin(pageAnnotation.min());
                    ae.setMax(pageAnnotation.max());
                }
            }

            list.add(ae);


        }

        if(!groupMap.isEmpty()){
            Collection<List<AnnotaionEntity>> values = groupMap.values();
            for(List<AnnotaionEntity> aes : values){
                if(aes == null || aes.size() < 2){
                    continue;
                }

                Collections.sort(aes , new Comparator<AnnotaionEntity>(){
                    @Override
                    public int compare(AnnotaionEntity o1, AnnotaionEntity o2) {
                        return o1.sort > o2.sort ? 1 : -1;
                    }
                });

                AnnotaionEntity init = aes.get(0);
                for(int index = 1 ; index < aes.size();index++ ){
                    AnnotaionEntity ae = aes.get(index);
                    ae.setSort(init.sort + index );
                }
            }
        }


        Collections.sort(list , new Comparator<AnnotaionEntity>(){
            @Override
            public int compare(AnnotaionEntity o1, AnnotaionEntity o2) {
                return o1.sort > o2.sort ? 1 : -1;
            }
        });
        return list;

    }


}
