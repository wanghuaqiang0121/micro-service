package org.module.bone.age.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年6月5日
 * @Version 
 * @Description spring上下文工具类，用于普通类调用springIOC中的对象
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringContextUtils.applicationContext == null) {
			SpringContextUtils.applicationContext = applicationContext;
		}
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @return 
	* @date 2018年6月5日
	* @version 1.0
	* @description 获取applicationContext
	*/
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param name
	* @return 
	* @date 2018年6月5日
	* @version 1.0
	* @description 通过name获取 Bean
	*/
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param clazz
	* @return 
	* @date 2018年6月5日
	* @version 1.0
	* @description 通过class获取Bean
	*/
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param name
	* @param clazz
	* @return 
	* @date 2018年6月5日
	* @version 1.0
	* @description 通过name, 以及Clazz返回指定的Bean
	*/
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
}
