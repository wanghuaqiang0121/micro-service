package org.module.organization.configure.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class RecommendOrganization extends BaseEntity {
	
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月19日
	 * @Version RecommendOrganization
	 * @Description 某机构的推荐机构列表
	 */
	public interface GetRecommendOrganizationsList {};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月19日
	 * @Version RecommendOrganization
	 * @Description 某机构的未推荐机构列表
	 */
	public interface GetNotRecommendOrganizationsList {};
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月19日
	 * @Version RecommendOrganization
	 * @Description 移动机构顺序
	 */
	public interface MoveRecommendOrganization {};
	
    private Integer id;

    @NotNull(message = "{recommend.organization.organization.id.is.not.null}", groups = {Insert.class,GetRecommendOrganizationsList.class,GetNotRecommendOrganizationsList.class})
    private Integer organizationId;
    
    @NotNull(message = "{recommend.organization.organization.site.id.is.not.null}", groups = {Insert.class} )
    private Integer organizationSiteId;

    @NotNull(message = "{recommend.organization.recommend.organization.id.is.not.null}", groups = {Insert.class})
    private Integer recommendOrganizationId;

    private Integer sort;
    
    @NotNull(message = "{recommend.organization.move.type.not.null}", groups = { MoveRecommendOrganization.class })
	private Integer moveType;

    private Organization organization;
    
    
    public Integer getOrganizationSiteId() {
		return organizationSiteId;
	}

	public void setOrganizationSiteId(Integer organizationSiteId) {
		this.organizationSiteId = organizationSiteId;
	}

	public Integer getMoveType() {
		return moveType;
	}

	public void setMoveType(Integer moveType) {
		this.moveType = moveType;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getRecommendOrganizationId() {
        return recommendOrganizationId;
    }

    public void setRecommendOrganizationId(Integer recommendOrganizationId) {
        this.recommendOrganizationId = recommendOrganizationId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,GetRecommendOrganizationsList.class,GetNotRecommendOrganizationsList.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,GetRecommendOrganizationsList.class,GetNotRecommendOrganizationsList.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}