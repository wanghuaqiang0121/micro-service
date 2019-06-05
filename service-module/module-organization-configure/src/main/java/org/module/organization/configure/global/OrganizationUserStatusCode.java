package org.module.organization.configure.global;

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
		
		/**
		 * @Fields <font color="blue">PHONESTATUSAUTH</font>
		 * @description 手机号状态认证
		 */
		PHONESTATUSAUTH(1),
		/**
		 * @Fields <font color="blue">PHONESTATUSUNAUTH</font>
		 * @description 手机号状态未认证
		 */
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
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年4月17日
	 * @Version 
	 * @Description 机构用户模块
	 */
	public enum organizationUserModule {
		
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
		
		private organizationUserModule(int value) {
			this.value = value;
		}
		
	}
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年4月18日
	 * @Version OrganizationUserStatusCode
	 * @Description 机构用户角色
	 */
	public enum organizationUserRole {
		
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
		
		private organizationUserRole(int value) {
			this.value = value;
		}
		
	}
}
