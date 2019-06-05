package org.module.organization.team.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年8月15日
 * @version 1.0
 * @description 实体类校验切面
 */
@Component
@Aspect
public class ValidatorAop {

	/**
	 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
	 * @param point
	 * @return {@link Object}
	 * @throws Throwable
	 * @date 2017年8月15日
	 * @version 1.0
	 * @description 环绕校验
	 */
	@Around("execution(public * org.module.organization.team.controller..*.*(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] objects = point.getArgs();
		for (Object object : objects) {
			if (object instanceof BindingResult) {
				if (((BindingResult) object).hasErrors()) {
					return new JsonApi(ApiCode.BAD_REQUEST).setMsg(((BindingResult) object).getFieldError().getDefaultMessage());
				}
			}
		}
		return point.proceed();
	}
}