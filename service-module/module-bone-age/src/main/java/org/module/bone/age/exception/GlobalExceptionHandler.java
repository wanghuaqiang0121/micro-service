package org.module.bone.age.exception;

import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
 * @date 2017年8月17日
 * @version 1.0
 * @description 全局异常处理器
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public JsonApi defaultErrorHandler(Exception e) {
		e.printStackTrace();
		log.error("error msg：{}", e.getMessage());
		return new JsonApi(ApiCode.ERROR).setMsg(e.getMessage());
	}
}
