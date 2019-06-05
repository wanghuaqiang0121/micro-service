package org.module.organization.configure.service;

import java.util.List;
import java.util.Map;

import org.module.organization.configure.dao.DepartmentMapper;
import org.module.organization.configure.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2017年12月6日
 * @Version
 * @Description 科室
 */
@Service
public class DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;

	public	int insert(Department department) {
		return departmentMapper.insert(department);

	}

	public	int update(Department department) {
		return departmentMapper.update(department);
	}

	public	int delete(Department department) {
		return departmentMapper.delete(department);
	}

	public	Map<String, Object> getRepeat(Department department) {
		return departmentMapper.getRepeat(department);
	}

	public	Map<String, Object> getOne(Department department) {
		return departmentMapper.getOne(department);
	}

	public	List<Map<String, Object>> getList(Department department){
		return departmentMapper.getList(department);
	}
}
