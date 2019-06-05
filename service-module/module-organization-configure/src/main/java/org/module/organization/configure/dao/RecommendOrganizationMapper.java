package org.module.organization.configure.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.domain.RecommendOrganization;
import org.service.core.dao.IBaseMapper;

public interface RecommendOrganizationMapper extends IBaseMapper<RecommendOrganization> {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendOrganization record);

    RecommendOrganization selectByPrimaryKey(Integer id);

    List<RecommendOrganization> selectAll();

    int updateByPrimaryKey(RecommendOrganization record);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganizationMaxSort
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 获取某一机构的推荐机构最大排序号 
	*/
	Map<String, Object> getRecommendOrganizationMaxSort(RecommendOrganization recommendOrganizationMaxSort);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 查询推荐机构列表
	*/
	List<Map<String, Object>> getRecommendOrganizations(RecommendOrganization recommendOrganization);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganization
	* @return 
	* @date 2018年3月19日
	* @version 1.0
	* @description 查询未推荐机构列表
	*/
	List<Map<String, Object>> getNotRecommendOrganizations(RecommendOrganization recommendOrganization);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganizationMoveUp
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 获取上移最近一条记录
	*/
	Map<String, Object> getRecommendOrganizationMinSortByMoveUp(RecommendOrganization recommendOrganizationMoveUp);

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param recommendOrganizationMoveDown
	* @return 
	* @date 2018年3月20日
	* @version 1.0
	* @description 获取下移最近一条记录
	*/
	Map<String, Object> getRecommendOrganizationMaxSortByMoveDown(RecommendOrganization recommendOrganizationMoveDown);
	
}