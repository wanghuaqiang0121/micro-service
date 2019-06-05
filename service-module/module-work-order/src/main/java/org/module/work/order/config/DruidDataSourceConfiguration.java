package org.module.work.order.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年8月11日
 * @version 1.0
 * @description Druid连接池配置
 */
@Configuration
public class DruidDataSourceConfiguration {

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link DataSource}
	 * @date 2017年8月15日
	 * @version 1.0
	 * @description 返回数据源
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource druidDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		return druidDataSource;
	}
}