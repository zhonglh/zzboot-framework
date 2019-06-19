package com.zzboot.framework.shiro.session;


import com.zzboot.framework.core.db.entity.IOnlineUserEnitty;
import com.zzboot.framework.core.enums.EnumOnlineStatus;
import com.zzboot.framework.manage.AsyncManager;
import com.zzboot.framework.shiro.IOnlineUserService;
import com.zzboot.util.spring.SpringUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Date;
import java.util.TimerTask;

/**
 * 针对自定义的ShiroSession的DB操作
 * @author zhonglh
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

    /**
     * 同步session到数据库的周期 单位为毫秒（默认1分钟）
     */
    @Value("${shiro.session.dbSyncPeriod}")
    private int dbSyncPeriod;


    @Autowired
    private OnlineSessionFactory onlineSessionFactory;


    /**
     * 上次同步数据库的时间戳
     */
    private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP" ;

    @Autowired(required = false)
    private IOnlineUserService onlineUserService;

    public OnlineSessionDAO() {
        super();
    }


    /**
     * 根据会话ID获取会话
     *
     * @param sessionId 会话ID
     * @return ShiroSession
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        IOnlineUserEnitty onlineUserEnitty =  onlineUserService.selectOnlineById((String)sessionId);
        if(onlineUserEnitty == null){
            return null;
        }else {
            return onlineSessionFactory.createSession(onlineUserEnitty);
        }
    }

    /**
     * 更新会话；如更新会话最后访问时间/停止会话/设置超时时间/设置移除属性等会调用
     */
    public void syncToDb(OnlineSession onlineSession) {
        Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
        if (lastSyncTimestamp != null) {
            boolean needSync = true;
            long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
            if (deltaTime < dbSyncPeriod * 60 * 1000) {
                // 时间差不足 无需同步
                needSync = false;
            }
            boolean isGuest = onlineSession.getUserId() == null ;

            // session 数据变更了 同步
            if (!isGuest && onlineSession.isAttributeChanged()) {
                needSync = true;
            }

            if (!needSync) {
                return;
            }
        }
        onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
        // 更新完后 重置标识
        if (onlineSession.isAttributeChanged()) {
            onlineSession.resetAttributeChanged();
        }
        AsyncManager.me().execute(syncSessionToDb(onlineSession));
    }


    public static TimerTask syncSessionToDb(final OnlineSession session) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtil.getBean(IOnlineUserService.class).saveOnline(session);

            }
        };
    }

    /**
     * 当会话过期/停止（如用户退出时）属性等会调用
     */
    @Override
    protected void doDelete(Session session) {
        OnlineSession onlineSession = (OnlineSession) session;
        if (null == onlineSession) {
            return;
        }
        onlineSession.setOnlineStatus(EnumOnlineStatus.OFF_LINE);
        onlineUserService.deleteSession((String)onlineSession.getId());
    }
}
