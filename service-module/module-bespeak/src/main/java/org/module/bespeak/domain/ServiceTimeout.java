package org.module.bespeak.domain;

import java.io.Serializable;

import org.module.bespeak.global.ServiceType;


public class ServiceTimeout implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private ServiceType type;
	private Long time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}


}
