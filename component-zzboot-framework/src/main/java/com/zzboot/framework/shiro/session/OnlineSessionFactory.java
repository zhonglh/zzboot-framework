package com.zzboot.framework.shiro.session;

import com.zzboot.framework.core.db.entity.IOnlineUserEnitty;
import com.zzboot.framework.core.enums.EnumOnlineStatus;
import com.zzboot.util.web.IpUtil;
import com.zzboot.util.web.ServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义sessionFactory会话
 */
@Component
public class OnlineSessionFactory implements SessionFactory {
    @Override
    public Session createSession(SessionContext initData) {
        OnlineSession session = new OnlineSession();
        if (initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null) {
                UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
                if(userAgent != null) {
                    // 获取客户端操作系统
                    String os = userAgent.getOperatingSystem().getName();
                    // 获取客户端浏览器
                    String browser = userAgent.getBrowser().getName();

                    session.setBrowser(browser);
                    session.setOs(os);
                }
                session.setHost(IpUtil.getIpAddr(request));
            }
        }
        return session;
    }


    public Session createSession(IOnlineUserEnitty onlineUser) {
        OnlineSession session = new OnlineSession();
        session.setId(onlineUser.getId());
        session.setUserId(onlineUser.getUserId());
        session.setUserName(onlineUser.getUserName());
        session.setLoginName(onlineUser.getLoginName());
        session.setDepId(onlineUser.getDepId());
        session.setDepName(onlineUser.getDepName());
        session.setTenantId(onlineUser.getTenantId());
        session.setTenantName(onlineUser.getTenantName());
        session.setOs(onlineUser.getOs());
        session.setIp(onlineUser.getIp());
        session.setBrowser(onlineUser.getBrowser());
        session.setOnlineStatus(EnumOnlineStatus.ON_LINE);
        session.setStartTimestamp(onlineUser.getStartTimestamp());
        session.setLastAccessTime(onlineUser.getLastAccessTime());
        return session;
    }
}
