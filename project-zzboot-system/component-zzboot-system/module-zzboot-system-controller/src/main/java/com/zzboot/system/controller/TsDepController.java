package com.zzboot.system.controller;


import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.system.enums.EnumDepStatus;
import com.zzboot.system.bo.TsDepBO;
import com.zzboot.system.query.impl.TsDepQueryWebImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 部门 控制层
* @author Administrator
* @date 2018-9-6 23:56:31
 */
@RequestMapping("/system/dep")
@Controller
public class TsDepController extends ZzDefaultSimpleController<TsDepBO, String , TsDepQueryWebImpl> {



	@Override
	public void setCustomInfoByInsert(TsDepBO tsDepBO , ILoginUserEntity<String> sessionUser){
		tsDepBO.setDepStatus(EnumDepStatus.normal.getVal());
		tsDepBO.setDepStatusName(EnumDepStatus.normal.getLabel());

		if(StringUtils.isEmpty(tsDepBO.getOrganId())){
			tsDepBO.setOrganId((String)sessionUser.getOrganId());
		}
	}








}
