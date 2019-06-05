package org.module.organization.user.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationUserRole extends BaseEntity {
    /**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年4月2日
     * @Version OrganizationUserRole
     * @Description 菜单列表（用于拦截）
     */
    public interface interceptoMenuList {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年4月2日
     * @Version OrganizationUserRole
     * @Description 操作列表（用于拦截）
     */
    public interface interceptoPerationList {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年4月2日
     * @Version OrganizationUserRole
     * @Description 权限列表（用于拦截）
     */
    public interface interceptPermissionList {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年4月2日
     * @Version OrganizationUserRole
     * @Description 用户角色列表（用于拦截）
     */
    public interface interceptRoleList {

	}

	private Integer id;

    private Integer organizationUserId;

    private Integer organizationRoleId;
    
    private Integer moduleId;
    
    private Integer organizationId;

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

    public Integer getOrganizationRoleId() {
        return organizationRoleId;
    }

    public void setOrganizationRoleId(Integer organizationRoleId) {
        this.organizationRoleId = organizationRoleId;
    }
    
    public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,interceptRoleList.class,interceptPermissionList.class,interceptoPerationList.class,interceptoMenuList.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,interceptRoleList.class,interceptPermissionList.class,interceptoPerationList.class,interceptoMenuList.class  })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}