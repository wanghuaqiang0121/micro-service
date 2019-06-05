package org.module.sign.domain;

import org.service.core.entity.BaseEntity;

public class UserUserType extends BaseEntity{
	
	private Integer id;
	private Integer userId;
	private Integer userTypeId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	
	

}
