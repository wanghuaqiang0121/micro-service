package org.module.organization.permission.domain;


import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年5月3日
 * @Version
 * @Description 系统模块表
 */
public class SystemModule extends BaseEntity {
	private Integer id;
	@Length(min = 0, max = 32, message = "{system.module.name.length}", groups = { Insert.class})
	@NotBlank(message = "{system.module.name.notblank.valid}", groups = { Insert.class})
	private String name;
	@Length(min = 0, max = 64, message = "{system.module.code.length}", groups = { Insert.class})
	@NotBlank(message = "{system.module.code.notblank.valid}", groups = { Insert.class})
	private String code;
	private String role;
	@Length(min = 0, max = 128, message = "{system.module.index.length}", groups = { Insert.class})
	@NotBlank(message = "{system.module.index.notblank.valid}", groups = { Insert.class})
	private String index;
	private String logo;
	private Date createDate;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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
