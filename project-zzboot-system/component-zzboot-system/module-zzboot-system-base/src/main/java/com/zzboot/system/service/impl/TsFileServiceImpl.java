package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.enums.EnumDictType;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsFileBO;
import com.zzboot.system.dao.TsFileDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 文件 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:25:13
 */
@Service
public class TsFileServiceImpl extends SystemBaseServiceImpl<TsFileBO,String> implements TsFileService {



	@Autowired
	private TsDictService tsDictService;





	@Autowired
	private TsFileDAO tsFileDAO ;



	@Override
	public BaseDAO getDAO() {
		return tsFileDAO ;
	}



	@Override
	public TsFileBO processResult(TsFileBO tsFileBO) {

		try {
			if(StringUtils.isEmpty(tsFileBO.getFileEngineName()) && StringUtils.isNotEmpty(tsFileBO.getFileEngine()) ) {
				String dictName = tsDictService.getDictName(tsFileBO.getFileEngine(),EnumDictType.FILE_ENGINE.getVal());
				tsFileBO.setFileEngineName(dictName);
			}
		}catch(Exception e){

		}

		return tsFileBO;

	}





	@Override
	public List<TsFileBO> processResult(List<TsFileBO> tsFileBOs) {
		if(tsFileBOs == null || tsFileBOs.isEmpty()){
			return tsFileBOs;
		}


		String[] dictTypes = new String[]{EnumDictType.FILE_ENGINE.getVal()};
		Map<String , TsDictBO> dictMap = tsDictService.allDict(dictTypes);

		tsFileBOs.forEach(tsFileBO -> {
			if(StringUtils.isEmpty(tsFileBO.getFileEngineName()) && StringUtils.isNotEmpty(tsFileBO.getFileEngine()) ) {
				TsDictBO dict = dictMap.get(EnumDictType.FILE_ENGINE.getVal() + tsFileBO.getFileEngine());
				if(dict != null) {
					tsFileBO.setFileEngineName(dict.getDictName());
				}
			}
		});



		return tsFileBOs;
	}




	@Override
	public void isExist(TsFileBO tsFileBO) {
	}


}