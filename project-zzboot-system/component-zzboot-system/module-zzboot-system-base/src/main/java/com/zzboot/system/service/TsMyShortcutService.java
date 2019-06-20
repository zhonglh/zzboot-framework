package com.zzboot.system.service;

import com.zzboot.framework.core.db.base.service.BaseService;
import com.zzboot.system.bo.TsMyShortcutBO;


/**
* 置顶快捷菜单 Service
* @author Administrator
* @date 2019-4-10 11:08:51
*/
public interface TsMyShortcutService extends BaseService<TsMyShortcutBO , String> {

    public void saveAll(String[] menuIds, String userId) ;
}
