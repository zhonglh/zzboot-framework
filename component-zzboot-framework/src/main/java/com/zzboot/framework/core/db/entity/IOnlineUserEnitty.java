package com.zzboot.framework.core.db.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhonglh
 */
public interface IOnlineUserEnitty {

    /**
     * session id
     * @return
     */
    public String getId();

    /**
     * 用户ID
     * @return
     */
    public String getUserId();


    public String getLoginName();

    public String getUserName();

    public String getDepId();

    public String getDepName();



    public String getTenantId();

    public String getTenantName();


    public String getOs();

    public String getBrowser();

    public String getIp();

    public Date getStartTimestamp();

    public Date getLastAccessTime();
}
