package org.module.user.service;

import java.util.List;
import java.util.Map;

import org.module.user.dao.itv.UserItvAccountMapper;
import org.module.user.domain.itv.UserItvAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserItvAccountService {

	@Autowired
	private UserItvAccountMapper itvAccountMapper;

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userItvAccount
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description itv绑定用户列表
	*/
	public List<Map<String, Object>> getList(UserItvAccount userItvAccount) {
		return itvAccountMapper.getList(userItvAccount);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userItvAccount
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 绑定用户
	*/
	public int insert(UserItvAccount userItvAccount) {
		return itvAccountMapper.insert(userItvAccount);
	}

	/** 
	* @author <font color="green"><b>Wang.HuaQiang</b></font>
	* @param userItvAccount
	* @return 
	* @date 2018年4月9日
	* @version 1.0
	* @description 判断是否重复
	*/
	public Map<String, Object> getRepeat(UserItvAccount userItvAccount) {
		return itvAccountMapper.getRepeat(userItvAccount);
	}

	public Map<String, Object> getOne(UserItvAccount userItvAccount) {
		return itvAccountMapper.getOne(userItvAccount);
	}

	public int delete(UserItvAccount userItvAccount) {
		return itvAccountMapper.delete(userItvAccount);
	}
	
}
