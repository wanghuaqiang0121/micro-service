package org.module.platform.configuration.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月23日
 * @Version 
 * @Description 人群类型
 */
public interface UserTypeStatusCode {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月23日
	 * @Version UserTypeStatusCode
	 * @Description 用户标签
	 */
	public enum UserType {

		/**
		 * @Fields <font color="blue">ENABLE</font>
		 * @description 启用
		 */
		ENABLE(true),
		/**
		 * @Fields <font color="blue">DISABLE</font>
		 * @description 禁用
		 */
		DISABLE(false),
		/**
		 *@Fields <font color="blue">currency</font>
		 *@description 通用
		 */
		CURRENCY(0),
		/**
		 *@Fields <font color="blue">PROPER</font>
		 *@description 专用
		 */
		PROPER(1);
		
		private Object value;

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		private UserType(Object value) {
			this.value = value;
		}
	}
}
