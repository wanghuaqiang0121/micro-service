package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class ServiceType extends BaseEntity {

	private Integer id;
	@Length(min = 0, max = 32, message = "{service.type.name.length}", groups = { Insert.class})
	@NotBlank(message = "{service.type.name.not.null.valid}", groups = { Insert.class })
	private String name;
	@Length(min = 0, max = 64, message = "{service.type.code.length}", groups = { Insert.class})
	@NotBlank(message = "{service.type.code.not.null.valid}", groups = { Insert.class })
	private String code;
	// @NotBlank(message = "{service.type.index.not.null.valid}", groups = {
	// Insert.class })
	// private String index;
	//@NotNull(message = "{service.type.status.not.null.valid}", groups = { Insert.class })
	private Integer status;
	
	private Integer businessTypeId;

	private String explain;

	private Date createDate;

	public Integer getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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