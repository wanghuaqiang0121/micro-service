package org.module.platform.configuration.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

public class BoneStandardGrade extends BaseEntity {
    private Integer id;
    @NotBlank(message="{bone.standard.grade.boneType.is.not.null}",groups={Insert.class})
    private String boneType;
    @NotBlank(message="{bone.standard.grade.boneGrade.is.not.null}",groups={Insert.class})
    private String boneGrade;

    private String featureDescription;

    private Date createDate;
    @NotBlank(message="{bone.standard.grade.bonePhoto.is.not.null}",groups={Insert.class})
    private String bonePhoto;
    
	public BoneStandardGrade() {
		super();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getBoneType() {
        return boneType;
    }

    public void setBoneType(String boneType) {
        this.boneType = boneType;
    }

    public String getBoneGrade() {
        return boneGrade;
    }

    public void setBoneGrade(String boneGrade) {
        this.boneGrade = boneGrade;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBonePhoto() {
        return bonePhoto;
    }

    public void setBonePhoto(String bonePhoto) {
        this.bonePhoto = bonePhoto;
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