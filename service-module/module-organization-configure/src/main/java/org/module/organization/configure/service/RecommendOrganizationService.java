package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.RecommendOrganizationMapper;
import org.module.organization.configure.domain.RecommendOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendOrganizationService {

	@Autowired
	private RecommendOrganizationMapper recommendOrganizationMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 添加推荐机构
	*/
	public int insert(RecommendOrganization recommendOrganization) {
		return recommendOrganizationMapper.insert(recommendOrganization);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganizationMaxSort
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 获取某一机构的推荐机构最大排序号
	*/
	public Map<String, Object> getRecommendOrganizationMaxSort(RecommendOrganization recommendOrganizationMaxSort) {
		return recommendOrganizationMapper.getRecommendOrganizationMaxSort(recommendOrganizationMaxSort);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 查询数据是否存在重复
	*/
	public Map<String, Object> getRepeat(RecommendOrganization recommendOrganization) {
		return recommendOrganizationMapper.getRepeat(recommendOrganization);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 查询推荐机构列表
	*/
	public List<Map<String, Object>> getRecommendOrganizations(RecommendOrganization recommendOrganization) {
		return recommendOrganizationMapper.getRecommendOrganizations(recommendOrganization);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 删除推荐机构
	*/
	public int delete(RecommendOrganization recommendOrganization) {
		return recommendOrganizationMapper.delete(recommendOrganization);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 查询未推荐机构列表
	*/
	public List<Map<String, Object>> getNotRecommendOrganizations(RecommendOrganization recommendOrganization) {
		return recommendOrganizationMapper.getNotRecommendOrganizations(recommendOrganization);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganizationMoveUp
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 获取上移最近一条记录
	*/
	public Map<String, Object> getRecommendOrganizationMinSortByMoveUp(RecommendOrganization recommendOrganizationMoveUp) {
		return recommendOrganizationMapper.getRecommendOrganizationMinSortByMoveUp(recommendOrganizationMoveUp);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param oldRecommendOrganization
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 修改
	*/
	public int update(RecommendOrganization oldRecommendOrganization) {
		return recommendOrganizationMapper.update(oldRecommendOrganization);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 查询详情
	*/
	public Map<String, Object> getOne(RecommendOrganization recommendOrganization) {
		return recommendOrganizationMapper.getOne(recommendOrganization);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganizationMoveDown
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 获取下移最近一条记录
	*/
	public Map<String, Object> getRecommendOrganizationMaxSortByMoveDown(RecommendOrganization recommendOrganizationMoveDown) {
		return recommendOrganizationMapper.getRecommendOrganizationMaxSortByMoveDown(recommendOrganizationMoveDown);
	}
	
}
