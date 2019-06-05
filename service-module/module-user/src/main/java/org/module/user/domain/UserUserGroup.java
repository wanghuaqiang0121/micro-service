package org.module.user.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class UserUserGroup extends BaseEntity {
    /**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月27日
     * @Version UserUserGroup
     * @Description 用户组管理者的成员列表
     */
    public interface UsersByGroup {

	}
    /**
     * @author <font color="red"><b>Wang.HuaQiang</b></font>
     * @Date 2018年4月9日
     * @Version UserUserGroup
     * @Description itv用户组管理者的成员列表
     */
    public interface ItvUsersByGroup {
    	
    }

	private Integer id;

	//@NotNull(message="{user.user.group.userId.is.not.null}",groups = {ItvUsersByGroup.class})
    private Integer userId;

    private Integer userGroupId;

    private String relation;
    
    private String relationName;

    private Integer trust;

    private Integer[] trusts;
    
    private Date createDatetime;

    
    public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

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
    
    public Integer[] getTrusts() {
		return trusts;
	}

	public void setTrusts(Integer[] trusts) {
		this.trusts = trusts;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,UsersByGroup.class,ItvUsersByGroup.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,UsersByGroup.class,ItvUsersByGroup.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}