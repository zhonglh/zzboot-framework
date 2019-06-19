
package com.zzboot.framework.filter;



import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 前端静态资源缓存过滤器
 * 过滤器用于生产，将HTTP缓存标头放置一个很长的（1个月）到期时间。
 * @author zhonglh
 */
public class CachingHttpHeadersFilter implements Filter {


	private static final long LAST_MODIFIED = System.currentTimeMillis();


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//设置缓存时间
		httpResponse.setHeader("Pragma", "cache");

		// 设置Expires标头，用于代理缓存
		httpResponse.setDateHeader("Expires", TimeUnit.DAYS.toMillis(30L) + System.currentTimeMillis());

		// 设置Last-Modified标头，用于浏览器缓存
		httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);

		chain.doFilter(request, response);
	}
}
