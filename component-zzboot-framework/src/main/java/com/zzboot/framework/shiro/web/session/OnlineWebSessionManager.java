package com.zzboot.framework.shiro.web.session;

import com.zzboot.framework.core.db.entity.IOnlineUserEnitty;
import com.zzboot.framework.shiro.IOnlineUserService;
import com.zzboot.framework.shiro.ShiroConstants;
import com.zzboot.framework.shiro.session.OnlineSession;
import com.zzboot.util.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 主要是在此如果会话的属性修改了 就标识下其修改了 然后方便 OnlineSessionDao 同步
 *
 * @author ruoyi
 */
@Slf4j
public class OnlineWebSessionManager extends DefaultWebSessionManager {

    @Override
    public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value){
        super.setAttribute(sessionKey, attributeKey, value);
        if (value != null && needMarkAttributeChanged(attributeKey)) {
            OnlineSession s = (OnlineSession) doGetSession(sessionKey);
            s.markAttributeChanged();
        }
    }

    private boolean needMarkAttributeChanged(Object attributeKey) {
        if (attributeKey == null) {
            return false;
        }
        String attributeKeyStr = attributeKey.toString();
        // 优化 flash属性没必要持久化
        if (attributeKeyStr.startsWith("org.springframework")) {
            return false;
        }
        if (attributeKeyStr.startsWith("javax.servlet")) {
            return false;
        }
        return !attributeKeyStr.equals(ShiroConstants.CURRENT_USERNAME);
    }

    @Override
    public Object removeAttribute(SessionKey sessionKey, Object attributeKey){
        Object removed = super.removeAttribute(sessionKey, attributeKey);
        if (removed != null) {
            OnlineSession s = (OnlineSession) doGetSession(sessionKey);
            s.markAttributeChanged();
        }

        return removed;
    }

    /**
     * 验证session是否有效 用于删除过期session
     */
    @Override
    public void validateSessions() {
        if (log.isInfoEnabled()) {
            log.info("invalidation sessions...");
        }

        int invalidCount = 0;

        int timeout = (int) this.getGlobalSessionTimeout();
        Date expiredDate = DateUtils.addMilliseconds(new Date(), 0 - timeout);
        IOnlineUserService onlineUserService = SpringUtil.getBean(IOnlineUserService.class);
        List<IOnlineUserEnitty> userOnlineList = onlineUserService.selectOnlineUserByExpired(expiredDate);
        // 批量过期删除
        List<String> needOfflineIdList = new ArrayList<>();
        for (IOnlineUserEnitty userOnline : userOnlineList) {
            try {
                SessionKey key = new DefaultSessionKey(userOnline.getId());
                Session session = retrieveSession(key);
                if (session != null) {
                    throw new InvalidSessionException();
                }
            } catch (InvalidSessionException e) {
                if (log.isDebugEnabled()) {
                    boolean expired = (e instanceof ExpiredSessionException);
                    String msg = "Invalidated session with id [" + userOnline.getId() + "]"
                            + (expired ? " (expired)" : " (stopped)");
                    log.debug(msg);
                }
                invalidCount++;
                needOfflineIdList.add(userOnline.getId());
            }

        }
        if (!needOfflineIdList.isEmpty()) {
            try {
                onlineUserService.batchDeleteOnlineUser(needOfflineIdList);
            } catch (Exception e) {
                log.error("batch delete db session error." , e);
            }
        }

        if (log.isInfoEnabled()) {
            String msg = "Finished invalidation session." ;
            if (invalidCount > 0) {
                msg += " [" + invalidCount + "] sessions were stopped." ;
            } else {
                msg += " No sessions were stopped." ;
            }
            log.info(msg);
        }

    }

    @Override
    protected Collection<Session> getActiveSessions() {
        throw new UnsupportedOperationException("getActiveSessions method not supported");
    }
}
