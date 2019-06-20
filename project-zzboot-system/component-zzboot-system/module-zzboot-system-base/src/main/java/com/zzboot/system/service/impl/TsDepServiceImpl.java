package com.zzboot.system.service.impl;

import com.zzboot.framework.core.db.base.daointerface.BaseDAO;
import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.enums.EnumErrorMsg;
import com.zzboot.framework.core.exceptions.BizException;
import com.zzboot.framework.enums.EnumDictType;
import com.zzboot.system.bo.TsDepBO;
import com.zzboot.system.bo.TsDictBO;
import com.zzboot.system.bo.TsUserBO;
import com.zzboot.system.dao.TsDepDAO;
import com.zzboot.system.dao.TsUserDAO;
import com.zzboot.system.service.TsDepService;
import com.zzboot.system.service.TsDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 部门 ServiceImpl
 * @author Administrator
 * @date 2019-4-10 18:19:33
 */
@Service
public class TsDepServiceImpl extends SystemBaseServiceImpl<TsDepBO,String> implements TsDepService {



	@Autowired
	private TsDictService tsDictService;



	@Autowired
	private TsUserDAO tsUserDAO;


	@Autowired
	private TsDepDAO tsDepDAO ;



	@Override
	public BaseDAO getDAO() {
		return tsDepDAO ;
	}



	@Override
	public TsDepBO processResult(TsDepBO tsDepBO) {


		if(StringUtils.isNotEmpty( tsDepBO.getPid())){
			TsDepBO temp = tsDepDAO.selectById( tsDepBO.getPid() );
			if(temp != null){
				tsDepBO.setPname(temp.getDepName());
			}
		}

		if(StringUtils.isNotEmpty( tsDepBO.getLeadUserId())){
			TsUserBO temp = tsUserDAO.selectById( tsDepBO.getLeadUserId() );
			if(temp != null){
				tsDepBO.setLeadUserName(temp.getUserName());
			}
		}
		try {
			if(StringUtils.isEmpty(tsDepBO.getDepStatusName()) && StringUtils.isNotEmpty(tsDepBO.getDepStatus()) ) {
				String dictName = tsDictService.getDictName(tsDepBO.getDepStatus(), EnumDictType.DEP_STATUS.getVal());
				tsDepBO.setDepStatusName(dictName);
			}
		}catch(Exception e){

		}

		return tsDepBO;

	}





	@Override
	public List<TsDepBO> processResult(List<TsDepBO> tsDepBOs) {
		if(tsDepBOs == null || tsDepBOs.isEmpty()){
			return tsDepBOs;
		}

		List<String> leadUserIdList = new ArrayList<String>();
		List<String> pidList = new ArrayList<String>();

		for(TsDepBO bo : tsDepBOs)		{

			if(StringUtils.isNotEmpty( bo.getLeadUserId())){
				leadUserIdList.add(bo.getLeadUserId());
			}
			if(StringUtils.isNotEmpty( bo.getPid())){
				pidList.add(bo.getPid());
			}
		}


		if(!leadUserIdList.isEmpty()){
			List<TsUserBO> list =  tsUserDAO.selectBatchIds(leadUserIdList);
			Map<String,TsUserBO> map = EntityUtil.list2Map(list);

			tsDepBOs.forEach(tsDepBO -> {
				if(StringUtils.isNotEmpty( tsDepBO.getLeadUserId())){
					TsUserBO temp = map.get( tsDepBO.getLeadUserId() );
					if(temp != null){
						tsDepBO.setLeadUserName(temp.getUserName());
					}
				}
			});
		}

		if(!pidList.isEmpty()){
			List<TsDepBO> list =  tsDepDAO.selectBatchIds(pidList);
			Map<String,TsDepBO> map = EntityUtil.list2Map(list);

			tsDepBOs.forEach(tsDepBO -> {
				if(StringUtils.isNotEmpty( tsDepBO.getPid())){
					TsDepBO temp = map.get( tsDepBO.getPid() );
					if(temp != null){
						tsDepBO.setPname(temp.getDepName());
					}
				}
			});
		}



		String[] dictTypes = new String[]{EnumDictType.DEP_STATUS.getVal()};
		Map<String , TsDictBO> dictMap = tsDictService.allDict(dictTypes);

		tsDepBOs.forEach(tsDepBO -> {
			if(StringUtils.isEmpty(tsDepBO.getDepStatusName()) && StringUtils.isNotEmpty(tsDepBO.getDepStatus()) ) {
				TsDictBO dict = dictMap.get(EnumDictType.DEP_STATUS.getVal() + tsDepBO.getDepStatus());
				if(dict != null) {
					tsDepBO.setDepStatusName(dict.getDictName());
				}
			}
		});


		return tsDepBOs;
	}





	@Override
	public void isExist(TsDepBO tsDepBO) {

		TsDepBO ckBO ;
		TsDepBO temp = null ;

		ckBO = new TsDepBO();
		ckBO.setId( tsDepBO.getId() );
		ckBO.setDepCode(tsDepBO.getDepCode());
		ckBO.setOrganId(tsDepBO.getOrganId());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"    ");
		}
		ckBO = new TsDepBO();
		ckBO.setId( tsDepBO.getId() );
		ckBO.setDepName(tsDepBO.getDepName());
		ckBO.setOrganId(tsDepBO.getOrganId());
		temp = this.selectCheck(ckBO);
		if (EntityUtil.isEntityExist(temp)) {
			throw new BizException(EnumErrorMsg.business_error.getCode(),"    ");
		}

	}


}