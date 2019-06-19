package com.zzboot.framework.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zzboot.framework.bean.ShiroBean;
import com.zzboot.framework.shiro.MyRealm;
import com.zzboot.framework.shiro.session.OnlineSessionDAO;
import com.zzboot.framework.shiro.session.OnlineSessionFactory;
import com.zzboot.framework.shiro.web.filter.LogoutFilter;
import com.zzboot.framework.shiro.web.filter.captcha.CaptchaValidateFilter;
import com.zzboot.framework.shiro.web.filter.online.OnlineSessionFilter;
import com.zzboot.framework.shiro.web.filter.sync.SyncOnlineSessionFilter;
import com.zzboot.framework.shiro.web.session.OnlineWebSessionManager;
import com.zzboot.framework.shiro.web.session.SpringSessionValidationScheduler;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 权限配置加载
 */
@Configuration
public class ShiroConfig {


    @Autowired
    private ShiroBean shiroBean;


    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean
    public EhCacheManager getEhCacheManager() {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("zzboot");
        EhCacheManager em = new EhCacheManager();
        if (cacheManager == null) {
            em.setCacheManager(new net.sf.ehcache.CacheManager(getCacheManagerConfigFileInputStream()));
            return em;
        } else {
            em.setCacheManager(cacheManager);
            return em;
        }
    }

    /**
     * 返回配置文件流 避免ehcache配置文件一直被占用，无法完全销毁项目重新部署
     */
    private InputStream getCacheManagerConfigFileInputStream(){
        String configFile = "classpath:ehcache-shiro.xml";
        try(InputStream inputStream = ResourceUtils.getInputStreamForPath(configFile)){
            byte[] b = IOUtils.toByteArray(inputStream);
            return new ByteArrayInputStream(b);
        }catch (IOException e){
            throw new ConfigurationException(
                    "Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
        }
    }

    /**
     * 自定义Realm
     */
    @Bean
    public MyRealm userRealm(EhCacheManager cacheManager) {
        MyRealm realm = new MyRealm();
        realm.setCacheManager(cacheManager);
        return realm;
    }

    /**
     * 自定义sessionDAO会话
     */
    @Bean
    public OnlineSessionDAO sessionDAO() {
        OnlineSessionDAO sessionDAO =  new OnlineSessionDAO();
        sessionDAO.setActiveSessionsCacheName(shiroBean.getCacheName());
        return sessionDAO;
    }

    /**
     * 自定义sessionFactory会话
     */
    @Bean
    public OnlineSessionFactory sessionFactory() {
        return new OnlineSessionFactory();
    }

    /**
     * 自定义sessionFactory调度器
     */
    @Bean
    public SpringSessionValidationScheduler sessionValidationScheduler() {
        SpringSessionValidationScheduler sessionValidationScheduler = new SpringSessionValidationScheduler();
        // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
        sessionValidationScheduler.setSessionValidationInterval( shiroBean.getValidationInterval() * 60 * 1000);
        // 设置会话验证调度器进行会话验证时的会话管理器
        sessionValidationScheduler.setSessionManager(sessionValidationManager());
        return sessionValidationScheduler;
    }

    /**
     * 会话管理器
     */
    @Bean
    public OnlineWebSessionManager sessionValidationManager() {
        OnlineWebSessionManager manager = getOnlineWebSessionManager();
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        manager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        manager.setSessionFactory(sessionFactory());
        return manager;
    }

    private OnlineWebSessionManager getOnlineWebSessionManager() {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        // 加入缓存管理器
        manager.setCacheManager(getEhCacheManager());
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout((long) shiroBean.getExpireTime() * 60 * 1000);
        // 去掉 JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        return manager;
    }

    /**
     * 会话管理器
     */
    @Bean
    public OnlineWebSessionManager sessionManager() {
        OnlineWebSessionManager manager = getOnlineWebSessionManager();
        // 定义要使用的无效的Session定时调度器
        manager.setSessionValidationScheduler(sessionValidationScheduler());
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        manager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        manager.setSessionFactory(sessionFactory());
        return manager;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myRealm);
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setCacheManager(getEhCacheManager());
        // session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 退出过滤器
     */
    private LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLoginUrl(shiroBean.getLoginUrl());
        return logoutFilter;
    }

    /**
     * Shiro过滤器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(shiroBean.getLoginUrl());
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroBean.getUnauthorizedUrl());
        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 对静态资源设置匿名访问
        filterChainDefinitionMap.put("/favicon.ico**" , "anon");
        filterChainDefinitionMap.put("/css/**" , "anon");
        filterChainDefinitionMap.put("/docs/**" , "anon");
        filterChainDefinitionMap.put("/fonts/**" , "anon");
        filterChainDefinitionMap.put("/img/**" , "anon");
        filterChainDefinitionMap.put("/js/**" , "anon");
        filterChainDefinitionMap.put("/druid/**" , "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**" , "anon");
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout" , "logout");
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/login" , "anon,captchaValidate");
        filterChainDefinitionMap.put("/test" , "anon");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("onlineSession" , onlineSessionFilter());
        filters.put("syncOnlineSession" , syncOnlineSessionFilter());
        filters.put("captchaValidate" , captchaValidateFilter());
        filters.put("logout" , logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 所有请求需要认证
        filterChainDefinitionMap.put("/**" , "user,onlineSession,syncOnlineSession");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自定义在线用户处理过滤器
     */
    @Bean
    public OnlineSessionFilter onlineSessionFilter() {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(shiroBean.getLoginUrl());
        return onlineSessionFilter;
    }

    /**
     * 自定义在线用户同步过滤器
     */
    @Bean
    public SyncOnlineSessionFilter syncOnlineSessionFilter() {
        return new SyncOnlineSessionFilter();
    }

    /**
     * 自定义验证码过滤器
     */
    @Bean
    public CaptchaValidateFilter captchaValidateFilter() {
        CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
        captchaValidateFilter.setCaptchaEnabled(true);
        captchaValidateFilter.setCaptchaType(shiroBean.getCaptchaType());
        return captchaValidateFilter;
    }

    /**
     * cookie 属性设置
     */
    private SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(shiroBean.getDomain());
        cookie.setPath(shiroBean.getPath());
        cookie.setHttpOnly(shiroBean.isHttpOnly());
        cookie.setMaxAge(shiroBean.getMaxAge() * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 记住我
     */
    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
        return cookieRememberMeManager;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 开启Shiro注解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
