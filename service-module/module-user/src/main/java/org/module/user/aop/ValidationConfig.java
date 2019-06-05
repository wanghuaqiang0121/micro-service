package org.module.user.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年8月15日
 * @version 1.0
 * @description 国际化资源文件配置
 */
@Configuration
public class ValidationConfig extends WebMvcConfigurerAdapter {
	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @return {@link ResourceBundleMessageSource}
	 * @date 2018年1月5日
	 * @version 1.0
	 * @description 配置资源文件信息
	 */
	public ResourceBundleMessageSource getMessageSource() {
		ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		rbms.setDefaultEncoding("UTF-8");
		rbms.setBasenames("i18n/validator/validator");
		return rbms;
	}

	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(getMessageSource());
		return validator;
	}
}
