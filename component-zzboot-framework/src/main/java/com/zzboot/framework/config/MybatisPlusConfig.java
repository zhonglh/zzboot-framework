
package com.zzboot.framework.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 配置mybatis plus
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

	/**
	 * 性能分析插件
	 * SQL执行效率插件 设置 dev test 环境开启
	 * @return {@link PerformanceInterceptor}
	 */
	@Bean
	@Profile({"dev", "test"})
	public PerformanceInterceptor performanceInterceptor() {
		PerformanceInterceptor pi =  new PerformanceInterceptor();
		pi.setFormat(true);
		pi.setMaxTime(500);
		return pi;
	}


	/**
	 * 配置数据权限过滤带有逻辑删除
	 * @return {@link LogicSqlInjector}
	 */
	@Bean
	public ISqlInjector dataScopeSqlInjector() {
		return new LogicSqlInjector();
	}

	/**
	 * 分页插件分页插件
	 * @return {@link PaginationInterceptor}
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor interceptor = new PaginationInterceptor();
		return interceptor;
	}


}
