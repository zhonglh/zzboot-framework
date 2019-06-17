

package com.zzboot.framework.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;



/**
 * WebMvc Config配置
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication
@ComponentScan(value = "com")
public class WebMvcConfig extends RequestMappingHandlerMapping
		implements WebMvcConfigurer, ServletContextInitializer, WebMvcRegistrations {


	/**
	 * 设置默认欢迎页面
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/").setViewName("forward:/index");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}



	/**
	 * 添加静态资源映射地址
	 *
	 * @param registry {@link ResourceHandlerRegistry}
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").resourceChain(true)
				.addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
				.resourceChain(true).addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
	}

	/**
	 * 配置默认Servlet处理
	 *
	 * @param configurer {@link DefaultServletHandlerConfigurer}
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		//启用
		configurer.enable();
	}



	/**
	 * 时间配置
	 *
	 * @param registry {@link FormatterRegistry}
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setUseIsoFormat(true);
		registrar.registerFormatters(registry);
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

	}
}
