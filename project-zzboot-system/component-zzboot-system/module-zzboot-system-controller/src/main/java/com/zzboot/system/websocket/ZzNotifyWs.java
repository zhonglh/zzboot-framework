package com.zzboot.system.websocket;


import com.zzboot.framework.core.db.entity.ILoginUserEntity;
import com.zzboot.framework.shiro.session.OnlineSessionDAO;
import com.zzboot.system.bo.TsUserBO;
import com.zzboot.util.spring.SpringUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import javax.websocket.server.ServerEndpoint;

/**
 * websocket 服务地址
 * @author Administrator
 */

@ServerEndpoint(value = "/websocket/notify/{userKey}/{userSessoinId}")
public class ZzNotifyWs extends AbstractNotifyWebSocket{


    static OnlineSessionDAO sessionDao = null;
    static {
        sessionDao = SpringUtil.getBean(OnlineSessionDAO.class);
    }

    @Override
    protected ILoginUserEntity<String> getCurrLoginUser(String userKey , String userSessoinId) {
        if(sessionDao == null){
            synchronized (ZzNotifyWs.class){
                if(sessionDao == null) {
                    sessionDao = SpringUtil.getBean(OnlineSessionDAO.class);
                }
            }
        }
        Session shiroSession = sessionDao.readSession(userSessoinId);
        if(shiroSession == null) {
            return null;
        }else {
            SimplePrincipalCollection  sc = (SimplePrincipalCollection)shiroSession.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

            Object userObj = sc.getPrimaryPrincipal() ;
            if(userObj != null && userObj instanceof TsUserBO){
                return (TsUserBO)userObj;
            }
        }
        return null;
    }

    @Override
    protected void logout(String userKey  , String userSessoinId) {
        /*Session shiroSession = sessionDao.readSession(userSessoinId);
        if(shiroSession != null) {
            sessionDao.delete(shiroSession);
        }*/
    }
}
