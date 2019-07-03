package com.zzboot.system.listeners;

import com.zzboot.framework.core.enums.EnumOperationType;
import com.zzboot.framework.events.LoginLogEvent;
import com.zzboot.system.bo.TsLoginLogBO;
import com.zzboot.system.bo.TsUserBO;
import com.zzboot.system.service.TsLoginLogService;
import com.zzboot.system.service.TsUserService;
import com.zzboot.util.base.data.DateKit;
import com.zzboot.util.base.java.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


/**
 * 登陆(登出)事件监听
 * @author zhonglh
 */
@Component
public class LoginEventListener implements ApplicationListener<LoginLogEvent> {

    @Autowired
    private TsLoginLogService loginLogService;

    @Autowired
    private TsUserService userService;

    @Override
    public void onApplicationEvent(LoginLogEvent loginLogEvent) {
        String userId = (String)loginLogEvent.getUserId();
        String loginType = loginLogEvent.getLoginType();
        String ip = loginLogEvent.getIp();

        EnumOperationType operationType = EnumOperationType.getEnumByValue(loginType);

        TsUserBO loginUser = userService.getById(userId,false);


        TsLoginLogBO loginLogBO = new TsLoginLogBO();
        loginLogBO.setId(IdUtils.getId());
        loginLogBO.setOperationType(operationType.getVal());
        loginLogBO.setOperationTypeName(operationType.getLabel());
        loginLogBO.setDepId((String)loginUser.getDepId());
        loginLogBO.setDepName(loginUser.getDepName());
        loginLogBO.setOperationUserId((String)loginUser.getId());
        loginLogBO.setOperationUserName(loginUser.getUserName());
        loginLogBO.setOperationIp(ip);
        loginLogBO.setOrganId((String)loginUser.getOrganId());
        loginLogBO.setTenantId(loginUser.getTenantId());
        loginLogBO.setOperationTime(DateKit.getCurrentDate());
        loginLogService.save(loginLogBO);


    }
}
