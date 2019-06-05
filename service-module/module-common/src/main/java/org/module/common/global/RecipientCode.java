package org.module.common.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月23日
 * @Version 
 * @Description 接收人code
 */
public interface RecipientCode {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月23日
	 * @Version OrganizationStatusCode
	 * @Description 类型
	 */
	public enum Type {
		/**
		 * @Fields <font color="blue">SEND</font>
		 * @description 1：发送
		 */
		SEND(1),
		/**
		 * @Fields <font color="blue">RECHARGE</font>
		 * @description 2：充值
		 */
		RECHARGE(2);

		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private Type(int value) {
			this.value = value;
		}
	}
	
}
