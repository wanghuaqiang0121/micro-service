package org.module.inquiry.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.inquiry.domain.Inquiry;
import org.module.inquiry.domain.InquiryReply;
import org.module.inquiry.domain.ServiceTimeout;
import org.module.inquiry.global.BaseGlobal;
import org.module.inquiry.global.Inquiry.InquiriesNum;
import org.module.inquiry.global.Inquiry.Status;
import org.module.inquiry.global.Inquiry.Type;
import org.module.inquiry.global.ServiceType;
import org.module.inquiry.message.Prompt;
import org.module.inquiry.service.InquiryReplyService;
import org.module.inquiry.service.InquiryService;
import org.module.inquiry.task.IInquiryService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
import org.service.tools.date.DateUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年10月11日
 * @Version 
 * @Description 问询
 */
@RestController
public class InquiryController {
	
	@Resource
	private InquiryService inquiryService;
	@Resource
	private InquiryReplyService inquiryReplyService;
	@Resource
	private RedisCacheManager cacheManager;
	
	@Resource
	private IInquiryService iInquiryService;
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param inquiryReply
	* @param result
	* @return 
	* @date 2018年10月11日
	* @version 1.0
	* @description 添加问询
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/inquiry" }, method = RequestMethod.POST)
	public JsonApi insert(@RequestBody @Validated({ BaseEntity.Insert.class })  Inquiry inquiry,BindingResult result){
		inquiry.setStatus(Status.TOBEACCEPTED.getValue());
		inquiry.setCreateDate(new Date());
		if (inquiryService.insert(inquiry) > 0) {
			/* 24小时自动结束 */
			ServiceTimeout serviceTimeout = new ServiceTimeout();
			serviceTimeout.setId(inquiry.getId());
			serviceTimeout.setType(ServiceType.INQUIRY);
			serviceTimeout.setTime(DateUtils.calLastedTimes(new Date(),DateUtils.getDayAfter(1)));
			iInquiryService.sendTimeout(serviceTimeout);
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param inquiryReply
	* @param result
	* @return 
	* @date 2018年10月16日
	* @version 1.0
	* @description 追问
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/inquiryReply" }, method = RequestMethod.POST)
	public JsonApi insertInquiryReply(@RequestBody @Validated({ BaseEntity.Insert.class })  InquiryReply inquiryReply,BindingResult result){
		inquiryReply.setType(Type.INQUIRIES.getValue());
		inquiryReply.setCreateDate(new Date());
		inquiryReply.setInquiriesNum(InquiriesNum.TOTAL.getValue());
		/* 判断追问次数 */
		Map<String, Object> map = inquiryReplyService.getInquiriesNum(inquiryReply);
		if (MapUtils.isNotEmpty(map)) {
			Long num = (Long)map.get("inquiriesNum");
			if (num.intValue() < 1) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("inquiries.num.is.less.than.one"));
			}
			if (inquiryReplyService.insert(inquiryReply) > 0) {
				return new JsonApi(ApiCode.OK);
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param inquiry
	* @param result
	* @return 
	* @date 2018年10月16日
	* @version 1.0
	* @description 用户问询列表
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/inquirys" }, method = RequestMethod.GET)
	public JsonApi getList(@RequestHeader(value = BaseGlobal.TOKEN_FLAG) String token, @Validated({ BaseEntity.SelectAll.class })  Inquiry inquiry,BindingResult result){
		// 从缓存中获取用户
		AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_USER, token));
		// 登录用户的id
		Integer operationUserIdId = (Integer) session.get(Map.class).get("id");
		inquiry.setUserId(operationUserIdId);
		Page<?> page = PageHelper.startPage(inquiry.getPage(), inquiry.getPageSize());
		List<Map<String, Object>> inquirysList = inquiryService.getList(inquiry);
		if (CollectionUtils.isNotEmpty(inquirysList)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(),inquirysList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param id
	* @param inquiry
	* @param result
	* @return 
	* @date 2018年10月16日
	* @version 1.0
	* @description 评价问询
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/inquiry/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(@PathVariable("id") Integer id ,@RequestBody @Validated({ BaseEntity.Update.class }) Inquiry inquiry,BindingResult result){
		/* 同时修改状态: */
		inquiry.setId(id);
		Map<String, Object> map = inquiryService.getOne(inquiry);
		if (MapUtils.isNotEmpty(map)) {
			if (Status.REPLIES.getValue() == ((Integer)map.get("status"))) {
				inquiry.setStatus(Status.CLOSED.getValue());
				if (inquiryService.update(inquiry) > 0) {
					return new JsonApi(ApiCode.OK);
				}
			}
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("inquiry.status.is.error"));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Wang.Hua.Qiang</b></font>
	* @param id
	* @param inquiry
	* @param result
	* @return 
	* @date 2018年10月16日
	* @version 1.0
	* @description 问询详情
	*/
	@RequiresAuthentication(authc = true, value = { "" })
	@RequestMapping(value = { "/inquiry/{id}" }, method = RequestMethod.GET)
	public JsonApi detail(@PathVariable("id") Integer id , @Validated({ BaseEntity.SelectOne.class }) Inquiry inquiry,BindingResult result){
		inquiry.setId(id);
		InquiryReply inquiryReply = new InquiryReply();
		inquiryReply.setInquiriesNum(InquiriesNum.TOTAL.getValue());
		inquiryReply.setType(Type.INQUIRIES.getValue());
		inquiry.setInquiryReply(inquiryReply);
		Map<String, Object> map = inquiryService.getOne(inquiry);
		if (MapUtils.isNotEmpty(map)) {
			return new JsonApi(ApiCode.OK,map);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	
	
}
