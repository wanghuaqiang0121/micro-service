package org.module.bone.age.domain;

import javax.validation.constraints.NotNull;

import org.service.core.entity.BaseEntity;

/**
* @author <font color="red"><b>Zhang.Xiang.Zheng</b></font> 
* @date 2018年5月31日 
* @version 1.0
* @description 中英身高百分位转换参考表
 */
public class HeightPercentileConvertTable extends BaseEntity {
	private Integer id;

    private Integer sex;

    private Integer age;

    private Float actual;

    private Float convert;

    private String type;
    
    /**
     *@Fields <font color="blue">condition</font>
     *@description 判断是否执行什么sql条件
     */
    private Integer condition;
    
    public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
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

    public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Float getActual() {
		return actual;
	}

	public void setActual(Float actual) {
		this.actual = actual;
	}

	public Float getConvert() {
		return convert;
	}

	public void setConvert(Float convert) {
		this.convert = convert;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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