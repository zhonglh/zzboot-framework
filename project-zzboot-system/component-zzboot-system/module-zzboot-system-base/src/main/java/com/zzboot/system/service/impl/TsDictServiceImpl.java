package com.zzboot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.BaseEntity;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.exceptions.DbException;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsDictTypeBO;
import com.zzboot.system.dao.TsDictDAO;
import com.zzboot.system.dao.TsDictTypeDAO;
import com.zzboot.system.dao.TsTenantDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.util.base.data.SqlKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 字典信息 ServiceImpl
* @author Administrator
* @date 2018-9-6 23:56:30
*/
@Service
public class TsDictServiceImpl extends SystemBaseServiceImpl<TsDictBO,String> implements TsDictService {



    @Autowired
    private TsDictTypeDAO tsDictTypeDAO;
    @Autowired
    private TsTenantDAO tsTenantDAO;


	@Autowired
	private TsDictDAO tsDictDAO ;


	@Override
	public BaseDAO getDAO() {
		return tsDictDAO ;
	}



	@Override
	public String getDictName(String value , String dictType) {
		if(value == null || dictType == null){
			return "";
		}

		QueryWrapper<TsDictBO> queryWrapper = new QueryWrapper<TsDictBO>();
		queryWrapper.lambda().eq(TsDictBO::getDictVal ,  value)
				.inSql(TsDictBO::getDictTypeId , "select id from ts_dict_type where dict_type_code = '"+dictType+"'");
		TsDictBO tsDictBO = tsDictDAO.selectOne(queryWrapper);
		if(tsDictBO == null){
			return "";
		}else {
			return tsDictBO.getDictName();
		}
	}




	@Override
	public String getDictValue(String name , String dictType) {

		if(name == null || dictType == null){
			return null;
		}

		QueryWrapper<TsDictBO> queryWrapper = new QueryWrapper<TsDictBO>();
		queryWrapper.lambda().eq(TsDictBO::getDictName ,  name)
				.inSql(TsDictBO::getDictTypeId , "select id from ts_dict_type where dict_type_code = '"+dictType+"'");

		TsDictBO tsDictBO = tsDictDAO.selectOne(queryWrapper);
		if(tsDictBO == null){
			return null;
		}else {
			return tsDictBO.getDictVal();
		}
	}




	/**
	 * 根据字典类型获取字典信息
	 * @param dictTypes
	 * @return
	 */
	@Override
	public Map<String , TsDictBO> allDict(String ... dictTypes) {

		Map<String , TsDictBO> result = new HashMap<String , TsDictBO>();
		if(dictTypes == null || dictTypes.length == 0){
			return result;
		}

		List<TsDictTypeBO> dictTypeBOS = tsDictTypeDAO.selectList(Wrappers.emptyWrapper());
		if(dictTypeBOS == null || dictTypeBOS.isEmpty()){
			return result;
		}
		Map<String,TsDictTypeBO> dictTypeBOMap = new HashMap<String,TsDictTypeBO>();
		for(TsDictTypeBO dictTypeBO : dictTypeBOS){
			dictTypeBOMap.put(dictTypeBO.getId() , dictTypeBO);
		}


		QueryWrapper<TsDictBO> queryWrapper = new QueryWrapper<TsDictBO>();
		queryWrapper.inSql("dict_type_id" , " select id from ts_dict_type where dict_type_code in ("+ SqlKit.toInContent(dictTypes)+") " );
		queryWrapper.orderByAsc("orderby");
		List<TsDictBO> dictBOS = tsDictDAO.selectList(queryWrapper);
		if(dictBOS == null || dictBOS.isEmpty()){
			return result;
		}
		for(TsDictBO dictBO : dictBOS){
			TsDictTypeBO dictTypeBO = dictTypeBOMap.get(dictBO.getDictTypeId());

			String key = dictTypeBO.getDictTypeCode()+dictBO.getDictVal();
			result.put(key ,dictBO);
			key = dictTypeBO.getDictTypeCode()+dictBO.getDictName();
			result.put(key ,dictBO);
		}

		return result;

	}




	@Override
	public Map<String , List<TsDictBO>> allDicts(String ... dictTypes){

		Map<String , List<TsDictBO>> result = new HashMap<String , List<TsDictBO>>();
		if(dictTypes == null || dictTypes.length == 0){
			return result;
		}

		List<TsDictTypeBO> dictTypeBOS = tsDictTypeDAO.selectList(Wrappers.emptyWrapper());
		if(dictTypeBOS == null || dictTypeBOS.isEmpty()){
			return result;
		}
		Map<String,TsDictTypeBO> dictTypeBOMap = new HashMap<String,TsDictTypeBO>();
		for(TsDictTypeBO dictTypeBO : dictTypeBOS){
			dictTypeBOMap.put(dictTypeBO.getId() , dictTypeBO);
		}



		QueryWrapper<TsDictBO> queryWrapper = new QueryWrapper<TsDictBO>();
		queryWrapper.inSql("dict_type_id" , " select id from ts_dict_type where dict_type_code in ("+ SqlKit.toInContent(dictTypes)+") " );
		queryWrapper.orderByAsc("orderby");
		List<TsDictBO> dictBOS = tsDictDAO.selectList(queryWrapper);
		if(dictBOS == null || dictBOS.isEmpty()){
			return result;
		}
		for(TsDictBO dictBO : dictBOS){
			TsDictTypeBO dictTypeBO = dictTypeBOMap.get(dictBO.getDictTypeId());
			if(dictTypeBO == null){

				continue;
			}

			List<TsDictBO> list = result.get(dictTypeBO.getDictTypeCode().toLowerCase());
			if(list == null){
				list = new ArrayList<TsDictBO>();
				result.put(dictTypeBO.getDictTypeCode().toLowerCase() , list);
			}
			list.add(dictBO);
		}

		return result;



	}

	@Override
	public Map<String, List<String>> allDictNames(String... dictTypes) {

		Map<String , List<String>> result = new HashMap<String , List<String>>();
		if(dictTypes == null || dictTypes.length == 0){
			return result;
		}

		List<TsDictTypeBO> dictTypeBOS = tsDictTypeDAO.selectList(Wrappers.emptyWrapper());
		if(dictTypeBOS == null || dictTypeBOS.isEmpty()){
			return result;
		}
		Map<String,TsDictTypeBO> dictTypeBOMap = new HashMap<String,TsDictTypeBO>();
		for(TsDictTypeBO dictTypeBO : dictTypeBOS){
			dictTypeBOMap.put(dictTypeBO.getId() , dictTypeBO);
		}



		QueryWrapper<TsDictBO> queryWrapper = new QueryWrapper<TsDictBO>();
		queryWrapper.inSql("dict_type_id" , " select id from ts_dict_type where dict_type_code in ("+ SqlKit.toInContent(dictTypes)+") " );
		queryWrapper.orderByAsc("orderby");
		List<TsDictBO> dictBOS = tsDictDAO.selectList(queryWrapper);
		if(dictBOS == null || dictBOS.isEmpty()){
			return result;
		}
		for(TsDictBO dictBO : dictBOS){
			TsDictTypeBO dictTypeBO = dictTypeBOMap.get(dictBO.getDictTypeId());
			if(dictTypeBO == null){

				continue;
			}

			List<String> list = result.get(dictTypeBO.getDictTypeCode().toLowerCase());
			if(list == null){
				list = new ArrayList<String>();
				result.put(dictTypeBO.getDictTypeCode().toLowerCase() , list);
			}
			list.add(dictBO.getDictName());
		}

		return result;

	}


	@Override
	public void isExist(TsDictBO tsDictBO) {

		TsDictBO ckBO ;
		BaseEntity temp = null ;

		ckBO = new TsDictBO();
		ckBO.setId( tsDictBO.getId() );
		ckBO.setDictTypeId(tsDictBO.getDictTypeId());
		ckBO.setDictVal(tsDictBO.getDictVal());
		ckBO.setTenantId(tsDictBO.getTenantId());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw DbException.DB_SAVE_SAME;
		}

	}
}