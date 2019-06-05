package org.module.bespeak.global;

public interface TeamAppointmentOrder {

	public enum Status {
		/**
		 *@Fields <font color="blue">PENDINGNUMBER</font>
		 *@description 1：待取号
		 */
		PENDINGNUMBER(1),
		
		/**
		 * @Fields <font color="blue">FETCHEDNUMBER</font>
		 * @description 2：已取号
		 */
		FETCHEDNUMBER(2),
		
		/**
		 *@Fields <font color="blue">CANCEL</font>
		 *@description 3：已取消
		 */
		CANCEL(3);
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
