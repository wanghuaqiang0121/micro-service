package org.module.bone.age.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class UserUserGroup extends BaseEntity {
    private Integer id;

    private Integer userId;

    private Integer userGroupId;

    private String relation;

    private Integer trust;

    private Date createDatetime;

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

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getTrust() {
        return trust;
    }

    public void setTrust(Integer trust) {
        this.trust = trust;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
    
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}