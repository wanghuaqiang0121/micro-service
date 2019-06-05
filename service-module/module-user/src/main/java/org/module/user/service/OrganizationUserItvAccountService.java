package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.itv.OrganizationUserItvAccountMapper;
import org.module.user.domain.itv.OrganizationUserItvAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationUserItvAccountService {

	@Autowired
	private OrganizationUserItvAccountMapper mapper;

	public Map<String, Object> getRepeat(OrganizationUserItvAccount organizationUserItvAccount) {
		return mapper.getRepeat(organizationUserItvAccount);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param organizationUserItvAccount
	* @return 
	* @date 2018年4月10日
	* @version 1.0
	* @description itv绑定医生
	*/
	public int insert(OrganizationUserItvAccount organizationUserItvAccount) {
		return mapper.insert(organizationUserItvAccount);
	}

	public List<Map<String, Object>> getList(OrganizationUserItvAccount organizationUserItvAccount) {
		return  mapper.getList(organizationUserItvAccount);
	}

	public Map<String, Object> getOne(OrganizationUserItvAccount organizationUserItvAccount) {
		return mapper.getOne(organizationUserItvAccount);
	}

	public int delete(OrganizationUserItvAccount organizationUserItvAccount) {
		return mapper.delete(organizationUserItvAccount);
	}
	
	
}
