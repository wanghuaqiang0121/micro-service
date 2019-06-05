package org.module.organization.configure.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.module.organization.configure.domain.Organization;
import org.module.organization.configure.domain.RecommendOrganization;
import org.module.organization.configure.global.OrganizationStatusCode;
import org.module.organization.configure.message.Prompt;
import org.module.organization.configure.service.RecommendOrganizationService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年3月19日
 * @Version 
 * @Description 推荐机构表
 */
@RestController
public class RecommendOrganizationController {

	@Autowired
	private RecommendOrganizationService recommendOrganizationService;
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 添加推荐机构
	*/
	@RequiresAuthentication( value = { "organization:configure:recommendOrganization:insert" },level=Level.OPERATION)
	@RequestMapping(value = { "/recommend/organization" }, method = RequestMethod.POST)
	public JsonApi addRecommendOrganization(
			@Validated(BaseEntity.Insert.class) @RequestBody RecommendOrganization recommendOrganization,BindingResult result){
		// 查询数据是否存在重复
		Map<String, Object> recommendOrganizationRepeatMap = recommendOrganizationService.getRepeat(recommendOrganization);
		if (recommendOrganizationRepeatMap!=null && !recommendOrganizationRepeatMap.isEmpty()) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("recommend.organization.site.is.exists"));
		}
		// 获取某一机构的推荐机构最大排序号 
		RecommendOrganization recommendOrganizationMaxSort = new RecommendOrganization();
		recommendOrganizationMaxSort.setOrganizationId(recommendOrganization.getOrganizationId());
		Map<String, Object> organizationMaxSortMap = recommendOrganizationService.getRecommendOrganizationMaxSort(recommendOrganizationMaxSort);
		if (organizationMaxSortMap==null || organizationMaxSortMap.isEmpty()) {
			//没有推荐机构最大号：设置起始号1开始
			recommendOrganization.setSort(1);
		} else {
			// 设置排序号：取最大号+1
			recommendOrganization.setSort((int) organizationMaxSortMap.get("maxSort") + 1);
		}
		// 添加推荐机构
		if (recommendOrganizationService.insert(recommendOrganization) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 某机构的推荐机构列表
	*/
	@RequiresAuthentication( value = { "organization:configure:recommendOrganization:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/recommend/organizations" }, method = RequestMethod.GET)
	public JsonApi recommendOrganizations(
			@Validated(RecommendOrganization.GetRecommendOrganizationsList.class) RecommendOrganization recommendOrganization,BindingResult result){
		// 分页设置
		Page<?> page = PageHelper.startPage(recommendOrganization.getPage(), recommendOrganization.getPageSize());
		// 设置查询条件
		Organization organization = (recommendOrganization.getOrganization() != null) ? recommendOrganization.getOrganization() : new Organization();
		// 机构建立方式：公立
		organization.setCreateType(OrganizationStatusCode.OrganizationType.ORGANIZATION_TYPE_PUBLIC.getValue());
		// 机构状态：启用
		organization.setStatus(OrganizationStatusCode.OrganizationType.ENABLE.getValue());
		recommendOrganization.setOrganization(organization);
		// 查询推荐机构列表
		List<Map<String, Object>> recommendOrganizationsList = recommendOrganizationService.getRecommendOrganizations(recommendOrganization);
		if (recommendOrganizationsList!=null && !recommendOrganizationsList.isEmpty()) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(), recommendOrganizationsList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	 * @author <font color="green"><b>Wang.HuaQiang</b></font>
	 * @param recommendOrganization
	 * @param result
	 * @param token
	 * @param organizationId
	 * @param moduleId
	 * @return 
	 * @date 2018年3月19日
	 * @version 1.0
	 * @description 未推荐机构列表
	 */
	@RequiresAuthentication( value = { "organization:configure:not:recommendOrganization:list" },level=Level.OPERATION)
	@RequestMapping(value = { "/not/recommend/organization" }, method = RequestMethod.GET)
	public JsonApi notrecommendOrganizations(
			@Validated(RecommendOrganization.GetNotRecommendOrganizationsList.class) RecommendOrganization recommendOrganization,BindingResult result){
		// 分页设置
		Page<?> page = PageHelper.startPage(recommendOrganization.getPage(), recommendOrganization.getPageSize());
		// 查询未推荐机构列表
		List<Map<String, Object>> notRecommendOrganizationsResult = recommendOrganizationService.getNotRecommendOrganizations(recommendOrganization);
		if (CollectionUtils.isNotEmpty(notRecommendOrganizationsResult)) {
			return new JsonApi(ApiCode.OK,new MultiLine(page.getTotal(), notRecommendOrganizationsResult));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param id
	* @param recommendOrganization
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 删除推荐机构
	*/
	@RequiresAuthentication( value = { "organization:configure:recommendOrganization:delete" },level=Level.OPERATION)
	@RequestMapping(value = { "/recommend/organization/{id}" }, method = RequestMethod.DELETE)
	public JsonApi deleteRecommendOrganization(@PathVariable("id") Integer id,
			@Validated(BaseEntity.Delete.class) RecommendOrganization recommendOrganization,BindingResult result){
		// 设置id
		recommendOrganization.setId(id);
		// 删除推荐机构
		if (recommendOrganizationService.delete(recommendOrganization) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @param result
	* @param token
	* @param organizationId
	* @param moduleId
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 推荐机构上移/下移
	*/
	@Transactional
	@RequiresAuthentication( value = { "organization:configure:recommendOrganization:move" },level=Level.OPERATION)
	@RequestMapping(value = { "/recommend/organization/move/{id}" }, method = RequestMethod.PUT)
	public JsonApi moveRecommendOrganization(@PathVariable("id") Integer id,
			@Validated(RecommendOrganization.MoveRecommendOrganization.class) @RequestBody RecommendOrganization recommendOrganization,BindingResult result){
		// 设置id
		recommendOrganization.setId(id);
		// 判断移动类型是否正确
		if((int) recommendOrganization.getMoveType() != OrganizationStatusCode.RecommendOrganization.RECOMMEND_ORGANIZATION_MOVE_UP.getValue()
				&& (int) recommendOrganization.getMoveType() != OrganizationStatusCode.RecommendOrganization.RECOMMEND_ORGANIZATION_MOVE_DOWN.getValue()) {
			return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("recommend.organization.move.type.error"));
		}
		// 查询详情
		Map<String, Object> recommendOrganizationMap = recommendOrganizationService.getOne(recommendOrganization);
		if (recommendOrganizationMap==null || recommendOrganizationMap.isEmpty()) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		// 上移
		if ((int)recommendOrganization.getMoveType()==OrganizationStatusCode.RecommendOrganization.RECOMMEND_ORGANIZATION_MOVE_UP.getValue()) {
			// 获取上移最近一条记录
			RecommendOrganization recommendOrganizationMoveUp=new RecommendOrganization();
			recommendOrganizationMoveUp.setId(id);
			recommendOrganizationMoveUp.setOrganizationId((int)recommendOrganizationMap.get("organizationId"));
			Map<String, Object> moveUpMap=recommendOrganizationService.getRecommendOrganizationMinSortByMoveUp(recommendOrganizationMoveUp);
			// 为空表示已达到上移最大限度
			if (moveUpMap==null || moveUpMap.isEmpty()) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("recommend.organization.move.up.error"));
			}
			// 设置移动数据
			// 当前记录
			RecommendOrganization oldRecommendOrganization=new RecommendOrganization();
			oldRecommendOrganization.setId(id);
			oldRecommendOrganization.setSort((int) moveUpMap.get("sort"));
			// 要交换的数据
			RecommendOrganization newRecommendOrganization=new RecommendOrganization();
			newRecommendOrganization.setId((int)moveUpMap.get("id"));
			newRecommendOrganization.setSort((int) recommendOrganizationMap.get("sort"));
			// 进行交换 
			if (recommendOrganizationService.update(oldRecommendOrganization)>0 && recommendOrganizationService.update(newRecommendOrganization)>0) {
				return new JsonApi(ApiCode.OK);
			}
			return new JsonApi(ApiCode.FAIL);
		}
		// 下移 
		else {
			// 获取下移最近一条记录
			RecommendOrganization recommendOrganizationMoveDown=new RecommendOrganization();
			recommendOrganizationMoveDown.setId(id);
			recommendOrganizationMoveDown.setOrganizationId((int)recommendOrganizationMap.get("organizationId"));
			Map<String, Object> moveDownMap=recommendOrganizationService.getRecommendOrganizationMaxSortByMoveDown(recommendOrganizationMoveDown);
			//为空表示已达到下移最大限度
			if (moveDownMap==null || moveDownMap.isEmpty()) {
				return new JsonApi(ApiCode.FAIL).setMsg(Prompt.bundle("recommend.organization.move.down.error"));
			}
			// 设置移动数据
			// 当前记录
			RecommendOrganization oldRecommendOrganization=new RecommendOrganization();
			oldRecommendOrganization.setId(id);
			oldRecommendOrganization.setSort((int) moveDownMap.get("sort"));
			// 要交换的数据
			RecommendOrganization newRecommendOrganization=new RecommendOrganization();
			newRecommendOrganization.setId((int)moveDownMap.get("id"));
			newRecommendOrganization.setSort((int) recommendOrganizationMap.get("sort"));
			// 进行交换 
			if (recommendOrganizationService.update(oldRecommendOrganization)>0 && recommendOrganizationService.update(newRecommendOrganization)>0) {
				return new JsonApi(ApiCode.OK);
			}
			return new JsonApi(ApiCode.FAIL);
		}
	}
	
	
}
