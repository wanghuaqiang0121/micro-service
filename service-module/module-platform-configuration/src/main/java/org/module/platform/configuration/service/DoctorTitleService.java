package org.module.platform.configuration.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.platform.configuration.dao.DoctorTitleMapper;
import org.module.platform.configuration.domain.DoctorTitle;
import org.springframework.stereotype.Service;

@Service
public class DoctorTitleService {

	@Resource
	private DoctorTitleMapper mapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTitle
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 判断数据是否重复
	*/
	public Map<String, Object> getRepeat(DoctorTitle doctorTitle) {
		return mapper.getRepeat(doctorTitle);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTitle
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 新增医生职称
	*/
	public int insert(DoctorTitle doctorTitle) {
		return mapper.insert(doctorTitle);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTitle
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 查看医生职称详情
	*/
	public Map<String, Object> getOne(DoctorTitle doctorTitle) {
		return mapper.getOne(doctorTitle);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTitle
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 查看医生职称列表
	*/
	public List<Map<String, Object>> getList(DoctorTitle doctorTitle) {
		return mapper.getList(doctorTitle);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param doctorTitle
	* @return 
	* @date 2018年4月13日
	* @version 1.0
	* @description 修改医生职称列表
	*/
	public int update(DoctorTitle doctorTitle) {
		return mapper.update(doctorTitle);
	}
	
}
