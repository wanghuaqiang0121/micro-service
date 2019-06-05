package org.module.platform.configuration.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class BoneScoreTable extends BaseEntity {
    /**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年7月3日
     * @Version RBoneScoreTable
     * @Description 骨头等级
     */
    public interface getBoneGrade {

	}

	/**
     * @author <font color="red"><b>Gong.YiYang</b></font>
     * @Date 2018年7月3日
     * @Version RBoneScoreTable
     * @Description 骨头类型
     */
    public interface getBoneType {

	}

	private Integer id;

    private Integer sex;

    private String boneType;

    private Integer boneScore;

    private String boneGrade;

    private String type;

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

    public String getBoneType() {
        return boneType;
    }

    public void setBoneType(String boneType) {
        this.boneType = boneType;
    }

    public Integer getBoneScore() {
        return boneScore;
    }

    public void setBoneScore(Integer boneScore) {
        this.boneScore = boneScore;
    }

    public String getBoneGrade() {
        return boneGrade;
    }

    public void setBoneGrade(String boneGrade) {
        this.boneGrade = boneGrade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class,getBoneType.class,getBoneGrade.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class,getBoneType.class ,getBoneGrade.class})
	public Integer getPageSize() {
		return super.getPageSize();
	}
}