package org.module.common.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.module.common.domain.Recipient;
import org.module.common.domain.Sms;
import org.module.common.global.BaseGlobal;
import org.module.common.global.RecipientCode;
import org.module.common.message.Prompt;
import org.module.common.service.HttpIOUtil;
//import org.module.common.service.ISmsService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.tools.code.ValidCodeUtils;
import org.service.tools.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class ValidCodeController {

	@Resource
	private CacheManager cacheManager;
	/*@Autowired
	private ISmsService smsService;*/
	@Autowired
	private HttpServletRequest request;
	@Resource
	private HttpIOUtil httpIOUtil;
	/** 
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址
	 * 所以取X-Forwarded-For中第一个非unknown的有效IP字符串
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param request
	* @return 
	* @date 2018年12月6日
	* @version 1.0
	* @description 获取用户真实ip
	*/
	public static String getIpAddress(HttpServletRequest request) {  
		String Xip = request.getHeader("X-Real-IP");//X-Real-IP：nginx服务代理
        String XFor = request.getHeader("X-Forwarded-For");//X-Forwarded-For：Squid 服务代理
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP"); //Proxy-Client-IP：apache 服务代理
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");//WL-Proxy-Client-IP：weblogic 服务代理
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");//HTTP_CLIENT_IP：有些代理服务器
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param phone
	* @return 
	* @date 2018年12月6日
	* @version 1.0
	* @description 获取短信验证码
	*/
	@SuppressWarnings("unchecked")
	@RequiresAuthentication(ignore = true, value = { "common:code:code:number" })
	@RequestMapping(value = { "/code/{phone}" }, method = RequestMethod.GET)
	public JsonApi getCodeByPhone(@PathVariable("phone") String phone) {
		/*判断当前IP+phone 是否在一分钟内获取过验证码*/
		String key = ValidCodeController.getIpAddress(request)+phone;
		ValueWrapper valueWrapper = cacheManager.getCache(BaseGlobal.IP_VOLId).get(key);
		if (valueWrapper != null) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("get.verification.code.too.frequently"));
		}
		String code = ValidCodeUtils.generateNumber(6);
		Sms sms = new Sms();
		sms.setSign(Prompt.bundle("sign"));
		sms.setContent(Prompt.bundle("validteCodeMessage", code));
		/*延迟时间*/
		sms.setTime(0);
		/*短信接收人*/
		List<Recipient> recipients = new ArrayList<Recipient>();
		Recipient recipient = new Recipient();
		/* 1：发送 2：充值 */
		recipient.setType(RecipientCode.Type.SEND.getValue());
		recipient.setCalledNumber(phone);
		recipients.add(recipient);
		sms.setRecipients(recipients);
		String response = null; // 定义响应字符串

		Calendar calendar = Calendar.getInstance();
		String year=calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH+1)+""+calendar.get(Calendar.DATE)+""+Calendar.HOUR_OF_DAY+""+Calendar.MINUTE+""+calendar.getMaximum(Calendar.SECOND)+"";
		String sign = "yxtjkghnmmd520yxt"+year;
		String signMd5 = MD5Util.getInstance().getMD5Code(sign).toLowerCase();

		String requestURL ="http://dc.28inter.com/v2sms.aspx?userid=1036&"
				+ "timestamp="+year+"&"
				+ "sign="+signMd5+"&"
				+ "mobile="
				+ phone
				+ "&content="
				+ sms.getSign()+sms.getContent()
				+ "&action=send&rt=json";
		Map<String,String> requestText = new HashMap<String,String>();
		Map<String,Object> responseMap = new HashMap<>();// 定义响应map
		// 发送短信
		try {
			// 得到response
			response = httpIOUtil.sendPostRequest(requestURL, requestText);
		} catch (Exception e) {
			return new JsonApi(ApiCode.FAIL);
		}
		// 转为map
		JSON responseJson = (JSON) JSON.parse(response);
		responseMap = (Map<String,Object>)responseJson;
		if ((responseMap.get("ReturnStatus").toString()).equals("Success")) {// 成功
			// 发送成功放入缓存
			cacheManager.getCache(BaseGlobal.CACHE_CODE).put(phone, code);
			/* 用于判断ip+phone 是否频繁获取验证码*/
			cacheManager.getCache(BaseGlobal.IP_VOLId).put(key, code);
			return new JsonApi(ApiCode.OK).setMsg(Prompt.bundle("phone.code.success"));
		}
		/*JsonApi result = smsService.send(sms);
		if (result.getCode() == ApiCode.OK.getValue()) {
			// 发送成功放入缓存
			cacheManager.getCache(BaseGlobal.CACHE_CODE).put(phone, code);
			 用于判断ip+phone 是否频繁获取验证码
			cacheManager.getCache(BaseGlobal.IP_VOLId).put(key, code);
			return new JsonApi(ApiCode.OK).setMsg(Prompt.bundle("phone.code.success"));
		}*/
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("phone.code.fall"));
	}
}
