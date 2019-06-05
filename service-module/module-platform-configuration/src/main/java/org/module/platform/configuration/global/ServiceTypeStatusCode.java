package org.module.platform.configuration.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月23日
 * @Version 
 * @Description 服务类型
 */
public interface ServiceTypeStatusCode {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月23日
	 * @Version ServiceTypeStatusCode
	 * @Description 服务类型
	 */
	public enum ServiceType {

		/**
		 * @Fields <font color="blue">ENABLE</font>
		 * @description 启用
		 */
		ENABLE(0),
		/**
		 * @Fields <font color="blue">DISABLE</font>
		 * @description 禁用
		 */
		DISABLE(1);

		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private ServiceType(int value) {
			this.value = value;
		}
	}
}
