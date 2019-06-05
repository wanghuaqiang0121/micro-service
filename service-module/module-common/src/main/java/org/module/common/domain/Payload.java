package org.module.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Payload implements Serializable {
	private static final long serialVersionUID = 4554167408431292182L;
	private String number;
	private Date time;
	private Map<String, String> param;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

}
