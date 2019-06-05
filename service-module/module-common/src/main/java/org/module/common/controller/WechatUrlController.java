package org.module.common.controller;

import java.util.Map;

import org.module.common.domain.WechatUrl;
import org.module.common.service.WechatUrlService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WechatUrlController {

	@Autowired
	private WechatUrlService wechatUrlService;
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param appid
	* @param wechatUrl
	* @param bindingResult
	* @param token
	* @return 
	* @date 2018年7月10日
	* @version 1.0
	* @description 查询微信平台对应的地址
	*/
	@RequiresAuthentication(ignore=true, value={"common:wechat.url:detail"})
	@RequestMapping(value={"/wechat/url/{appid}"}, method=RequestMethod.GET)
	public JsonApi getWechatUrlDetails(
			@PathVariable("appid")String appid,
			@Validated(BaseEntity.SelectOne.class) WechatUrl wechatUrl,BindingResult result
			) {
		wechatUrl.setAppid(appid);
		Map<String, Object> wechatUrlMap = wechatUrlService.getOne(wechatUrl);
		if (wechatUrlMap!=null && !wechatUrlMap.isEmpty()) {
			return new JsonApi(ApiCode.OK, wechatUrlMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

}
