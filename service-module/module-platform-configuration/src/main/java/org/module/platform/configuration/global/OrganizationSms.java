package org.module.platform.configuration.global;

public interface OrganizationSms {
	public enum Type {

		/**
		 * @Fields <font color="blue">USE</font>
		 * @description 使用
		 */
		USE(1),
		/**
		 * @Fields <font color="blue">RECHARGE</font>
		 * @description 充值
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
	public enum Status {
		/**
		 * @Fields <font color="blue">RECHARGE</font>
		 * @description 充值
		 */
		RECHARGE(1);
		
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private Status(int value) {
			this.value = value;
		}
	}
}
