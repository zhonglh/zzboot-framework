package com.zzboot.framework.shiro.web.session;

import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.core.enums.EnumOperationType;
import com.zzboot.framework.events.LoginLogEvent;
import com.zzboot.framework.shiro.session.OnlineSessionDAO;
import com.zzboot.framework.shiro.utils.ShiroUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * 类ShiroSessionListener的功能描述:
 * 发现用户登出后，Session没有从Redis中销毁，虽然当前重新new了一个，但会对统计带来干扰，通过SessionListener解决这个问题
 */
public class ShiroSessionListener extends SessionListenerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    @Override
    public void onStart(Session session) {
        // 会话创建时触发
        logger.info("ShiroSessionListener session {} 被创建", session.getId());
    }

    @Override
    public void onStop(Session session) {

        try {
            ILoginUserEntity loginUser = (ILoginUserEntity) ShiroUtils.getSubject().getPrincipal();
            if (loginUser != null) {
                LoginLogEvent le = new LoginLogEvent(new Date());
                le.setIp(null);
                le.setLoginType(EnumOperationType.LOGOUT_TIMEOUT.getVal());
                le.setUserId(loginUser.getId());
                applicationContext.publishEvent(le);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        onlineSessionDAO.delete(session);
        // 会话被停止时触发
        logger.info("ShiroSessionListener session {} 被销毁", session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        onlineSessionDAO.delete(session);
        //会话过期时触发
        logger.info("ShiroSessionListener session {} 过期", session.getId());
    }
}
