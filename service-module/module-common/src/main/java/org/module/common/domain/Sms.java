package org.module.common.domain;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.service.core.entity.BaseEntity.Insert;

public class Sms implements Serializable{

	private static final long serialVersionUID = -87802149931857823L;
	@NotBlank(message = "{sms.sign.not.blank}", groups = { Insert.class })
	
	private String sign;
	@NotBlank(message = "{sms.content.not.blank}", groups = { Insert.class })
	private String content;
	/**
	 * @type: {@link Recipient[]}
	 * @author: LiuGangQiang
	 * @date: 2018年9月19日
	 * @description: 接受者列表
	 */
	@NotNull(message = "{sms.recipients.not.null}", groups = { Insert.class })
	@Valid
	private List<Recipient> recipients;
	/**
	 * @type: {@link Long}
	 * @author: LiuGangQiang
	 * @date: 2018年9月18日
	 * @description: 延迟时间 默认0
	 */
	@NotNull(message = "{sms.time.not.null}", groups = { Insert.class })
	@Min(message = "{sms.time.min}", value = 0, groups = { Insert.class })
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
