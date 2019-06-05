package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2019年2月25日
 * @Version
 * @Description 阅片医生价格配置
 */
public class ReadFilmDoctorPrice extends BaseEntity {

	private static final long serialVersionUID = 3099326517282006488L;

	private Integer id;
	
	@NotNull(message = "{read.film.doctor.price.doctor.id.not.null.valid}", groups = { Insert.class })
	private Integer doctorId;
	@NotNull(message = "{read.film.doctor.price.price.id.not.null.valid}", groups = { Insert.class })
	private Double price;
	@NotNull(message = "{read.film.doctor.price.status.id.not.null.valid}", groups = { Insert.class })
	private Boolean status;
	private String remark;
	
	private Date createTime;
	@NotNull(message = "{read.film.doctor.price.organiztion.id.not.null.valid}", groups = { Insert.class })
	private Integer organizationId;
	private String doctorName;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}


	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
