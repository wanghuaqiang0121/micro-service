package org.module.bespeak.global;

public interface TeamAppointmentConfigure {

	public enum Status {
		/**
		 *@Fields <font color="blue">ENABLE</font>
		 *@description 启用
		 */
		ENABLE(1),
		
		/**
		 * @Fields <font color="blue">OffLine</font>
		 * @description 禁用
		 */
		DISABLE(2);
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
	/**
	 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
	 * @Date 2018年10月15日
	 * @Version TeamAppointmentConfigure
	 * @Description 周期天数
	 */
	public enum CycleNum {
		/**
		 *@Fields <font color="blue">ENABLE</font>
		 *@description 7天
		 */
		WEEK(7);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private CycleNum(int value) {
			this.value = value;
		}
	}
	public enum Cycle {
		/**
		 *@Fields <font color="blue">EVERYDAY</font>
		 *@description 每天
		 */
		EVERYDAY(1),
		
 		/**
 		 *@Fields <font color="blue">CUSTOM</font>
 		 *@description 自定义
 		 */
 		CUSTOM(2);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private Cycle(int value) {
			this.value = value;
		}
	}
}
