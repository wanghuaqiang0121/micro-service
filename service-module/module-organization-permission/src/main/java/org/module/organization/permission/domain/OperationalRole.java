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
 * @Description 运营角色表
 */
public class OperationalRole extends BaseEntity{
	private Integer id;
	@Length(min = 0, max = 32, message = "{operation.role.name.length}", groups = { Insert.class})
	@NotBlank(message = "{operation.role.name.notblank.valid}", groups = { Insert.class})
	private String name;
	@Length(min = 0, max = 32, message = "{operation.role.code.length}", groups = { Insert.class})
	@NotBlank(message = "{operation.role.code.notblank.valid}", groups = { Insert.class})
	private String code;
	@NotNull(message = "{operation.role.isUsed.notnull.valid}", groups = { Insert.class})
	private Boolean isUsed;
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
	public Boolean getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
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
