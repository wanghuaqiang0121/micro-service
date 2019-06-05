package org.module.organization.configure.global;


/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月22日
 * @Version 
 * @Description 机构类枚举：机构表，机构关联表等
 */
public interface OrganizationStatusCode {

	
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationStatusS
	 * @Description 机构类型
	 */
	public enum OrganizationType implements OrganizationStatusCode{
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
		 * @Fields <font color="blue">ORGANIZATION_TYPE_PUBLIC</font>
		 * @description 公立
		 */
		ORGANIZATION_TYPE_PUBLIC(0),

		/**
		 * @Fields <font color="blue">ORGANIZATION_TYPE_PRIVATE</font>
		 * @description 私立
		 */
		ORGANIZATION_TYPE_PRIVATE(1);
		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private OrganizationType(int value) {
			this.value = value;
		}
		
	}

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationStatusS
	 * @Description 推荐机构
	 */
	public enum RecommendOrganization {
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
		 *@Fields <font color="blue">RECOMMEND_ORGANIZATION_MOVE_UP</font>
		 *@description 推荐机构调整顺序，上移：2
		 */
		RECOMMEND_ORGANIZATION_MOVE_UP(2),
		/**
		 *@Fields <font color="blue">RECOMMEND_ORGANIZATION_MOVE_DOWN</font>
		 *@description 推荐机构调整顺序，下移：3
		 */
		RECOMMEND_ORGANIZATION_MOVE_DOWN(3);
		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private RecommendOrganization(int value) {
			this.value = value;
		}
		
	}
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年4月17日
	 * @Version OrganizationStatusCode
	 * @Description 服务包
	 */
	public enum OrganizationPackage {
		/**
		 * @Fields <font color="blue">YEAR</font>
		 * @description 1：按年
		 */
		YEAR(1),
		/**
		 * @Fields <font color="blue">YEAROFNATURE</font>
		 * @description 2：按自然年
		 */
		YEAROFNATURE(2),
		/**
		 * @Fields <font color="blue">MONTH</font>
		 * @description 3：按月
		 */
		MONTH(3),
		/**
		 * @Fields <font color="blue">MONTHOFNATURE</font>
		 * @description 4：按自然月
		 */
		MONTHOFNATURE(4);
		
		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private OrganizationPackage(int value) {
			this.value = value;
		}
		
	}
	
	
}
