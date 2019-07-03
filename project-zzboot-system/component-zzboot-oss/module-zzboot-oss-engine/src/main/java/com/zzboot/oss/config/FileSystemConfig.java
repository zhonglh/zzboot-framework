package com.zzboot.oss.config;

import com.zzboot.oss.OssConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 文件系统 设置
 * @author Administrator
 */
@Component
public class FileSystemConfig implements OssConfig {


    @Value("#{fileystem.root}")
    private String root;


    @Value("#{fileystem.active}")
    private boolean active;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
