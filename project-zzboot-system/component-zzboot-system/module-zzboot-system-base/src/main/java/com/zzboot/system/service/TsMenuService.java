package com.zzboot.system.service;

import com.zzboot.framework.core.db.base.service.BaseService;
import com.zzboot.system.bo.TsMenuBO;

import java.util.List;


/**
* 菜单 Service
* @author Administrator
* @date 2019-4-10 11:08:55
*/
public interface TsMenuService extends BaseService<TsMenuBO , String> {
    public List<TsMenuBO> getAllMenu(TsMenuBO tsMenuBO);
}
