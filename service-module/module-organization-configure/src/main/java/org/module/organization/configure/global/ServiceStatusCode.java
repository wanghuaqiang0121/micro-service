package org.module.organization.configure.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月22日
 * @Version 
 * @Description 服务状态枚举：服务项目，服务包等
 */
public interface ServiceStatusCode {

	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年3月23日
	 * @Version ServiceStatusCode
	 * @Description
	 */
	public enum OrganizationServicePackage{
		/**
		 * @Fields <font color="blue">SUBMITED</font>
		 * @description 提交/待审核
		 */
		SUBMITED(0),
		/**
		 * @Fields <font color="blue">PASS</font>
		 * @description 审核通过
		 */
		PASS(1),
		
		/**
		 * @Fields <font color="blue">NOTPASS</font>
		 * @description 审核不通过
		 */
		NOTPASS(2),
		
		/**
		 * @Fields <font color="blue">THESHELVES</font>
		 * @description 上架
		 */
		THESHELVES(3),
		
		/**
		 * @Fields <font color="blue">OFFTHESHELF</font>
		 * @description 下架
		 */
		OFFTHESHELF(4),
		
		/**
		 * @Fields <font color="blue">OFFTHESHELF</font>
		 * @description 审核中
		 */
		EXAMINE(5);
		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private OrganizationServicePackage(int value) {
			this.value = value;
		}
	}
}
