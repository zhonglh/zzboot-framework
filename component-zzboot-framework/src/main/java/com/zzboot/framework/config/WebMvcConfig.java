

package com.zzboot.framework.config;

import com.zzboot.framework.core.xss.XssFilter;
import com.zzboot.framework.filter.CachingHttpHeadersFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
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

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;


/**
 * WebMvc Config配置
 */
@Slf4j
@Configuration
@ConditionalOnWebApplication
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
	 * 初始化缓存HTTP标头过滤器
	 */
	private void initCachingHttpHeadersFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
		log.debug("注册缓存HTTP标头过滤器");
		FilterRegistration.Dynamic cachingHttpHeadersFilter = servletContext
				.addFilter("cachingHttpHeadersFilter", new CachingHttpHeadersFilter());
		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/webjars/*", "/static/*");
		cachingHttpHeadersFilter.setAsyncSupported(true);
	}


	@Bean
	public FilterRegistrationBean xssFilterRegistration() {
		XssFilter xssFilter = new XssFilter();
		// 这里可以加不被xss过滤的接口
		// xssFilter.setUrlExclusion(Arrays.asList("/notice/update", "/notice/add"));
		FilterRegistrationBean registration = new FilterRegistrationBean(xssFilter);
		registration.addUrlPatterns("/*");
		return registration;
	}

	/**
	 * 配置默认Servlet处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		//启用
		configurer.enable();
	}



	/**
	 * 时间配置
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setUseIsoFormat(true);
		registrar.registerFormatters(registry);
	}


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);

		//初始化缓存HTTP标头过滤器
		initCachingHttpHeadersFilter(servletContext, dispatcherTypes);
	}

}
