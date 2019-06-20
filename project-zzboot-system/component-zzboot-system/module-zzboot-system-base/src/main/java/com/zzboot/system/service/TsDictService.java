package com.zzboot.system.service;

import com.zzboot.framework.core.db.base.service.BaseService;
import com.zzboot.system.bo.TsDictBO;

import java.util.List;
import java.util.Map;


/**
* 字典信息 Service
* @author Administrator
* @date 2019-4-10 11:08:53
*/
public interface TsDictService extends BaseService<TsDictBO , String> {

    /**
     * 获取字典名称
     * @param value         字典值
     * @param dictType      字典类型
     * @return
     */
    public String getDictName(String value, String dictType) ;



    /**
     * 获取字典值
     * @param name         字典名称
     * @param dictType      字典类型
     * @return
     */
    public String getDictValue(String name, String dictType) ;

    /**
     * 获取所有的字典
     * @return
     */
    public Map<String , TsDictBO> allDict(String... dictTypes) ;


    /**
     * 获取所有的字典
     * @return
     */
    public Map<String , List<TsDictBO>> allDicts(String... dictTypes) ;


    /**
     * 获取所有的字典名称
     * @return
     */
    public Map<String , List<String>> allDictNames(String... dictTypes) ;
}
