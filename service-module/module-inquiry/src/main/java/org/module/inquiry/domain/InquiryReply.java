package org.module.inquiry.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;
import org.service.core.entity.BaseEntity.SelectAll;

public class InquiryReply extends BaseEntity {
    private Integer id;

    @NotNull(message = "{inquiry.id.not.null.valid}", groups = { Insert.class})
    private Integer inquiryId;

    private Integer type;
    
    @NotBlank(message = "{content.not.blank.valid}", groups = { Insert.class})
    private String content;

    private Date createDate;
    
    private Integer inquiriesNum;
    
    public Integer getInquiriesNum() {
		return inquiriesNum;
	}

	public void setInquiriesNum(Integer inquiriesNum) {
		this.inquiriesNum = inquiriesNum;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Integer inquiryId) {
        this.inquiryId = inquiryId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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