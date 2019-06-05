package org.module.platform.configuration.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月22日
 * @Version
 * @Description 机构用户状态枚举：机构用户
 */
public interface OrganizationUserStatusCode {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationUserStatusCod
	 * @Description 机构用户
	 */
	public enum OrganizationUser {

		/**
		 * @Fields <font color="blue">ENABLE</font>
		 * @description 启用
		 */
		ENABLE(0),
		/**
		 * @Fields <font color="blue">DISABLE</font>
		 * @description 禁用
		 */
		DISABLE(1),
		/*手机号状态认证*/
		PHONESTATUSAUTH(1),
		/*手机号状态未认证*/
		PHONESTATUSUNAUTH(0);
		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private OrganizationUser(int value) {
			this.value = value;
		}
	}
}
