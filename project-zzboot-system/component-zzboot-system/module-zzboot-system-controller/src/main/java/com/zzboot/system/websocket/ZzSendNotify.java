package com.zzboot.system.websocket;

import com.zzboot.framework.core.db.entity.EntityUtil;
import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumYesNo;
import com.zzboot.framework.core.exceptions.InternalException;
import com.zzboot.system.bo.TsNotificationBO;
import com.zzboot.system.bo.TsNotificationReceiveBO;
import com.zzboot.system.query.VsNotificationQuery;
import com.zzboot.system.query.impl.VsNotificationQueryImpl;
import com.zzboot.system.service.TsNotificationReceiveService;
import com.zzboot.system.service.TsNotificationService;
import com.zzboot.util.base.java.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 发送通知
 * @author Administrator
 */
@Component
public class ZzSendNotify {

    @Autowired
    private TsNotificationService tsNotificationService ;


    @Autowired
    private TsNotificationReceiveService tsNotificationReceiveService ;




    @Transactional(rollbackFor = Exception.class)
    public void sendNotify(TsNotificationBO notify , List<TsNotificationReceiveBO> receiveBOs, ILoginUserEntity loginUser){
        if(notify == null){
            return ;
        }

        if(loginUser == null || StringUtils.isEmpty((String)loginUser.getId())){
            return ;
        }


        if(receiveBOs == null || receiveBOs.isEmpty()){
            throw new InternalException();
        }

        EntityUtil.autoSetInsertEntity(notify, loginUser);
        tsNotificationService.save(notify);

        for(TsNotificationReceiveBO bo : receiveBOs){
            bo.setId(IdUtils.getId());
            bo.setNotifyId(notify.getId());
        }
        tsNotificationReceiveService.saveBatch(receiveBOs);

        sendNotify(notify, receiveBOs);


    }

    @Async
    public void sendNotify(TsNotificationBO notify, List<TsNotificationReceiveBO> receiveBOs) {
        for( TsNotificationReceiveBO bo : receiveBOs) {
            VsNotificationQuery query = new VsNotificationQueryImpl();
            query.receiveUserId(bo.getReceiveUserId());
            query.isRead(EnumYesNo.NO.getCode());
            int count = tsNotificationService.count(query.buildWrapper());
            WebSocketHelp.sendMessage(notify, bo , count);
        }
    }



}
