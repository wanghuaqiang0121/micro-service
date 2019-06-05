package org.module.platform.configuration.controller.organization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.module.platform.configuration.domain.organization.ModuleOperationalRole;
import org.module.platform.configuration.domain.organization.OrganizationRole;
import org.module.platform.configuration.global.BaseGlobal;
import org.module.platform.configuration.global.OrganizationStatusCode;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.organization.OrganizationRoleService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
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
* @author <font color="red"><b>Zhang.Xiang.Zheng</b></font> 
* @date 2018年4月18日 
* @version 1.0
* @description 机构角色
 */
@RestController
public class OrganizationRoleController {
	
	
	@Autowired
	private OrganizationRoleService organizationRoleService;
	
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param organizationRole
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 新增机构和角色关联
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationRole:insert" })
	@RequestMapping(value = { "/organization/role" }, method = RequestMethod.POST)
	public JsonApi organizationRoleInsert(@Validated({ BaseEntity.Insert.class }) @RequestBody OrganizationRole organizationRole){
		/*判断数据是否重复*/
		Map<String, Object> organizationRoleMap = organizationRoleService.getRepeat(organizationRole);
		if (organizationRoleMap!=null && !organizationRoleMap.isEmpty()) {
			OrganizationRole organizationRoleNew = new OrganizationRole();
			organizationRoleNew.setId((Integer)organizationRoleMap.get("id"));
			organizationRoleNew.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("id", (Integer)organizationRoleMap.get("id"));
			if (organizationRoleService.update(organizationRoleNew)>0) {
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}else {
			organizationRole.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
			if (organizationRoleService.insert(organizationRole)>0) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("id", organizationRole.getId());
				return new JsonApi(ApiCode.OK,resultMap);
			}
		}
		return new JsonApi(ApiCode.FAIL);
		
	}
	
	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param organizationRole
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年6月6日
	* @version 1.0
	* @description 新增机构和角色关联批量新增
	*/
	@Transactional
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationRole:batchInsert" })
	@RequestMapping(value = { "/organization/role/batch" }, method = RequestMethod.POST)
	public JsonApi organizationRolebatchInsert(
			@Validated({ OrganizationRole.batchInsert.class }) @RequestBody OrganizationRole organizationRole,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId){
		List<Integer> operationalRoleList = organizationRole.getOperationalRoles();
		operationalRoleList.removeAll(Collections.singleton(null));
		HashSet<Integer> hashSet = new HashSet<>(operationalRoleList);
		if (hashSet.size() != operationalRoleList.size()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("operationl.role.is.repeat"));
		}
		List<OrganizationRole> organizationRolesList = new  ArrayList<OrganizationRole>();
		for (Integer operationalRoleId : operationalRoleList) {
			OrganizationRole organizationRoleNew = new OrganizationRole();
			organizationRoleNew.setOrganizationId(organizationRole.getOrganizationId());
			organizationRoleNew.setSystemModuleId(organizationRole.getSystemModuleId());
			organizationRoleNew.setOperationalRoleId(operationalRoleId);
			organizationRoleNew.setStatus(OrganizationStatusCode.Organization.ENABLE.getValue());
			organizationRolesList.add(organizationRoleNew);
		}
		List<Map<String, Object>> organizationRoleList = organizationRoleService.getList(organizationRole);
		if (operationalRoleList == null || operationalRoleList.isEmpty()) {
			if (organizationRoleList == null || organizationRoleList.isEmpty()) {
				return new JsonApi(ApiCode.OK);
			}
			if (organizationRoleService.batchDelete(organizationRole) >0 ) {
				return new JsonApi(ApiCode.OK);
			}
		}else {
			if (organizationRoleList == null || organizationRoleList.isEmpty()) {
				if (organizationRoleService.batchInsert(organizationRolesList) == organizationRolesList.size()) {
					return new JsonApi(ApiCode.OK);
				}
			}
			if (organizationRoleService.batchDelete(organizationRole) >0 ) {
				
				if (organizationRoleService.batchInsert(organizationRolesList) == organizationRolesList.size()) {
					return new JsonApi(ApiCode.OK);
				}
				throw new RuntimeException();
			}
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param moduleOperationalRole
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月18日
	 * @version 1.0
	 * @description 查询机构在该模块下拥有和未拥有的角色列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:moduleOperationalRole:list" })
	@RequestMapping(value = { "/organization/role/isChoose" }, method = RequestMethod.GET)
	public JsonApi moduleOperationalRoleList(@Validated({ModuleOperationalRole.ModuleOperationalRoleList.class})ModuleOperationalRole moduleOperationalRole,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		Page<?> page = PageHelper.startPage(moduleOperationalRole.getPage(), moduleOperationalRole.getPageSize());
		List<Map<String, Object>> operationalRoleList = organizationRoleService.moduleOperationalRoleList(moduleOperationalRole);
		if (operationalRoleList != null && !operationalRoleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), operationalRoleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param organizationRole
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月19日
	 * @version 1.0
	 * @description 查询机构和角色关联列表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationRole:isChoose:list" })
	@RequestMapping(value = { "/organization/module/role" }, method = RequestMethod.GET)
	public JsonApi organizationRoleIsChooseList(@Validated({BaseEntity.SelectAll.class})OrganizationRole organizationRole,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		Page<?> page = PageHelper.startPage(organizationRole.getPage(), organizationRole.getPageSize());
		List<Map<String, Object>> roleList = organizationRoleService.getList(organizationRole);
		if (roleList != null && !roleList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), roleList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Zhang.Xiang.Zheng</b></font>
	 * @param id
	 * @param organizationRole
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return
	 * @date 2018年4月19日
	 * @version 1.0
	 * @description 删除机构和角色关联表
	 */
	@RequiresAuthentication(level=Level.OPERATION, value = { "platform:configure:organizationRole:delete" })
	@RequestMapping(value = { "/organization/role/{id}" }, method = RequestMethod.DELETE)
	public JsonApi organizationRoleUpdate(@PathVariable("id") Integer id,
			@Validated({BaseEntity.Delete.class})OrganizationRole organizationRole,
			BindingResult result, @RequestHeader(required = true, value = BaseGlobal.TOKEN_FLAG) String token,
			@RequestHeader(required = true, value = BaseGlobal.ORGANIZATION_ID) Integer organizationId,
			@RequestHeader(required = true, value = BaseGlobal.MODULE_ID) Integer moduleId) {
		organizationRole.setId(id);
		organizationRole.setStatus(OrganizationStatusCode.Organization.DISABLE.getValue());
		//修改
		if (organizationRoleService.update(organizationRole) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
}
