package org.module.bone.age.domain;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User extends BaseEntity {
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年6月6日
	 * @Version User
	 * @Description 添加成员
	 */
	public interface addGroup {

	}

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年5月31日
	 * @Version User
	 * @Description 注册新用户
	 */
	public interface registerUser {

	}

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年5月30日
	 * @Version User
	 * @Description 添加成员
	 */
	public interface addMember {

	}

	@NotNull(message = "{user.id.notnull.valid}", groups = { addGroup.class })
	private Integer id;
	
	private Integer source;
	private Integer terminalSource;
	@NotBlank(message = "{user.name.notblank.valid}", groups = { addMember.class, registerUser.class })
	@Length(min = 0, max = 16, message = "{user.name.length}", groups = { addMember.class, registerUser.class })
	private String name;
	@NotNull(message = "{user.sex.notnull.valid}", groups = { addMember.class, registerUser.class })
	private Integer sex;

	private String idCard;
	@NotNull(message = "{user.birthday.notnull.valid}", groups = { addMember.class, registerUser.class })
	@Past(message = "{user.birthday.past.valid}", groups = { addMember.class, registerUser.class })
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	// @NotBlank(message = "{user.phone.not.blank}", groups = {
	// addMember.class,SelectAll.class })
	private String phone;

	private Integer phoneStatus;

	private String photo;

	private String province;

	private String city;

	private String area;

	private String street;

	private Double lng;

	private Double lat;
	@NotBlank(message = "{user.homeAddress.notblank.valid}", groups = { addMember.class, registerUser.class })
	private String address;

	private Float menarcheAge;

	private Integer status;

	private Boolean isBindWechat;

	private Date createDate;

	private Date updateDate;

	private String remark;
	@NotNull(message = "{user.userGroupId.notnull.valid}", groups = { addMember.class, addGroup.class })
	private Integer userGroupId;

	@NotBlank(message = "{user.contacts.relation.notblank.valid}", groups = { addMember.class, registerUser.class,
			addGroup.class })
	private String relation;
	// @Valid
	// @NotNull(message = "{user.certificate.notnull.valid}", groups = {
	// addMember.class,registerUser.class })
	private UserCertificate userCertificate;
	@Valid
	@NotNull(message = "{user.certificate.notnull.valid}", groups = { addMember.class, registerUser.class })
	private List<UserCertificate> userCertificates;

	@NotBlank(message = "{user.parentName.notblank.valid}", groups = { registerUser.class })
	private String parentName;
	@NotNull(message = "{user.parentSex.notblank.valid}", groups = { registerUser.class })
	private Integer parentSex;
	@NotBlank(message = "{user.parentPhone.notblank.valid}", groups = { registerUser.class })
	private String parentPhone;
	@NotNull(message = "{user.parentBirthday.notblank.valid}", groups = { registerUser.class })
	@Past(message = "{user.birthday.past.valid}", groups = { registerUser.class })
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date parentBirthday;

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

	public List<UserCertificate> getUserCertificates() {
		return userCertificates;
	}

	public void setUserCertificates(List<UserCertificate> userCertificates) {
		this.userCertificates = userCertificates;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getParentSex() {
		return parentSex;
	}

	public void setParentSex(Integer parentSex) {
		this.parentSex = parentSex;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public Date getParentBirthday() {
		return parentBirthday;
	}

	public void setParentBirthday(Date parentBirthday) {
		this.parentBirthday = parentBirthday;
	}

	public Float getMenarcheAge() {
		return menarcheAge;
	}

	public void setMenarcheAge(Float menarcheAge) {
		this.menarcheAge = menarcheAge;
	}

	public Integer getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Integer userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public UserCertificate getUserCertificate() {
		return userCertificate;
	}

	public void setUserCertificate(UserCertificate userCertificate) {
		this.userCertificate = userCertificate;
	}

	public Integer getId() {
		return id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsBindWechat() {
		return isBindWechat;
	}

	public void setIsBindWechat(Boolean isBindWechat) {
		this.isBindWechat = isBindWechat;
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