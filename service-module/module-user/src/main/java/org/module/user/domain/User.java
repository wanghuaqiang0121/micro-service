package org.module.user.domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User extends BaseEntity {

	
	private static final long serialVersionUID = 5838298243902581206L;

	public interface Change {

	}
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月27日
	 * @Version User
	 * @Description 注册并绑定用户
	 */
	public interface register {

	}
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月27日
	 * @Version User
	 * @Description 添加家庭组成员
	 */
	public interface insertMember {

	}
	public interface BindWechat {
	}
	public interface WechatLogin {
	}

	private Integer id;
	@NotNull(message = "{user.userGroupId.notnull.valid}", groups = { SelectOne.class })
	private Integer userGroupId;
	private Integer source;
	private Integer terminalSource;
	
	@NotBlank(message = "{user.name.notblank.valid}", groups = { insertMember.class })
	@Length(min = 0, max = 16, message = "{user.name.length}", groups = { insertMember.class })
	private String name;
	@NotNull(message = "{user.sex.notnull.valid}", groups = { insertMember.class })
	private Integer sex;
	// @NotBlank(message = "{user.idCard.not.blank}", groups = { Insert.class })
	private String idCard;
	@NotNull(message = "{user.birthday.notnull.valid}", groups = { insertMember.class })
	@Past(message = "{user.birthday.past.valid}", groups = { insertMember.class })
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	@NotBlank(message = "{user.phone.not.blank}", groups = { Insert.class,register.class })
	private String phone;
	private Integer phoneStatus;
	private String photo;
	@NotBlank(message = "{user.province.notblank.valid}", groups = { insertMember.class})
	private String province;
	@NotBlank(message = "{user.city.notblank.valid}", groups = { insertMember.class})
	private String city;
	@NotBlank(message = "{user.area.notblank.valid}", groups = { insertMember.class})
	private String area;
	@NotBlank(message = "{user.street.notblank.valid}", groups = { insertMember.class})
	private String street;
	
	private Double lng;
	private Double lat;
	//@NotBlank(message = "{user.homeAddress.notblank.valid}", groups = { insertMember.class})
	private String address;
	private int status;
	private Boolean isBindWechat;
	private Date createDate;
	private Date updateDate;
	private String remark;
	@NotBlank(message = "{user.contacts.relation.no.tblank.valid}", groups = { insertMember.class})
	private String relation;
	
	private String relationName;
	@NotBlank(message = "{user.validCode.notblank.valid}", groups = { register.class})
	private String validCode;
	@Valid
	@NotNull(message = "{user.wechat.info.not.null}", groups = { BindWechat.class, WechatLogin.class,register.class})
	private UserWechat wechat;
	
	// 设备：1 家庭医生 2 儿童身高
	@NotNull(message = "{user.wechat.equipment.not.null}", groups = { WechatLogin.class,Change.class})
	private Integer equipment;
	
	@Valid 
	@NotNull(message = "{user.certificate.notnull.valid}", groups = { insertMember.class })
	private UserCertificate userCertificate;
	
	public Integer getEquipment() {
		return equipment;
	}

	public void setEquipment(Integer equipment) {
		this.equipment = equipment;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public Integer getId() {
		return id;
	}

	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getTerminalSource() {
		return terminalSource;
	}

	public void setTerminalSource(Integer terminalSource) {
		this.terminalSource = terminalSource;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getPhoneStatus() {
		return phoneStatus;
	}

	public void setPhoneStatus(Integer phoneStatus) {
		this.phoneStatus = phoneStatus;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Boolean getIsBindWechat() {
		return isBindWechat;
	}

	public void setIsBindWechat(Boolean isBindWechat) {
		this.isBindWechat = isBindWechat;
	}

	public UserWechat getWechat() {
		return wechat;
	}

	public void setWechat(UserWechat wechat) {
		this.wechat = wechat;
	}

	public UserCertificate getUserCertificate() {
		return userCertificate;
	}

	public void setUserCertificate(UserCertificate userCertificate) {
		this.userCertificate = userCertificate;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
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
