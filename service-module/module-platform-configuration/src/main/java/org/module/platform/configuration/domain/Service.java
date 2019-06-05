package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class Service extends BaseEntity {
    private Integer id;
    @NotNull(message = "{service.serviceTypeId.not.null.valid}", groups = { Insert.class })
    private Integer serviceTypeId;
    @Length(min = 0, max = 32, message = "{service.name.length}", groups = { Insert.class})
    @NotBlank(message = "{service.name.not.null.valid}", groups = { Insert.class })
    private String name;
    private String remark;
    @Length(min = 0, max = 64, message = "{service.code.length}", groups = { Insert.class})
    @NotBlank(message = "{service.code.not.null.valid}", groups = { Insert.class })
    private String code;

    private String index;

    private Date createDate;

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Override
   	@NotNull(message = "{page.empty}", groups = { SelectAll.class})
   	public Integer getPage() {
   		return super.getPage();
   	}

   	@Override
   	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class})
   	public Integer getPageSize() {
   		return super.getPageSize();
   	}
}