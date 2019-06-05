package org.module.bone.age.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年5月30日
 * @Version
 * @Description 骨龄检测工单
 */
public class BoneAgeOrder extends BaseEntity {
	/**
	 * @author <font color="red"><b>Zhang.Xiang.Zheng</b></font>
	 * @date 2018年5月31日
	 * @version 1.0
	 * @description 返回用户骨龄分值曲线
	 */
	public interface getUserScore {

	};

	/**
	 * @author <font color="red"><b>Zhang.Xiang.Zheng</b></font>
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description 审核提交工单
	 */
	public interface updateCheckWorkOrder {

	}

	/**
	 * @author <font color="red"><b>Zhang.Xiang.Zheng</b></font>
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description TW2法R骨测试结果
	 */
	public interface getTW2RBoneTestResult {

	}

	/**
	 * @author <font color="red"><b>Zhang.Xiang.Zheng</b></font>
	 * @date 2018年6月1日
	 * @version 1.0
	 * @description TW2法T骨测试结果
	 */
	public interface getTW2TBoneTestResult {

	}

	@NotNull(message = "{bone.age.order.id.notnull.valid}", groups = { getTW2RBoneTestResult.class,
			getTW2TBoneTestResult.class, getUserScore.class })
	private Integer id;
	@NotNull(message = "{bone.age.order.group.user.id.notnull.valid}", groups = { Insert.class })
	private Integer groupUserId;
	@NotNull(message = "{bone.age.order.user.id.notnull.valid}", groups = { Insert.class })
	private Integer userId;
	// @NotNull(message = "{bone.age.order.organization.team.id.notnull.valid}",
	// groups = { Insert.class})
	private Integer organizationTeamId;
	// @NotNull(message = "{bone.age.order.organization.user.id.notnull.valid}",
	// groups = { Insert.class})
	private Integer organizationUserId;
	private Integer status;
	@NotNull(message = "{bone.age.order.height.notnull.valid}", groups = { Insert.class })
	private Float height;
	@NotNull(message = "{bone.age.order.weight.notnull.valid}", groups = { Insert.class })
	private Float weight;
	@NotBlank(message = "{bone.age.order.xRay.notblank.valid}", groups = { Insert.class })
	private String xRay;
	@NotBlank(message = "{bone.age.order.xRayNo.notblank.valid}", groups = { Insert.class })
	private String xRayNo;
	@NotNull(message = "{bone.age.order.algorithm.notblank.valid}", groups = { Insert.class })
	private Integer algorithm;
	private String radiusLevel;
	private String ulnaLevel;
	private String metacarpal1Level;
	private String metacarpal3Level;
	private String metacarpal5Level;
	private String nearPhalanges1Level;
	private String nearPhalanges3Level;
	private String nearPhalanges5Level;
	private String inPhalanges3Level;
	private String inPhalanges5Level;
	private String farPhalanges1Level;
	private String farPhalanges3Level;
	private String farPhalanges5Level;
	private Float boneAge;
	private Float forecastHeight;
	private Float doctorForecastHeight;
	private Float minForecastHeight;
	private Float doctorMinForecastHeight;
	private Float maxForecastHeight;
	private Float doctorMaxForecastHeight;
	private String result;
	private String advise;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	private Float menarcheAge;
	private String menarcheType;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startCreateDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endCreateDate;

	private Integer sex;
	private BoneAgeOrderLog boneAgeOrderLog;

	private Boolean isBindWechat;
	private String filed;

	private String headBoneLevel;
	private String hamateLevel;
	private String pyramidalBoneLevel;
	private String moonBoneLevel;
	private String scaphoidLevel;
	private String mostOfTheHornsLevel;
	private String smallPolyhornsLevel;

	private Boolean isTW2T;
	private String scoreTableType;

	public String getScoreTableType() {
		return scoreTableType;
	}

	public void setScoreTableType(String scoreTableType) {
		this.scoreTableType = scoreTableType;
	}

	public Boolean getIsTW2T() {
		return isTW2T;
	}

	public void setIsTW2T(Boolean isTW2T) {
		this.isTW2T = isTW2T;
	}

	public String getMenarcheType() {
		return menarcheType;
	}

	public void setMenarcheType(String menarcheType) {
		this.menarcheType = menarcheType;
	}

	public Float getMenarcheAge() {
		return menarcheAge;
	}

	public void setMenarcheAge(Float menarcheAge) {
		this.menarcheAge = menarcheAge;
	}

	public Date getStartCreateDate() {
		return startCreateDate;
	}

	public void setStartCreateDate(Date startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(Integer groupUserId) {
		this.groupUserId = groupUserId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrganizationTeamId() {
		return organizationTeamId;
	}

	public void setOrganizationTeamId(Integer organizationTeamId) {
		this.organizationTeamId = organizationTeamId;
	}

	public Integer getOrganizationUserId() {
		return organizationUserId;
	}

	public void setOrganizationUserId(Integer organizationUserId) {
		this.organizationUserId = organizationUserId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getxRay() {
		return xRay;
	}

	public void setxRay(String xRay) {
		this.xRay = xRay;
	}

	public String getxRayNo() {
		return xRayNo;
	}

	public void setxRayNo(String xRayNo) {
		this.xRayNo = xRayNo;
	}

	public String getRadiusLevel() {
		return radiusLevel;
	}

	public void setRadiusLevel(String radiusLevel) {
		this.radiusLevel = radiusLevel;
	}

	public String getUlnaLevel() {
		return ulnaLevel;
	}

	public void setUlnaLevel(String ulnaLevel) {
		this.ulnaLevel = ulnaLevel;
	}

	public String getMetacarpal1Level() {
		return metacarpal1Level;
	}

	public void setMetacarpal1Level(String metacarpal1Level) {
		this.metacarpal1Level = metacarpal1Level;
	}

	public String getMetacarpal3Level() {
		return metacarpal3Level;
	}

	public void setMetacarpal3Level(String metacarpal3Level) {
		this.metacarpal3Level = metacarpal3Level;
	}

	public String getMetacarpal5Level() {
		return metacarpal5Level;
	}

	public void setMetacarpal5Level(String metacarpal5Level) {
		this.metacarpal5Level = metacarpal5Level;
	}

	public String getNearPhalanges1Level() {
		return nearPhalanges1Level;
	}

	public void setNearPhalanges1Level(String nearPhalanges1Level) {
		this.nearPhalanges1Level = nearPhalanges1Level;
	}

	public String getNearPhalanges3Level() {
		return nearPhalanges3Level;
	}

	public void setNearPhalanges3Level(String nearPhalanges3Level) {
		this.nearPhalanges3Level = nearPhalanges3Level;
	}

	public String getNearPhalanges5Level() {
		return nearPhalanges5Level;
	}

	public void setNearPhalanges5Level(String nearPhalanges5Level) {
		this.nearPhalanges5Level = nearPhalanges5Level;
	}

	public String getInPhalanges3Level() {
		return inPhalanges3Level;
	}

	public void setInPhalanges3Level(String inPhalanges3Level) {
		this.inPhalanges3Level = inPhalanges3Level;
	}

	public String getInPhalanges5Level() {
		return inPhalanges5Level;
	}

	public void setInPhalanges5Level(String inPhalanges5Level) {
		this.inPhalanges5Level = inPhalanges5Level;
	}

	public String getFarPhalanges1Level() {
		return farPhalanges1Level;
	}

	public void setFarPhalanges1Level(String farPhalanges1Level) {
		this.farPhalanges1Level = farPhalanges1Level;
	}

	public String getFarPhalanges3Level() {
		return farPhalanges3Level;
	}

	public void setFarPhalanges3Level(String farPhalanges3Level) {
		this.farPhalanges3Level = farPhalanges3Level;
	}

	public String getFarPhalanges5Level() {
		return farPhalanges5Level;
	}

	public void setFarPhalanges5Level(String farPhalanges5Level) {
		this.farPhalanges5Level = farPhalanges5Level;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAdvise() {
		return advise;
	}

	public void setAdvise(String advise) {
		this.advise = advise;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BoneAgeOrderLog getBoneAgeOrderLog() {
		return boneAgeOrderLog;
	}

	public void setBoneAgeOrderLog(BoneAgeOrderLog boneAgeOrderLog) {
		this.boneAgeOrderLog = boneAgeOrderLog;
	}

	public Boolean getIsBindWechat() {
		return isBindWechat;
	}

	public void setIsBindWechat(Boolean isBindWechat) {
		this.isBindWechat = isBindWechat;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
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

	public Float getForecastHeight() {
		return forecastHeight;
	}

	public void setForecastHeight(Float forecastHeight) {
		this.forecastHeight = forecastHeight;
	}

	public Float getDoctorForecastHeight() {
		return doctorForecastHeight;
	}

	public void setDoctorForecastHeight(Float doctorForecastHeight) {
		this.doctorForecastHeight = doctorForecastHeight;
	}

	public Float getMinForecastHeight() {
		return minForecastHeight;
	}

	public void setMinForecastHeight(Float minForecastHeight) {
		this.minForecastHeight = minForecastHeight;
	}

	public Float getDoctorMinForecastHeight() {
		return doctorMinForecastHeight;
	}

	public void setDoctorMinForecastHeight(Float doctorMinForecastHeight) {
		this.doctorMinForecastHeight = doctorMinForecastHeight;
	}

	public Float getMaxForecastHeight() {
		return maxForecastHeight;
	}

	public void setMaxForecastHeight(Float maxForecastHeight) {
		this.maxForecastHeight = maxForecastHeight;
	}

	public Float getDoctorMaxForecastHeight() {
		return doctorMaxForecastHeight;
	}

	public void setDoctorMaxForecastHeight(Float doctorMaxForecastHeight) {
		this.doctorMaxForecastHeight = doctorMaxForecastHeight;
	}

	public String getHeadBoneLevel() {
		return headBoneLevel;
	}

	public void setHeadBoneLevel(String headBoneLevel) {
		this.headBoneLevel = headBoneLevel;
	}

	public String getHamateLevel() {
		return hamateLevel;
	}

	public void setHamateLevel(String hamateLevel) {
		this.hamateLevel = hamateLevel;
	}

	public String getPyramidalBoneLevel() {
		return pyramidalBoneLevel;
	}

	public void setPyramidalBoneLevel(String pyramidalBoneLevel) {
		this.pyramidalBoneLevel = pyramidalBoneLevel;
	}

	public String getMoonBoneLevel() {
		return moonBoneLevel;
	}

	public void setMoonBoneLevel(String moonBoneLevel) {
		this.moonBoneLevel = moonBoneLevel;
	}

	public String getScaphoidLevel() {
		return scaphoidLevel;
	}

	public void setScaphoidLevel(String scaphoidLevel) {
		this.scaphoidLevel = scaphoidLevel;
	}

	public String getMostOfTheHornsLevel() {
		return mostOfTheHornsLevel;
	}

	public void setMostOfTheHornsLevel(String mostOfTheHornsLevel) {
		this.mostOfTheHornsLevel = mostOfTheHornsLevel;
	}

	public String getSmallPolyhornsLevel() {
		return smallPolyhornsLevel;
	}

	public void setSmallPolyhornsLevel(String smallPolyhornsLevel) {
		this.smallPolyhornsLevel = smallPolyhornsLevel;
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
