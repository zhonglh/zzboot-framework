package com.zzboot.system.web;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Admin
 */
@SpringBootApplication(scanBasePackages = "com.zzboot")
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
@MapperScan(value = {"com.zzboot.**.dao"})
public class SystemApplication
{

    public static void main(String[] args) {

        SpringApplication.run(SystemApplication.class, args);
    }
}
