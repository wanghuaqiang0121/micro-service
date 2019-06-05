package org.module.work.order.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

public class UserSign extends BaseEntity {
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月27日
	 * @Version UserSign
	 * @Description 线上签约
	 */
	public interface onlineSign {}
	
    private Integer id;
    
    @NotNull(message = "{user.id.not.null.valid}", groups = { onlineSign.class})
    private Integer userId;
    @NotNull(message = "{user.sign.organization.team.id.not.null}", groups = { onlineSign.class})
    private Integer organizationTeamId;

    private Integer organizationUserId;

    private String signNo;

    private Integer intentionServicePackageId;

    private Integer userServicePackageOrderId;

    private Integer launchOrganizationUserId;

    private Integer status;
    //@NotBlank(message = "{user.sign.signature.not.null}", groups = { onlineSign.class})
    private String signature;

    private String explain;

    private Integer launchType;

    private String protocol;

    private String closeReasonCode;

    private String closeReason;

    private String notice;

    private Date updateDate;

    private Date createDate;

    private String remark;
    
    private List<UserUserType> userUserType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public Integer getIntentionServicePackageId() {
        return intentionServicePackageId;
    }

    public void setIntentionServicePackageId(Integer intentionServicePackageId) {
        this.intentionServicePackageId = intentionServicePackageId;
    }

    public Integer getUserServicePackageOrderId() {
        return userServicePackageOrderId;
    }

    public void setUserServicePackageOrderId(Integer userServicePackageOrderId) {
        this.userServicePackageOrderId = userServicePackageOrderId;
    }

    public Integer getLaunchOrganizationUserId() {
        return launchOrganizationUserId;
    }

    public void setLaunchOrganizationUserId(Integer launchOrganizationUserId) {
        this.launchOrganizationUserId = launchOrganizationUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Integer getLaunchType() {
        return launchType;
    }

    public void setLaunchType(Integer launchType) {
        this.launchType = launchType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCloseReasonCode() {
        return closeReasonCode;
    }

    public void setCloseReasonCode(String closeReasonCode) {
        this.closeReasonCode = closeReasonCode;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public List<UserUserType> getUserUserType() {
		return userUserType;
	}

	public void setUserUserType(List<UserUserType> userUserType) {
		this.userUserType = userUserType;
	}

	
    
}