package org.module.organization.configure.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年3月20日
 * @Version
 * @Description 机构用户标签关联表
 */
public class OrganizationUserType extends BaseEntity {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年6月21日
	 * @Version OrganizationUserType
	 * @Description 是否拥有人群类型
	 */
	public interface organizationUserTypehaveAndNotHave {

	}

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年4月16日
	 * @Version OrganizationUserType
	 * @Description 当前机构没有关联的人群类型
	 */
	public interface organizationUserTypeIsNull {

	}

	private Integer id;
	private Integer organizationId;
	@NotNull(message = "{organization.user.type.user.type.id.notnull.valid}", groups = { Insert.class})
	private Integer userTypeId;

	private Integer applyType;
	
	private String name;
	
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

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	
	public Integer getApplyType() {
		return applyType;
	}

	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,organizationUserTypeIsNull.class ,organizationUserTypehaveAndNotHave.class})
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,organizationUserTypeIsNull.class,organizationUserTypehaveAndNotHave.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}
