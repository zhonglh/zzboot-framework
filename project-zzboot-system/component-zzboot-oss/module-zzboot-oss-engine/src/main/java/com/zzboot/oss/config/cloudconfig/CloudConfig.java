package com.zzboot.oss.config.cloudconfig;

import com.zzboot.oss.OssConfig;

/**
 * 云配置
 * @author Administrator
 */
public interface CloudConfig {


    /**
     * 获取云域名
     * @return
     */
    public String getCloudDomain();


    /**
     * 获取云路径前缀
     * @return
     */
    public String getCloudPrefix();


    /**
     * 获取云 访问域名
     * @return
     */
    public String getCloudEndPoint();



    /**
     * 获取云 App Id
     * @return
     */
    public String getCloudAppId();



    /**
     * 获取云 Secret Id
     * @return
     */
    public String getCloudSecretId();



    /**
     * 获取云 Secret Key
     * @return
     */
    public String getCloudSecretKey();


    /**
     * 获取云 空间名
     * @return
     */
    public String getCloudBucketName();

    /**
     * 获取云 所属地区
     * @return
     */
    public String getCloudRegion();


}
