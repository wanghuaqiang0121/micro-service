package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.module.organization.configure.domain.Referral;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.ReferralService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月22日
 * @Version 
 * @Description 转诊单
 */
@RestController
public class ReferralController {

	@Autowired
	private ReferralService referralService;
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param referral
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 查询分诊到我机构的转诊单
	*/
	@RequiresAuthentication( value = { "organization:configure:referral:referralByOrganizationId" },level=Level.OPERATION)
	@RequestMapping(value = { "/referral/organization" }, method = RequestMethod.GET)
	public JsonApi referralByOrganizationList(
			@Validated({ Referral.referralByOrganizationId.class }) Referral referral, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		referral.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(referral.getPage(), referral.getPageSize());
		List<Map<String, Object>> referralList = referralService.getReferralByOrganizationId(referral);
		if (referralList != null && !referralList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), referralList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	/** 
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param referral
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 查询分诊到我机构科室的转诊单
	 */
	@RequiresAuthentication( value = { "organization:configure:referral:referralByDepartment" },level=Level.OPERATION)
	@RequestMapping(value = { "/referral/department" }, method = RequestMethod.GET)
	public JsonApi referralByDepartmentList(
			@Validated({ Referral.referralByDepartment.class }) Referral referral, BindingResult result,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		referral.setOrganizationId(organizationId);
		Page<?> page = PageHelper.startPage(referral.getPage(), referral.getPageSize());
		List<Map<String, Object>> referralList = referralService.getReferralByDepartment(referral);
		if (referralList != null && !referralList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), referralList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param referral
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 修改分诊转诊单
	*/
	@RequiresAuthentication( value = { "organization:configure:referral:triage" },level=Level.OPERATION)
	@RequestMapping(value = { "/referral/triage/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi referralByTriage(@PathVariable("id")Integer id,
			@Validated({ Referral.Triage.class })@RequestBody Referral referral, BindingResult result) {
		referral.setId(id);
		//查询数据是否存在
		Map<String, Object> referralOne = referralService.getOne(referral);
		if (MapUtils.isEmpty(referralOne)) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("referral.not.found"));
		}
		if(null != referralOne.get("organizationTeamId")) {
			return new JsonApi(ApiCode.NOT_FOUND).setMsg(Prompt.bundle("referral.is.triage"));
		}
		
		Referral referralNew = new Referral();
		//修改数据
		referralNew.setId(id);
		referralNew.setDepartmentId(referral.getDepartmentId());
		referralNew.setOrganizationTeamId(referral.getOrganizationTeamId());
		referralNew.setOrganizationUserId(referral.getOrganizationUserId());
		if (referralService.update(referralNew) >0) {
				return new JsonApi(ApiCode.OK);
		}
		
		return new JsonApi(ApiCode.FAIL);
	}
}
