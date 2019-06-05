package org.module.bone.age.global;


/**
 * 
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2018年5月30日
 * @Version 
 * @Description 骨龄检测工单状态
 */
public class BoneAgeOrderStatusCode {

	/**
	 * 
	 * @author <font color="red"><b>Chen.Yan</b></font>
	 * @Date 2018年5月30日
	 * @Version BoneAgeOrderStatusCode
	 * @Description 骨龄检测工单状态
	 */
	public enum BoneAgeOrder {
		
		/**
		 * @Fields <font color="blue">BONE_AGE_ORDER_GENERATE</font>
		 * @description 1:生成工单
		 */
		BONE_AGE_ORDER_GENERATE(1),
		
		/**
		 * @Fields <font color="blue">BONE_AGE_ORDER_SAVE</font>
		 * @description 2:保存R骨数据
		 */
		BONE_AGE_ORDER_SAVE(2),
		
		/**
		 * @Fields <font color="blue">BONE_AGE_ORDER_FINISH</font>
		 * @description 3:完成工单
		 */
		BONE_AGE_ORDER_FINISH(3);

		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private BoneAgeOrder(int value) {
			this.value = value;
		}
		
	}
}
