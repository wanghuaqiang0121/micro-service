package org.module.bespeak.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年8月11日
 * @version 1.0
 * @description Druid连接池监控配置
 */
@Configuration
public class DruidMonitorConfiguration {

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link ServletRegistrationBean}
	 * @date 2017年8月15日
	 * @version 1.0
	 * @description 配置监控地址和账户等信息
	 */
	@Bean
	public ServletRegistrationBean DruidStatViewServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "123456");
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link FilterRegistrationBean}
	 * @date 2017年8月15日
	 * @version 1.0
	 * @description 配置过滤规则
	 */
	@Bean
	public FilterRegistrationBean druidStatFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		// 添加过滤规则.
		filterRegistrationBean.addUrlPatterns("/*");
		// 添加不需要忽略的格式信息.
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

}