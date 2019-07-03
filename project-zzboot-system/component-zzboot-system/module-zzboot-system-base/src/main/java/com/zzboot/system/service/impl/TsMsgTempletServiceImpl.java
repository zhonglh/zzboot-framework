package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.system.enums.EnumSystemDictType;
import com.zzboot.system.bo.TsDepBO;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsMsgTempletBO;
import com.zzboot.system.dao.TsDepDAO;
import com.zzboot.system.dao.TsMsgTempletDAO;
import com.zzboot.system.service.TsDictService;
import com.zzboot.system.service.TsMsgTempletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息模板 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:25:15
 */
@Service
public class TsMsgTempletServiceImpl extends SystemBaseServiceImpl<TsMsgTempletBO,String> implements TsMsgTempletService {



	@Autowired
	private TsDictService tsDictService;



	@Autowired
	private TsDepDAO tsDepDAO;


	@Autowired
	private TsMsgTempletDAO tsMsgTempletDAO ;



	@Override
	public BaseDAO getDAO() {
		return tsMsgTempletDAO ;
	}



	@Override
	public TsMsgTempletBO processResult(TsMsgTempletBO tsMsgTempletBO) {


		if(StringUtils.isNotEmpty( tsMsgTempletBO.getDepId())){
			TsDepBO temp = tsDepDAO.selectById( tsMsgTempletBO.getDepId() );
			if(temp != null){
				tsMsgTempletBO.setDepName(temp.getDepName());
			}
		}
		try {
			if(StringUtils.isEmpty(tsMsgTempletBO.getTempletEffectiveName()) && StringUtils.isNotEmpty(tsMsgTempletBO.getTempletEffective()) ) {
				String dictName = tsDictService.getDictName(tsMsgTempletBO.getTempletEffective(), EnumSystemDictType.YES_NO.getVal());
				tsMsgTempletBO.setTempletEffectiveName(dictName);
			}
		}catch(Exception e){

		}
		try {
			if(StringUtils.isEmpty(tsMsgTempletBO.getMsgLanguageName()) && StringUtils.isNotEmpty(tsMsgTempletBO.getMsgLanguage()) ) {
				String dictName = tsDictService.getDictName(tsMsgTempletBO.getMsgLanguage(), EnumSystemDictType.MSG_LANGUAGE.getVal());
				tsMsgTempletBO.setMsgLanguageName(dictName);
			}
		}catch(Exception e){

		}
		try {
			if(StringUtils.isEmpty(tsMsgTempletBO.getMsgTempletTypeName()) && StringUtils.isNotEmpty(tsMsgTempletBO.getMsgTempletType()) ) {
				String dictName = tsDictService.getDictName(tsMsgTempletBO.getMsgTempletType(), EnumSystemDictType.MSG_TEMPLET_TYPE.getVal());
				tsMsgTempletBO.setMsgTempletTypeName(dictName);
			}
		}catch(Exception e){

		}

		return tsMsgTempletBO;

	}





	@Override
	public List<TsMsgTempletBO> processResult(List<TsMsgTempletBO> tsMsgTempletBOs) {
		if(tsMsgTempletBOs == null || tsMsgTempletBOs.isEmpty()){
			return tsMsgTempletBOs;
		}

		List<String> depIdList = new ArrayList<String>();

		for(TsMsgTempletBO bo : tsMsgTempletBOs)		{

			if(StringUtils.isNotEmpty( bo.getDepId())){
				depIdList.add(bo.getDepId());
			}
		}


		if(!depIdList.isEmpty()){
			List<TsDepBO> list =  tsDepDAO.selectBatchIds(depIdList);
			Map<String,TsDepBO> map = EntityUtil.list2Map(list);

			tsMsgTempletBOs.forEach(tsMsgTempletBO -> {
				if(StringUtils.isNotEmpty( tsMsgTempletBO.getDepId())){
					TsDepBO temp = map.get( tsMsgTempletBO.getDepId() );
					if(temp != null){
						tsMsgTempletBO.setDepName(temp.getDepName());
					}
				}
			});
		}





		String[] dictTypes = new String[]{EnumSystemDictType.YES_NO.getVal(), EnumSystemDictType.MSG_LANGUAGE.getVal(), EnumSystemDictType.MSG_TEMPLET_TYPE.getVal()};
		Map<String , TsDictBO> dictMap = tsDictService.allDict(dictTypes);

		tsMsgTempletBOs.forEach(tsMsgTempletBO -> {
			if(StringUtils.isEmpty(tsMsgTempletBO.getTempletEffectiveName()) && StringUtils.isNotEmpty(tsMsgTempletBO.getTempletEffective()) ) {
				TsDictBO dict = dictMap.get(EnumSystemDictType.YES_NO.getVal() + tsMsgTempletBO.getTempletEffective());
				if(dict != null) {
					tsMsgTempletBO.setTempletEffectiveName(dict.getDictName());
				}
			}
			if(StringUtils.isEmpty(tsMsgTempletBO.getMsgLanguageName()) && StringUtils.isNotEmpty(tsMsgTempletBO.getMsgLanguage()) ) {
				TsDictBO dict = dictMap.get(EnumSystemDictType.MSG_LANGUAGE.getVal() + tsMsgTempletBO.getMsgLanguage());
				if(dict != null) {
					tsMsgTempletBO.setMsgLanguageName(dict.getDictName());
				}
			}
			if(StringUtils.isEmpty(tsMsgTempletBO.getMsgTempletTypeName()) && StringUtils.isNotEmpty(tsMsgTempletBO.getMsgTempletType()) ) {
				TsDictBO dict = dictMap.get(EnumSystemDictType.MSG_TEMPLET_TYPE.getVal() + tsMsgTempletBO.getMsgTempletType());
				if(dict != null) {
					tsMsgTempletBO.setMsgTempletTypeName(dict.getDictName());
				}
			}
		});



		return tsMsgTempletBOs;
	}




	@Override
	public void isExist(TsMsgTempletBO tsMsgTempletBO) {

		TsMsgTempletBO ckBO ;
		TsMsgTempletBO temp = null ;

		ckBO = new TsMsgTempletBO();
		ckBO.setId( tsMsgTempletBO.getId() );
		ckBO.setMsgTempletName(tsMsgTempletBO.getMsgTempletName());
		ckBO.setTenantId(tsMsgTempletBO.getTenantId());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"模板名称已经使用");
		}


	}


}