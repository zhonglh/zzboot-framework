package com.zzboot.framework.shiro.session;

import com.zzboot.framework.core.enums.EnumOnlineStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;

/**
 * 在线用户会话属性
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OnlineSession extends SimpleSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String loginName;


    /**
     * 用户名称
     */
    private String UserName;


    /**
     * 部门名称
     */
    private String depId;

    /**
     * 部门名称
     */
    private String depName;


    /**
     * 租户(企业)ID
     */
    private String tenantId;



    /**
     * 租户(企业)名称
     */
    private String tenantName;

    /**
     * 登录IP地址
     */
    private String ip;

    /**
     * 浏览器类型
     */
    private String browser;


    private EnumOnlineStatus onlineStatus;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 属性是否改变 优化session数据同步
     */
    private transient boolean attributeChanged = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public EnumOnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(EnumOnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    void resetAttributeChanged() {
        this.attributeChanged = false;
    }

    boolean isAttributeChanged() {
        return attributeChanged;
    }

    public void markAttributeChanged() {
        this.attributeChanged = true;
    }
}
