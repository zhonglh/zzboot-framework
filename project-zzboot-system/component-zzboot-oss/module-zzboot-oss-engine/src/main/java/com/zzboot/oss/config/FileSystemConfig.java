package com.zzboot.oss.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 文件系统 设置
 * @author Administrator
 */
@PropertySource("classpath:oss-file.properties")
@Component
public class FileSystemConfig  {


    private String root;



    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

}
