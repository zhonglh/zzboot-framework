package com.zzboot.oss.config.cloudconfig.impl;

import com.zzboot.oss.config.cloudconfig.CloudConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 七牛云设置
 * @author Administrator
 */
@PropertySource("classpath:oss-cloud-qiniuyun.properties")
@Component
public class QiniuCloudConfig implements CloudConfig {


    private String domain;


    private String prefix;


    private String bucketName;

    private String endPoint;

    private String accessKeyId;

    private String accessKeySecret;


    private boolean active;


    @Override
    public String getCloudDomain() {
        return domain;
    }

    @Override
    public String getCloudPrefix() {
        return prefix;
    }

    @Override
    public String getCloudEndPoint() {
        return endPoint;
    }

    @Override
    public String getCloudAppId() {
        return null;
    }

    @Override
    public String getCloudSecretId() {
        return accessKeyId;
    }

    @Override
    public String getCloudSecretKey() {
        return accessKeySecret;
    }

    @Override
    public String getCloudBucketName() {
        return bucketName;
    }

    @Override
    public String getCloudRegion() {
        return null;
    }

}
