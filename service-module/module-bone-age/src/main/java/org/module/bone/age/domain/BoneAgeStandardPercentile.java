package org.module.bone.age.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

/**
* @author <font color="red"><b>Zhang.Xiang.Zheng</b></font> 
* @date 2018年5月31日 
* @version 1.0
* @description R骨龄标准百分位数表
 */
public class BoneAgeStandardPercentile extends BaseEntity {
	/**
	* @author <font color="red"><b>Zhang.Xiang.Zheng</b></font> 
	* @date 2018年5月31日 
	* @version 1.0
	* @description 获取标准百分位
	 */
	public interface getStandardPercentile{
		
	};
	
	
	private Integer id;

	@NotNull(message = "{user.sex.notnull.valid}", groups = { getStandardPercentile.class})
    private Integer sex;

    private Float boneAge;

    private Integer boneAgeFraction;

    private String percentile;
    
    private Integer adjacentValues;
    
    @NotBlank(message = "{bone.age.standard.percentile.type.notblank.valid}", groups = { getStandardPercentile.class})
    private String type;

    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Float getBoneAge() {
        return boneAge;
    }

    public void setBoneAge(Float boneAge) {
        this.boneAge = boneAge;
    }

    public Integer getBoneAgeFraction() {
        return boneAgeFraction;
    }

    public void setBoneAgeFraction(Integer boneAgeFraction) {
        this.boneAgeFraction = boneAgeFraction;
    }

    public String getPercentile() {
        return percentile;
    }

    public void setPercentile(String percentile) {
        this.percentile = percentile;
    }
    
	public Integer getAdjacentValues() {
		return adjacentValues;
	}

	public void setAdjacentValues(Integer adjacentValues) {
		this.adjacentValues = adjacentValues;
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