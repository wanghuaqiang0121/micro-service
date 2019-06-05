package org.module.user.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月23日
 * @Version 
 * @Description WechatApi
 */
public interface IWechatApi {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationStatusCode
	 * @Description 模板类型
	 */
	public enum Type {
		/**
		 * @Fields <font color="blue">SEND</font>
		 * @description 模板类型1
		 */
		ONE("微信推送"),
		/**
		 * @Fields <font color="blue">RECHARGE</font>
		 * @description 模板类型2
		 */
		TWO("微信推送");

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		private Type(String value) {
			this.value = value;
		}
	}
	
}
