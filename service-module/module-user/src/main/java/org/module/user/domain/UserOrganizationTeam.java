package org.module.user.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class UserOrganizationTeam extends BaseEntity {
    private Integer id;

    @NotNull(message="{user.id.is.not.null}",groups={SelectAll.class})
    private Integer userId;

    private Integer organizationTeamId;

    private Boolean isSign;

    private Boolean isIncrementService;

    private Boolean isSingleService;

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

    public Integer getOrganizationTeamId() {
        return organizationTeamId;
    }

    public void setOrganizationTeamId(Integer organizationTeamId) {
        this.organizationTeamId = organizationTeamId;
    }

    public Boolean getIsSign() {
        return isSign;
    }

    public void setIsSign(Boolean isSign) {
        this.isSign = isSign;
    }

    public Boolean getIsIncrementService() {
        return isIncrementService;
    }

    public void setIsIncrementService(Boolean isIncrementService) {
        this.isIncrementService = isIncrementService;
    }

    public Boolean getIsSingleService() {
        return isSingleService;
    }

    public void setIsSingleService(Boolean isSingleService) {
        this.isSingleService = isSingleService;
    }
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class})
	public Integer getPageSize() {
		return super.getPageSize();
	}
}