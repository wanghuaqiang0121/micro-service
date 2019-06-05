package org.module.organization.user.domain.team;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationOrganizationTeam extends BaseEntity {
    /**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月9日
     * @Version OrganizationOrganizationTeam
     * @Description 查询机构用户团队操作列表
     */
    public interface TeamOperations {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月9日
     * @Version OrganizationOrganizationTeam
     * @Description 查询机构用户团队菜单列表
     */
    public interface TeamMenus {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月9日
     * @Version OrganizationOrganizationTeam
     * @Description 查询机构用户团队权限列表
     */
    public interface TeamPermissions {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年3月9日
     * @Version OrganizationOrganizationTeam
     * @Description 查询机构用户团队角色列表
     */
    public interface TeamRoles {

	}

	private Integer id;

    private Integer organizationUserId;
    
    private Integer organizationId;

    private Integer organizationTeamId;

    private Integer organizationTeamRoleId;

    private Integer status;

    
    public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationUserId() {
        return organizationUserId;
    }

    public void setOrganizationUserId(Integer organizationUserId) {
        this.organizationUserId = organizationUserId;
    }

    public Integer getOrganizationTeamId() {
        return organizationTeamId;
    }

    public void setOrganizationTeamId(Integer organizationTeamId) {
        this.organizationTeamId = organizationTeamId;
    }

    public Integer getOrganizationTeamRoleId() {
        return organizationTeamRoleId;
    }

    public void setOrganizationTeamRoleId(Integer organizationTeamRoleId) {
        this.organizationTeamRoleId = organizationTeamRoleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    @Override
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class,TeamRoles.class ,TeamPermissions.class,TeamMenus.class,TeamOperations.class})
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,TeamRoles.class,TeamPermissions.class,TeamMenus.class,TeamOperations.class})
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}