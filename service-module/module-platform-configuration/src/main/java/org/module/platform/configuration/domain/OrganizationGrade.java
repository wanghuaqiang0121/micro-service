package org.module.platform.configuration.domain;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity;

/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年7月19日
 * @Version 
 * @Description 机构等级
 */
public class OrganizationGrade extends BaseEntity {
    private Integer id;
    @NotBlank(message = "{organization.grade.name.not.blank.valid}", groups = {Insert.class})
    private String name;

    private Date createDate;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}