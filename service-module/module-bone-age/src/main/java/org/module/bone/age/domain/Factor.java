package org.module.bone.age.domain;

/**
 * @author <font color="red"><b>Wang.HuaQiang</b></font>
 * @Date 2018年6月1日
 * @Version 
 * @Description 因素法判断因素类
 */
public class Factor {
	
	private Integer userId;
	// 性别:1男  2女
	private Integer sex;
	// 年龄
	private Float age;
	// 骨龄
	private Double boneAge;
	// 身高
	private Double height;
	// 初潮年龄
	private Double menarcheAge;
	// 初潮类型
	private Integer menarcheType;
	// 身高转换值
	private	Double convertHeight;
	// △H:上1年（0.88-1.12年，或1年前后6周内）的身高增加数 从工单中查询记录，再用现在实际的减去工单的
	private	Double deltaH;
	// 上1年（0.88-1.12年，或1年前后6周内）的R骨龄增加数 从工单中查询记录，再用现在实际的减去工单的
	private	Double deltaR;
	
	public Integer getMenarcheType() {
		return menarcheType;
	}
	public void setMenarcheType(Integer menarcheType) {
		this.menarcheType = menarcheType;
	}
	public Double getDeltaH() {
		return deltaH;
	}
	public void setDeltaH(Double deltaH) {
		this.deltaH = deltaH;
	}
	public Double getDeltaR() {
		return deltaR;
	}
	public void setDeltaR(Double deltaR) {
		this.deltaR = deltaR;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getConvertHeight() {
		return convertHeight;
	}
	public void setConvertHeight(Double convertHeight) {
		this.convertHeight = convertHeight;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	 
	public Float getAge() {
		return age;
	}
	public void setAge(Float age) {
		this.age = age;
	}
	public Double getBoneAge() {
		return boneAge;
	}
	public void setBoneAge(Double boneAge) {
		this.boneAge = boneAge;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getMenarcheAge() {
		return menarcheAge;
	}
	public void setMenarcheAge(Double menarcheAge) {
		this.menarcheAge = menarcheAge;
	}
	
	
}
