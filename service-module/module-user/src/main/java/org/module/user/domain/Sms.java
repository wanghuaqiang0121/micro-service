package org.module.user.domain;

import java.io.Serializable;
import java.util.List;

public class Sms implements Serializable{

	private static final long serialVersionUID = -87802149931857823L;
	private String sign;
	private String content;
	/**
	 * @type: {@link Recipient[]}
	 * @author: LiuGangQiang
	 * @date: 2018年9月19日
	 * @description: 接受者列表
	 */
	private List<Recipient> recipients;
	/**
	 * @type: {@link Long}
	 * @author: LiuGangQiang
	 * @date: 2018年9月18日
	 * @description: 延迟时间 默认0
	 */
	private Integer time;

	private String status;
	private String message;
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<Recipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}

	public Integer getTime() {
		return time;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
