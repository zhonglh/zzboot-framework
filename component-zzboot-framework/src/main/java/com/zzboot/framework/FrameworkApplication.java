package com.zzboot.framework;


import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@SpringBootApplication(scanBasePackages = "com.zzboot")
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
@MapperScan(value = {"com.zzboot.**.dao"})
public class FrameworkApplication
{

    public static void main(String[] args) {

        SpringApplication.run(FrameworkApplication.class, args);
    }
}
