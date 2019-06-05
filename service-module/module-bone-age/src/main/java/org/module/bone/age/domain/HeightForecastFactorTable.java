package org.module.bone.age.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
* @author <font color="red"><b>Zhang.Xiang.Zheng</b></font> 
* @date 2018年5月31日 
* @version 1.0
* @description 身高预测因素法
 */
public class HeightForecastFactorTable extends BaseEntity {
    private Integer id;

    private Integer type;

    private Float age;

    private Float heightCoefficient;

    private Float ageCoefficient;

    private Float rBoneCoefficient;

    private Integer constant;

    private Float standardDeviation;

    private Float multipleCorrelationCoefficient;

    private Integer sex;

    private Integer menarcheType;

    private Float menarcheCoefficient;

    private Float lastYearHeightAddCoefficient;

    private Float lastYearRBoneAddCoefficient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getAge() {
        return age;
    }

    public void setAge(Float age) {
        this.age = age;
    }

    public Float getHeightCoefficient() {
        return heightCoefficient;
    }

    public void setHeightCoefficient(Float heightCoefficient) {
        this.heightCoefficient = heightCoefficient;
    }

    public Float getAgeCoefficient() {
        return ageCoefficient;
    }

    public void setAgeCoefficient(Float ageCoefficient) {
        this.ageCoefficient = ageCoefficient;
    }

    public Float getrBoneCoefficient() {
        return rBoneCoefficient;
    }

    public void setrBoneCoefficient(Float rBoneCoefficient) {
        this.rBoneCoefficient = rBoneCoefficient;
    }

    public Integer getConstant() {
        return constant;
    }

    public void setConstant(Integer constant) {
        this.constant = constant;
    }

    public Float getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Float standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public Float getMultipleCorrelationCoefficient() {
        return multipleCorrelationCoefficient;
    }

    public void setMultipleCorrelationCoefficient(Float multipleCorrelationCoefficient) {
        this.multipleCorrelationCoefficient = multipleCorrelationCoefficient;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMenarcheType() {
        return menarcheType;
    }

    public void setMenarcheType(Integer menarcheType) {
        this.menarcheType = menarcheType;
    }

    public Float getMenarcheCoefficient() {
        return menarcheCoefficient;
    }

    public void setMenarcheCoefficient(Float menarcheCoefficient) {
        this.menarcheCoefficient = menarcheCoefficient;
    }

    public Float getLastYearHeightAddCoefficient() {
        return lastYearHeightAddCoefficient;
    }

    public void setLastYearHeightAddCoefficient(Float lastYearHeightAddCoefficient) {
        this.lastYearHeightAddCoefficient = lastYearHeightAddCoefficient;
    }

    public Float getLastYearRBoneAddCoefficient() {
        return lastYearRBoneAddCoefficient;
    }

    public void setLastYearRBoneAddCoefficient(Float lastYearRBoneAddCoefficient) {
        this.lastYearRBoneAddCoefficient = lastYearRBoneAddCoefficient;
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