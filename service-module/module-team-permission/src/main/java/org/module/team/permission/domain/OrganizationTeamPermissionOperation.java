package org.module.team.permission.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class OrganizationTeamPermissionOperation extends BaseEntity {
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年5月16日
	 * @Version OrganizationTeamPermissionMenu
	 * @Description 团队权限（拥有未拥有）的菜单 验证
	 */
	public interface HaveAndNotHaveOperation{};
	
    private Integer id;
    @NotNull(message="{organizationTeamPermissionOperation.organizationTeamPermissionId.is.not.null}",groups={Insert.class})
    private Integer organizationTeamPermissionId;
    @NotNull(message="{organizationTeamPermissionOperation.organizationTeamOperationId.is.not.null}",groups={Insert.class})
    private Integer organizationTeamOperationId;
    private String name;
    private String code;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationTeamPermissionId() {
        return organizationTeamPermissionId;
    }

    public void setOrganizationTeamPermissionId(Integer organizationTeamPermissionId) {
        this.organizationTeamPermissionId = organizationTeamPermissionId;
    }

    public Integer getOrganizationTeamOperationId() {
        return organizationTeamOperationId;
    }

    public void setOrganizationTeamOperationId(Integer organizationTeamOperationId) {
        this.organizationTeamOperationId = organizationTeamOperationId;
    }
    @Override
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class,HaveAndNotHaveOperation.class})
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,HaveAndNotHaveOperation.class })
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}