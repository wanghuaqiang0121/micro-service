package org.module.organization.configure.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月22日
 * @Version 
 * @Description 团队状态枚举：团队，关联表等
 */
public interface TeamStatusCode {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationStatusCode
	 * @Description 机构团队
	 */
	public enum OrganizationTeam{
		
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

		private OrganizationTeam(int value) {
			this.value = value;
		}
	}
}
