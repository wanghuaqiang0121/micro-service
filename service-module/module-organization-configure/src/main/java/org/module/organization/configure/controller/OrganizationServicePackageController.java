package org.module.organization.configure.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.module.organization.configure.domain.Organization;
import org.module.organization.configure.domain.OrganizationPackageService;
import org.module.organization.configure.domain.OrganizationPackageUserType;
import org.module.organization.configure.domain.OrganizationServicePackage;
import org.module.organization.configure.domain.OrganizationServicePackageLog;
import org.module.organization.configure.domain.TeamOrganizationServicePackage;
import org.module.organization.configure.global.BaseGlobal;
import org.module.organization.configure.global.ServiceStatusCode;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.OrganizationPackageServiceService;
import org.module.organization.configure.service.OrganizationPackageUserTypeService;
import org.module.organization.configure.service.OrganizationService;
import org.module.organization.configure.service.OrganizationServicePackageLogService;
import org.module.organization.configure.service.OrganizationServicePackageService;
import org.module.organization.configure.service.TeamOrganizationServicePackageService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.service.redis.cache.RedisCacheManager;
import org.service.redis.token.AuthenticationSession;
import org.service.redis.token.AuthenticationToken;
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
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月19日
 * @Version
 * @Description 机构服务包
 */
@RestController
public class OrganizationServicePackageController {

	@Autowired
	private OrganizationServicePackageService organizationServicePackageService;
	@Autowired
	private TeamOrganizationServicePackageService teamOrganizationServicePackageService;
	@Autowired
	private OrganizationPackageServiceService organizationPackageServiceService;
	@Autowired
	private OrganizationPackageUserTypeService organizationPackageUserTypeService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OrganizationServicePackageLogService organizationServicePackageLogService;
	@Resource
	private RedisCacheManager cacheManager;
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 验证服务包重复
	 *  // TODO 待添加到新项目
	 */
	@RequiresAuthentication(value = { "organization:configure:organizationServicePackage:valid" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/valid" }, method = RequestMethod.GET)
	public JsonApi validOrganizationPackageRepeat(
			@Validated(OrganizationServicePackage.validOrganizationPackageName.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, 
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		/*验证服务包重复*/
		organizationServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.SUBMITED.getValue());
		organizationServicePackage.setOrganizationId(organizationId);
		Map<String, Object> resultMap = new HashMap<>(); 
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getRepeat(organizationServicePackage);
		if (organizationServicePackageMap!=null && !organizationServicePackageMap.isEmpty()) {
			resultMap.put("isRepeat",true);
			return new JsonApi(ApiCode.OK,resultMap).setMsg(Prompt.bundle("organization.service.package.data.exist"));
		}
		resultMap.put("isRepeat",false);
		return new JsonApi(ApiCode.OK,resultMap);
	}
		
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 新增机构服务包
	*/
	@Transactional
	@RequiresAuthentication(authc=true,value = { "organization:configure:organizationServicePackage:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package" }, method = RequestMethod.POST)
	public JsonApi insertOrganizationPackages(
			@Validated(BaseEntity.Insert.class)@RequestBody OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 设置状态:提交/待审核
		organizationServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.SUBMITED.getValue());
		// 设置机构id
		organizationServicePackage.setOrganizationId(organizationId);
		// 判断重复
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getRepeat(organizationServicePackage);
		if (organizationServicePackageMap!=null && !organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("organization.service.package.data.exist"));
		}
		// 设置创建时间
		organizationServicePackage.setCreateDate(new Date());
		/* 新增数据 */
		if (organizationServicePackageService.insert(organizationServicePackage) > 0) {
			/* 判断是否新增服务包的人群类型 */
			if (null != organizationServicePackage.getOrganizationPackageUserTypes()) {
				for (int i = 0; i < organizationServicePackage.getOrganizationPackageUserTypes().size(); i++) {
					/* 设置机构服务包ID */
					organizationServicePackage.getOrganizationPackageUserTypes().get(i)
							.setOrganizationServicePackageId(organizationServicePackage.getId());
				}
				OrganizationPackageUserType organizationPackageUserType = new OrganizationPackageUserType();
				organizationPackageUserType.setOrganizationPackageUserTypes(organizationServicePackage.getOrganizationPackageUserTypes());
				// 批量新增服务包的人群类型 
				int rows=organizationPackageUserTypeService.batchInsert(organizationPackageUserType.getOrganizationPackageUserTypes());
				if ( rows== organizationServicePackage.getOrganizationPackageUserTypes().size()) {
					// 添加机构服务包日志
					/* 获取用户缓存信息 */
					AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
					
					Integer operationUserId = (Integer) session.get(Map.class).get("id");
					OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
					packagelog.setAuditOrganizationId(organizationServicePackage.getAuditOrganizationId());
					packagelog.setOrganizationServicePackageId(organizationServicePackage.getId());
					packagelog.setOrganizationUserId(operationUserId);
					packagelog.setStatus(organizationServicePackage.getStatus());
					packagelog.setCreateDate(new Date());
					packagelog.setRemark("添加机构服务包");
					if(organizationServicePackageLogService.insert(packagelog) > 0)
					{
						return new JsonApi(ApiCode.OK);
					}
					throw new RuntimeException();					
				}else {
					throw new RuntimeException();
				}
			}
			return new JsonApi(ApiCode.OK);
		} 
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param id
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 查询机构的服务包详情
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:detail" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/{id}" }, method = RequestMethod.GET)
	public JsonApi organizationServicePackageDetail(@PathVariable("id") Integer id,
			@Validated(BaseEntity.SelectOne.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result) {
		// 设置id
		organizationServicePackage.setId(id);
		// 查询机构的服务包详情
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService
				.getOne(organizationServicePackage);
		if (organizationServicePackageMap!=null && !organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.OK, organizationServicePackageMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}

	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param id
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月21日
	 * @version 1.0
	 * @description 修改机构的服务包
	 */
	@Transactional
	@RequiresAuthentication(value = { "organization:configure:organizationServicePackage:update" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/{id}" }, method = RequestMethod.PUT)
	public JsonApi updateOrganizationServicePackage(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Update.class) @RequestBody OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 设置id
		organizationServicePackage.setId(id);
		// 查询数据是否存在
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService
				.getOne(organizationServicePackage);
		if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 如果修改数据唯一
		if (organizationServicePackage.getStatus() != null && organizationServicePackage.getOrganizationId() != null
				&& organizationServicePackage.getServicePackageTypeId() != null
				&& organizationServicePackage.getName() != null) {
			// 查询是否存在重复
			Map<String, Object> organizationServicePackageRepeatMap = organizationServicePackageService
					.getRepeat(organizationServicePackage);
			if (organizationServicePackageRepeatMap!=null && organizationServicePackageRepeatMap.size()>0) {
				if (!id.equals(organizationServicePackageRepeatMap.get("id"))) {
					return new JsonApi(ApiCode.CONFLICT);
				}
			}
		}
		// 修改机构的服务包详情
		if (organizationServicePackageService.update(organizationServicePackage) > 0) {
			/* 判断是否修改服务包的人群类型 */
			if (null != organizationServicePackage.getOrganizationPackageUserTypes()) {
				OrganizationPackageUserType oPackageUserType = new OrganizationPackageUserType();
				oPackageUserType.setOrganizationServicePackageId(id);
				if (organizationPackageUserTypeService.deleteByOrganizationPackageId(oPackageUserType) > 0) {
					for (int i = 0; i < organizationServicePackage.getOrganizationPackageUserTypes().size(); i++) {
						/* 设置机构服务包ID */
						organizationServicePackage.getOrganizationPackageUserTypes().get(i)
						.setOrganizationServicePackageId(organizationServicePackage.getId());
					}
					OrganizationPackageUserType organizationPackageUserType = new OrganizationPackageUserType();
					organizationPackageUserType.setOrganizationPackageUserTypes(organizationServicePackage.getOrganizationPackageUserTypes());
					// 批量新增服务包的人群类型 
					int rows=organizationPackageUserTypeService.batchInsert(organizationPackageUserType.getOrganizationPackageUserTypes());
					if (rows == organizationServicePackage.getOrganizationPackageUserTypes().size()) {
						// 添加机构服务包日志
						/* 获取用户缓存信息 */
						AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
						
						Integer operationUserId = (Integer) session.get(Map.class).get("id");
						OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
						packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
						packagelog.setOrganizationServicePackageId(id);
						packagelog.setOrganizationUserId(operationUserId);
						packagelog.setStatus(Integer.parseInt(organizationServicePackageMap.get("status").toString()));
						packagelog.setCreateDate(new Date());
						packagelog.setRemark("修改机构服务包");
						if(organizationServicePackageLogService.insert(packagelog) > 0)
						{
							return new JsonApi(ApiCode.OK);
						}
						throw new RuntimeException();
					}else {
						throw new RuntimeException();
					}
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param id
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 修改机构的服务包用户协议
	 */
	@RequiresAuthentication(value = {"organization:configure:organizationServicePackage:updateprotocol" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/protocol/{id}" }, method = RequestMethod.PUT)
	@Transactional
	public JsonApi updateProtocol(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.UpdateProtocol.class) @RequestBody OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		OrganizationServicePackage updateProtocol = new OrganizationServicePackage();
		// 设置id
		updateProtocol.setId(id);
		// 查询数据是否存在
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService
				.getOne(updateProtocol);
		if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 设置用户协议
		updateProtocol.setProtocol(organizationServicePackage.getProtocol());
		// 修改机构的服务包用户协议
		if (organizationServicePackageService.update(updateProtocol) > 0) {
			// 添加机构服务包日志
			/* 获取用户缓存信息 */
			AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
			
			Integer operationUserId = (Integer) session.get(Map.class).get("id");
			OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
			packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
			packagelog.setOrganizationServicePackageId(id);
			packagelog.setOrganizationUserId(operationUserId);
			packagelog.setStatus(Integer.parseInt(organizationServicePackageMap.get("status").toString()));
			packagelog.setCreateDate(new Date());
			packagelog.setRemark("修改机构服务包用户协议");
			if(organizationServicePackageLogService.insert(packagelog) > 0)
			{
				return new JsonApi(ApiCode.OK);
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}

	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param id
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 删除机构的服务包
	 */
	@Transactional
	@RequiresAuthentication(value = { "organization:configure:organizationServicePackage:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/{id}" }, method = RequestMethod.DELETE)
	public JsonApi delete(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Delete.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		// 设置id
		organizationServicePackage.setId(id);
		// 判断是否有团队使用
		TeamOrganizationServicePackage teamOrganizationServicePackage = new TeamOrganizationServicePackage();
		teamOrganizationServicePackage.setOrganizationServicePackageId(id);
		List<Map<String, Object>> teamOrganizationServicePackagesList = teamOrganizationServicePackageService
				.getListByOrganizationServicePackageId(teamOrganizationServicePackage);
		// 如果有团队使用，不能删除
		if (teamOrganizationServicePackagesList!=null && !teamOrganizationServicePackagesList.isEmpty()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.is.useing"));
		}
		// 删除机构的服务包
		if (organizationServicePackageService.delete(organizationServicePackage) > 0) {
			OrganizationPackageUserType organizationPackageUserType = new OrganizationPackageUserType();
			organizationPackageUserType.setOrganizationServicePackageId(id);
			// 删除机构的服务包对应的人群类型
			if(organizationPackageUserTypeService.deleteByOrganizationPackageId(organizationPackageUserType) > 0)
			{
				OrganizationPackageService organizationPackageServices = new OrganizationPackageService();
				organizationPackageServices.setOrganizationServicePackageId(id);
				// 删除服务包所有服务关联
				if(organizationPackageServiceService.deleteByOrganizationPackageId(organizationPackageServices) > 0)
				{
					// 查询数据是否存在
					Map<String, Object> organizationServicePackageMap = organizationServicePackageService
							.getOne(organizationServicePackage);
					if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
						return new JsonApi(ApiCode.NOT_FOUND);
					}
					// 添加机构服务包日志
					/* 获取用户缓存信息 */
					AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));					
					Integer operationUserId = (Integer) session.get(Map.class).get("id");
					OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
					packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
					packagelog.setOrganizationServicePackageId(id);
					packagelog.setOrganizationUserId(operationUserId);
					packagelog.setStatus(Integer.parseInt(organizationServicePackageMap.get("status").toString()));
					packagelog.setCreateDate(new Date());
					packagelog.setRemark("删除机构服务包");
					if(organizationServicePackageLogService.insert(packagelog) > 0)
					{
						return new JsonApi(ApiCode.OK);
					}
				}
			}
			throw new RuntimeException();
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param id
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 修改状态为审核中
	 */
	@Transactional
	@RequiresAuthentication(value = { "organization:configure:organizationServicePackage:examine" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/examine/{id}" }, method = RequestMethod.PUT)
	public JsonApi oPackageExamine(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.Examine.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		OrganizationServicePackage organizationServicePackageAudit = new OrganizationServicePackage();
		// 设置id
		organizationServicePackageAudit.setId(id);
		// 判断数据是否存在
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackageAudit);
		if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 状态为 提交/待审核 审核不通过  才能改为审核中
		if ((int)organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.SUBMITED.getValue() || 
				(int)organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.NOTPASS.getValue()) {
			// 设置状态为审核中
			organizationServicePackageAudit.setStatus(ServiceStatusCode.OrganizationServicePackage.EXAMINE.getValue());
			// 修改
			if(organizationServicePackageService.update(organizationServicePackageAudit) > 0){
				// 添加机构服务包日志
				/* 获取用户缓存信息 */
				AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
				
				Integer operationUserId = (Integer) session.get(Map.class).get("id");
				OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
				packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
				packagelog.setOrganizationServicePackageId(id);
				packagelog.setOrganizationUserId(operationUserId);
				packagelog.setStatus(organizationServicePackageAudit.getStatus());
				packagelog.setCreateDate(new Date());
				packagelog.setRemark("修改状态为审核中");
				if(organizationServicePackageLogService.insert(packagelog) > 0)
				{
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.status.error"));
		
	}
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月22日
	* @version 1.0
	* @description 审核通过
	*/
	@Transactional
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:pass" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/passs/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationServicePackagePass(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.Pass.class) @RequestBody OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		
		OrganizationServicePackage organizationServicePackagePass = new OrganizationServicePackage();
		organizationServicePackagePass.setAuditRemark(organizationServicePackage.getAuditRemark());
		// 设置id
		organizationServicePackagePass.setId(id);
		// 判断数据是否存在
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackagePass);
				if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
					return new JsonApi(ApiCode.NOT_FOUND);
				}
		// 状态为 审核中 才能审核
		if ((int)organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.EXAMINE.getValue()) {
			// 设置状态为已通过
			organizationServicePackagePass.setStatus(ServiceStatusCode.OrganizationServicePackage.PASS.getValue());
			// 修改
			if(organizationServicePackageService.update(organizationServicePackagePass) > 0){
				// 添加机构服务包日志
				/* 获取用户缓存信息 */
				AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
				
				Integer operationUserId = (Integer) session.get(Map.class).get("id");
				OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
				packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
				packagelog.setOrganizationServicePackageId(id);
				packagelog.setOrganizationUserId(operationUserId);
				packagelog.setStatus(organizationServicePackagePass.getStatus());
				packagelog.setCreateDate(new Date());
				packagelog.setRemark("审核通过");
				if(organizationServicePackageLogService.insert(packagelog) > 0)
				{
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.status.error"));
		
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 审核不通过
	*/
	@Transactional
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:notPass" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/notPass/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationServicePackageNotPass(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.NotPass.class) @RequestBody OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		OrganizationServicePackage organizationServicePackageNotPass = new OrganizationServicePackage();
		organizationServicePackageNotPass.setAuditRemark(organizationServicePackage.getAuditRemark());
		// 设置id
		organizationServicePackageNotPass.setId(id);
		// 判断数据是否存在
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackageNotPass);
		if (organizationServicePackageMap == null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 状态为 审核中 才能审核
		if ((int)organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.EXAMINE.getValue()) {
			// 设置状态为不通过
			organizationServicePackageNotPass.setStatus(ServiceStatusCode.OrganizationServicePackage.NOTPASS.getValue());
			// 修改
			if(organizationServicePackageService.update(organizationServicePackageNotPass) > 0){
				// 添加机构服务包日志
				/* 获取用户缓存信息 */
				AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
				if (null == session) {
					return new JsonApi(ApiCode.UNAUTHORIZED);
				}
				Integer operationUserId = (Integer) session.get(Map.class).get("id");
				OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
				packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
				packagelog.setOrganizationServicePackageId(id);
				packagelog.setOrganizationUserId(operationUserId);
				packagelog.setStatus(organizationServicePackageNotPass.getStatus());
				packagelog.setCreateDate(new Date());
				packagelog.setRemark(organizationServicePackage.getAuditRemark());
				if(organizationServicePackageLogService.insert(packagelog) > 0)
				{
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.status.error"));
		
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 上架
	*/
	@Transactional
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:shelves" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/shelves/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationServicePackageShelves(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.Shelves.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		OrganizationServicePackage organizationServicePackageShelves = new OrganizationServicePackage();
		// 设置id
		organizationServicePackageShelves.setId(id);
		// 判断数据是否存在
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackageShelves);
		if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 状态为审核通过或下架才能上架
		if ((int)organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.PASS.getValue()
				|| (int)organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.OFFTHESHELF.getValue()) {
			// 设置状态为上架
			organizationServicePackageShelves.setStatus(ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue());
			// 判断该种包是否存在已上架
			OrganizationServicePackage repeatServicePackage = new OrganizationServicePackage();
			repeatServicePackage.setOrganizationId(Integer.parseInt(organizationServicePackageMap.get("organizationId").toString()));
			repeatServicePackage
					.setServicePackageTypeId(Integer.parseInt(organizationServicePackageMap.get("servicePackageTypeId").toString()));
			repeatServicePackage.setName(organizationServicePackageMap.get("name").toString());
			repeatServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue());
			Map<String, Object> servicePackageRepeatResult = organizationServicePackageService.getRepeat(repeatServicePackage);
			if (MapUtils.isNotEmpty(servicePackageRepeatResult)) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.data.exist"));
			}
			// 修改
			if(organizationServicePackageService.update(organizationServicePackageShelves) > 0){
				// 添加机构服务包日志
				/* 获取用户缓存信息 */
				AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
				Integer operationUserId = (Integer) session.get(Map.class).get("id");
				OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
				packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
				packagelog.setOrganizationServicePackageId(id);
				packagelog.setOrganizationUserId(operationUserId);
				packagelog.setStatus(organizationServicePackageShelves.getStatus());
				packagelog.setCreateDate(new Date());
				packagelog.setRemark("上架");
				if(organizationServicePackageLogService.insert(packagelog) > 0)
				{
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.status.error"));
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param id
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 下架
	 */
	@Transactional
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:offShelf" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/package/offShelf/{id}" }, method = RequestMethod.PUT)
	public JsonApi organizationServicePackageOffShelf(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.OffShelf.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token) {
		OrganizationServicePackage organizationServicePackageOffShelf = new OrganizationServicePackage();
		// 设置id
		organizationServicePackageOffShelf.setId(id);
		// 判断数据是否存在
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackageOffShelf);
		if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 状态上架才能下架
		if ((int)organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue()) {
			// 设置状态为下架
			organizationServicePackageOffShelf.setStatus(ServiceStatusCode.OrganizationServicePackage.OFFTHESHELF.getValue());
			// 修改
			if(organizationServicePackageService.update(organizationServicePackageOffShelf) > 0){
				// 添加机构服务包日志
				/* 获取用户缓存信息 */
				AuthenticationSession session = cacheManager.getSession(new AuthenticationToken(BaseGlobal.CACHE_ORGANIZATION_USER, token));
				Integer operationUserId = (Integer) session.get(Map.class).get("id");
				OrganizationServicePackageLog packagelog = new OrganizationServicePackageLog();
				packagelog.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
				packagelog.setOrganizationServicePackageId(id);
				packagelog.setOrganizationUserId(operationUserId);
				packagelog.setStatus(organizationServicePackageOffShelf.getStatus());
				packagelog.setCreateDate(new Date());
				packagelog.setRemark("下架");
				if(organizationServicePackageLogService.insert(packagelog) > 0)
				{
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
			return new JsonApi(ApiCode.FAIL);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.status.error"));
	}

	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 查询可以复制的（就是上架的）父机构服务包列表
	 */
	@RequiresAuthentication(value = { "organization:configure:organizationServicePackage:pidList" },level=Level.OPERATION)
	@RequestMapping(value = { "/parent/organization/service/packages" }, method = RequestMethod.GET)
	public JsonApi parentOrganizationPackages(
			@Validated(OrganizationServicePackage.ParentOrganizationPackages.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 设置机构id
		organizationServicePackage.setOrganizationId(organizationId);
		organizationServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue());
		// 分页设置
		Page<?> page = PageHelper.startPage(organizationServicePackage.getPage(),
				organizationServicePackage.getPageSize());
		// 查询父机构服务包列表
		List<Map<String, Object>> parentOrganizationPackagesList = organizationServicePackageService
				.getParentOrganizationPackages(organizationServicePackage);
		if (parentOrganizationPackagesList!=null && !parentOrganizationPackagesList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), parentOrganizationPackagesList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 需要本机构审核的包
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:needAuditPackages" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/packages/need/audit/packages" }, method = RequestMethod.GET)
	public JsonApi needAuditPackages(
			@Validated(OrganizationServicePackage.NeedAuditPackages.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		
		// 设置审核机构id
		organizationServicePackage.setAuditOrganizationId(organizationId);
		// 审核中
		organizationServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.EXAMINE.getValue());
		// 分页设置
		Page<?> page = PageHelper.startPage(organizationServicePackage.getPage(),organizationServicePackage.getPageSize());
		// 查询需要本机构审核的包列表
		List<Map<String, Object>> organizationServicePackageList = organizationServicePackageService.getList(organizationServicePackage);
		if (organizationServicePackageList !=null && !organizationServicePackageList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationServicePackageList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月23日
	 * @version 1.0
	 * @description 机构服务包列表
	 */
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:packages" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/packages" }, method = RequestMethod.GET)
	public JsonApi organizationServicePackages(
			@Validated(OrganizationServicePackage.SelectAll.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		// 设置机构id
		organizationServicePackage.setOrganizationId(organizationId);
		// 分页设置
		Page<?> page = PageHelper.startPage(organizationServicePackage.getPage(),organizationServicePackage.getPageSize());
		// 查询包列表
		List<Map<String, Object>> organizationServicePackageList = organizationServicePackageService.getList(organizationServicePackage);
		if (organizationServicePackageList !=null && !organizationServicePackageList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationServicePackageList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationTeamId
	* @param organizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年4月17日
	* @version 1.0
	* @description 某个团队有或者没有的服务包列表
	*/
	@RequiresAuthentication( value = { "organization:configure:organizationServicePackage:authorizationTeam:packages" },level=Level.OPERATION)
	@RequestMapping(value = { "/organization/service/authorization/{organizationTeamId}/packages" }, method = RequestMethod.GET)
	public JsonApi getOrganizationServiceAuthorizationTeamPackages(
			@PathVariable("organizationTeamId")Integer organizationTeamId,
			@Validated(OrganizationServicePackage.SelectAll.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		// 设置机构id
		organizationServicePackage.setOrganizationId(organizationId);
		organizationServicePackage.setOrganizationTeamId(organizationTeamId);
		// 分页设置
		Page<?> page = PageHelper.startPage(organizationServicePackage.getPage(),organizationServicePackage.getPageSize());
		// 查询包列表
		List<Map<String, Object>> organizationServicePackageList = organizationServicePackageService.getOrganizationServiceAuthorizationTeamPackages(organizationServicePackage);
		if (organizationServicePackageList !=null && !organizationServicePackageList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), organizationServicePackageList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param organizationServicePackage
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年3月22日
	 * @version 1.0
	 * @description 复制上级机构服务包（状态：上线）
	 */
	@Transactional
	@RequiresAuthentication(value = {"organization:configure:organizationServicePackage:copyParentOrganizationPackages" },level=Level.OPERATION)
	@RequestMapping(value = { "/copy/parent/organization/service/package/{id}" }, method = RequestMethod.GET)
	public JsonApi copyParentOrganizationPackage(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.CopyParentOrganizationPackage.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 设置id
		organizationServicePackage.setId(id);
		// 查询机构服务包详情
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackage);
		if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		
		// 获取当前机构信息
		Organization organization = new Organization();
		organization.setId(organizationId);
		Map<String, Object> organizationMap = organizationService.getOne(organization);
		if (organizationMap==null || organizationMap.isEmpty()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.is.not.exists"));
		}
		// 判断要复制的包是不是上级机构的服务包 
		if (!organizationServicePackageMap.get("organizationId").equals(organizationMap.get("pid"))) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.is.not.parent.organization.service.package"));
		}
		// 判断该包是否能进行复制：状态：上架
		if ((int) organizationServicePackageMap.get("status") != ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.status.error"));
		}
		// 新增时判断该种包是否存在
		OrganizationServicePackage repeatServicePackage = new OrganizationServicePackage();
		repeatServicePackage.setOrganizationId(organizationId);
		repeatServicePackage
				.setServicePackageTypeId(Integer.parseInt(organizationServicePackageMap.get("servicePackageTypeId").toString()));
		repeatServicePackage.setName(organizationServicePackageMap.get("name").toString());
		repeatServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue());
		Map<String, Object> servicePackageRepeatResult = organizationServicePackageService.getRepeat(repeatServicePackage);
		if (MapUtils.isNotEmpty(servicePackageRepeatResult)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.data.exist"));
		}
		// 新增机构服务包
		OrganizationServicePackage newServicePackage = new OrganizationServicePackage();
		newServicePackage.setPid(id); 
		newServicePackage.setAuditOrganizationId((Integer)organizationServicePackageMap.get("auditOrganizationId"));
		newServicePackage.setAuditRemark((String)organizationServicePackageMap.get("auditRemark"));
		newServicePackage.setOrganizationId(organizationId);
		newServicePackage.setOriginalPrice(new BigDecimal(organizationServicePackageMap.get("originalPrice").toString()));
		newServicePackage.setServicePackageTypeId(Integer.parseInt(organizationServicePackageMap.get("servicePackageTypeId").toString()));
		newServicePackage.setName(organizationServicePackageMap.get("name").toString());
		newServicePackage.setPrice(new BigDecimal(organizationServicePackageMap.get("price").toString()));
		//newServicePackage.setDiscount(new Float(organizationServicePackageMap.get("discount").toString()));
		newServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.THESHELVES.getValue());
		newServicePackage.setAcquisitiveType(Integer.parseInt(organizationServicePackageMap.get("acquisitiveType").toString()));
		newServicePackage.setAcquisitive(Integer.parseInt(organizationServicePackageMap.get("acquisitive").toString()));
		
		newServicePackage.setCover(null != organizationServicePackageMap.get("cover")?organizationServicePackageMap.get("cover").toString():null);
		newServicePackage.setProtocol(null != organizationServicePackageMap.get("protocol")?organizationServicePackageMap.get("protocol").toString():null);
		newServicePackage.setRemark(null != organizationServicePackageMap.get("remark")?organizationServicePackageMap.get("remark").toString():null);
		newServicePackage.setCreateDate(new Date());
		if (organizationServicePackageService.insert(newServicePackage) > 0) {
			// 查询服务包的人群类型 
			OrganizationPackageUserType packageUserTypeRepeat=new OrganizationPackageUserType();
			packageUserTypeRepeat.setOrganizationServicePackageId(id);
			List<Map<String, Object>> packageUserTypeList=organizationPackageUserTypeService.getList(packageUserTypeRepeat);
			if (packageUserTypeList!=null && !packageUserTypeList.isEmpty()) {
				List<OrganizationPackageUserType> userTypeList=new ArrayList<>();
				OrganizationPackageUserType userType = null;
				/* 设置服务包内的人群类型 */
				for (int i = 0; i < packageUserTypeList.size(); i++) {
					userType = new OrganizationPackageUserType();
					userType.setOrganizationServicePackageId(newServicePackage.getId());
					userType.setUserTypeId(Integer.parseInt(packageUserTypeList.get(i).get("userTypeId").toString()));
					userTypeList.add(userType);
				}
				OrganizationPackageUserType organizationPackageUserType = new OrganizationPackageUserType();
				organizationPackageUserType.setOrganizationPackageUserTypes(userTypeList);
				// 批量新增服务包的人群类型 
				int rows =organizationPackageUserTypeService.batchInsert(organizationPackageUserType.getOrganizationPackageUserTypes());
				if ( rows == userTypeList.size()) {
					/* 查询服务包中的服务项目并设置 */
					OrganizationPackageService packageServiceRepeat = new OrganizationPackageService();
					packageServiceRepeat.setOrganizationServicePackageId(id);
					List<Map<String, Object>> serviceList = organizationPackageServiceService.getList(packageServiceRepeat);
					if (serviceList!=null && !serviceList.isEmpty()) {
						List<OrganizationPackageService> packageServiceList = new ArrayList<>();
						OrganizationPackageService service = null;
						/* 设置服务包内的服务项目 */
						for (int i = 0; i < serviceList.size(); i++) {
							service = new OrganizationPackageService();
							service.setServiceTypeId(Integer.parseInt(serviceList.get(i).get("serviceTypeId").toString()));
							service.setOrganizationServicePackageId(newServicePackage.getId());
							service.setPrice(new BigDecimal(serviceList.get(i).get("price").toString()));
							service.setName(serviceList.get(i).get("name").toString());
							service.setTimes(Integer.parseInt(serviceList.get(i).get("times").toString()));
							packageServiceList.add(service);
						}
						OrganizationPackageService organizationPackageServices = new OrganizationPackageService();
						organizationPackageServices.setOrganizationPackageServices(packageServiceList);
						// 批量新增服务包内服务项
						int rowss=organizationPackageServiceService.batchInsert(organizationPackageServices.getOrganizationPackageServices());
						if ( rowss== packageServiceList.size()) {
							return new JsonApi(ApiCode.OK);
						}else {
							throw new RuntimeException();
						}
					}
					return new JsonApi(ApiCode.OK);
				}else{
					throw new RuntimeException();
				}
			}
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param organizationServicePackage
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月23日
	* @version 1.0
	* @description 复制自己机构服务包
	*/
	@Transactional
	@RequiresAuthentication( value = {"organization:configure:organizationServicePackage:copyOwnOrganizationPackages" },level=Level.OPERATION)
	@RequestMapping(value = { "/copy/own/organization/service/package/{id}" }, method = RequestMethod.GET)
	public JsonApi copyOwnOrganizationPackage(@PathVariable("id") Integer id,
			@Validated(OrganizationServicePackage.CopyOwnOrganizationPackage.class) OrganizationServicePackage organizationServicePackage,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId) {
		// 设置id
		organizationServicePackage.setId(id);
		// 查询机构服务包详情
		Map<String, Object> organizationServicePackageMap = organizationServicePackageService.getOne(organizationServicePackage);
		if (organizationServicePackageMap==null || organizationServicePackageMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 判断是不是自己机构
		if (!organizationServicePackageMap.get("organizationId").equals(organizationId)) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.is.not.own.organization"));
		}
		// 判断该包是否能进行复制：状态：下架 ,审核不通过可以复制
		if ((int) organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.OFFTHESHELF.getValue() ||
				(int) organizationServicePackageMap.get("status") == ServiceStatusCode.OrganizationServicePackage.NOTPASS.getValue()) {
			// 新增时判断该种包是否存在
			OrganizationServicePackage repeatServicePackage = new OrganizationServicePackage();
			repeatServicePackage.setOrganizationId(Integer.parseInt(organizationServicePackageMap.get("organizationId").toString()));
			repeatServicePackage
			.setServicePackageTypeId(Integer.parseInt(organizationServicePackageMap.get("servicePackageTypeId").toString()));
			repeatServicePackage.setName(organizationServicePackageMap.get("name").toString());
			repeatServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.SUBMITED.getValue());
			Map<String, Object> servicePackageMap= organizationServicePackageService.getRepeat(repeatServicePackage);
			if (servicePackageMap!=null && !servicePackageMap.isEmpty()) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.data.exist"));
			}
			// 新增机构服务包
			OrganizationServicePackage newServicePackage = new OrganizationServicePackage();
			newServicePackage.setPid(id);
			if (null == organizationServicePackage.getAuditOrganizationId()) {
				newServicePackage.setAuditOrganizationId(Integer.parseInt(organizationServicePackageMap.get("auditOrganizationId").toString()));
			}
			newServicePackage.setAuditOrganizationId(organizationServicePackage.getAuditOrganizationId());
			newServicePackage.setOriginalPrice(new BigDecimal(organizationServicePackageMap.get("originalPrice").toString()));
			newServicePackage.setOrganizationId(Integer.parseInt(organizationServicePackageMap.get("organizationId").toString()));
			newServicePackage.setServicePackageTypeId(Integer.parseInt(organizationServicePackageMap.get("servicePackageTypeId").toString()));
			newServicePackage.setName(organizationServicePackageMap.get("name").toString());
			newServicePackage.setPrice(new BigDecimal(organizationServicePackageMap.get("price").toString()));
			//newServicePackage.setDiscount(new Float(organizationServicePackageMap.get("discount").toString()));
			newServicePackage.setStatus(ServiceStatusCode.OrganizationServicePackage.SUBMITED.getValue());
			newServicePackage.setAcquisitiveType(Integer.parseInt(organizationServicePackageMap.get("acquisitiveType").toString()));
			newServicePackage.setAcquisitive(Integer.parseInt(organizationServicePackageMap.get("acquisitive").toString()));
			newServicePackage.setCover(organizationServicePackageMap.get("cover").toString());
			newServicePackage.setProtocol(organizationServicePackageMap.get("protocol").toString());
			newServicePackage.setRemark(organizationServicePackageMap.get("remark").toString());
			newServicePackage.setCreateDate(new Date());
			if (organizationServicePackageService.insert(newServicePackage) > 0) {
				// 查询服务包的人群类型 
				OrganizationPackageUserType packageUserTypeRepeat=new OrganizationPackageUserType();
				packageUserTypeRepeat.setOrganizationServicePackageId(id);
				List<Map<String, Object>> packageUserTypeList=organizationPackageUserTypeService.getList(packageUserTypeRepeat);
				if (packageUserTypeList!=null && !packageUserTypeList.isEmpty()) {
					List<OrganizationPackageUserType> userTypeList=new ArrayList<>();
					OrganizationPackageUserType userType = null;
					/* 设置服务包内的人群类型 */
					for (int i = 0; i < packageUserTypeList.size(); i++) {
						userType = new OrganizationPackageUserType();
						userType.setOrganizationServicePackageId(newServicePackage.getId());
						userType.setUserTypeId(Integer.parseInt(packageUserTypeList.get(i).get("userTypeId").toString()));
						userTypeList.add(userType);
					}
					OrganizationPackageUserType organizationPackageUserType = new OrganizationPackageUserType();
					organizationPackageUserType.setOrganizationPackageUserTypes(userTypeList);
					// 批量新增服务包的人群类型 
					int rows=organizationPackageUserTypeService.batchInsert(organizationPackageUserType.getOrganizationPackageUserTypes());
					if (rows == userTypeList.size()) {
						/* 查询服务包中的服务项目并设置 */
						OrganizationPackageService packageServiceRepeat = new OrganizationPackageService();
						packageServiceRepeat.setOrganizationServicePackageId(id);
						List<Map<String, Object>> serviceList = organizationPackageServiceService.getList(packageServiceRepeat);
						if (CollectionUtils.isNotEmpty(serviceList)) {
							List<OrganizationPackageService> packageServiceList = new ArrayList<>();
							OrganizationPackageService service = null;
							/* 设置服务包内的服务项目 */
							for (int i = 0; i < serviceList.size(); i++) {
								service = new OrganizationPackageService();
								service.setServiceTypeId(Integer.parseInt(serviceList.get(i).get("serviceTypeId").toString()));
								service.setOrganizationServicePackageId(newServicePackage.getId());
								service.setPrice(new BigDecimal(serviceList.get(i).get("price").toString()));
								service.setName(serviceList.get(i).get("name").toString());
								service.setTimes(Integer.parseInt(serviceList.get(i).get("times").toString()));
								packageServiceList.add(service);
							}
							OrganizationPackageService organizationPackageServices = new OrganizationPackageService();
							organizationPackageServices.setOrganizationPackageServices(packageServiceList);
							// 批量新增服务包内服务项
							int rowss=organizationPackageServiceService.batchInsert(organizationPackageServices.getOrganizationPackageServices());
							if ( rowss== packageServiceList.size()) {
								return new JsonApi(ApiCode.OK);
							}else{
								throw new RuntimeException();
							}
						}
						return new JsonApi(ApiCode.OK);
					}else{
						throw new RuntimeException();
					}
				}
				return new JsonApi(ApiCode.OK);
			}
			return new JsonApi(ApiCode.FAIL);
		}
		return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("organization.service.package.status.error"));
		
	}
}
