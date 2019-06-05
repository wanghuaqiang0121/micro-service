package org.module.organization.configure.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class Referral extends BaseEntity {
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version Referral
	 * @Description 分诊转诊单
	 */
	public interface Triage {

	}

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version Referral
	 * @Description 查询分诊到我机构科室的转诊单
	 */
	public interface referralByDepartment {

	}

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version Referral
	 * @Description 查询转诊到机构的转诊单
	 */
	public interface referralByOrganizationId {

	}

	private Integer id;

	private Integer workOrderId;

	private Integer userId;

	private Integer launchOrganizationTeamId;

	private Integer launchOrganizationUserId;

	private Integer organizationId;

	private Integer departmentId;
	@NotNull(message = "{referral.organization.team.id.not.null}", groups = { Triage.class })
	private Integer organizationTeamId;
	/* 是否需要分诊 */
	private boolean NeedTriage;

	private Integer organizationUserId;

	private String number;

	private String diagnosis;

	private String reason;

	private String objective;

	private String picture;

	private Float evaluateStar;

	private Integer status;

	private Date createDate;

	private String chiefComplaint;

	private String adviceBefore;

	private String adviceAfter;

	private String evaluateText;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Integer workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLaunchOrganizationTeamId() {
		return launchOrganizationTeamId;
	}

	public void setLaunchOrganizationTeamId(Integer launchOrganizationTeamId) {
		this.launchOrganizationTeamId = launchOrganizationTeamId;
	}

	public Integer getLaunchOrganizationUserId() {
		return launchOrganizationUserId;
	}

	public void setLaunchOrganizationUserId(Integer launchOrganizationUserId) {
		this.launchOrganizationUserId = launchOrganizationUserId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public boolean isNeedTriage() {
		return NeedTriage;
	}

	public void setNeedTriage(boolean needTriage) {
		NeedTriage = needTriage;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Float getEvaluateStar() {
		return evaluateStar;
	}

	public void setEvaluateStar(Float evaluateStar) {
		this.evaluateStar = evaluateStar;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getAdviceBefore() {
		return adviceBefore;
	}

	public void setAdviceBefore(String adviceBefore) {
		this.adviceBefore = adviceBefore;
	}

	public String getAdviceAfter() {
		return adviceAfter;
	}

	public void setAdviceAfter(String adviceAfter) {
		this.adviceAfter = adviceAfter;
	}

	public String getEvaluateText() {
		return evaluateText;
	}

	public void setEvaluateText(String evaluateText) {
		this.evaluateText = evaluateText;
	}

	@Override
	@NotNull(message = "{page.empty}", groups = { SelectAll.class, referralByOrganizationId.class,
			referralByDepartment.class })
	public Integer getPage() {
		return super.getPage();
	}

	@Override
	@NotNull(message = "{pageSize.empty}", groups = { SelectAll.class, referralByOrganizationId.class,
			referralByDepartment.class })
	public Integer getPageSize() {
		return super.getPageSize();
	}
}