package org.module.bone.age.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年6月4日
 * @Version 
 * @Description 中英身高百分位转换参考表
 */
public interface HeightPercentileConvertTableStatusCode {

	
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年6月4日
	 * @Version HeightPercentileConvertTableStatusCode
	 * @Description 中英身高百分位转换参考表
	 */
	public enum HeightPercentileConvertTable {
		
		/**
		 *@Fields <font color="blue">BE_EQUAL_TO</font>
		 *@description 是否有对应的值
		 */
		BE_EQUAL_TO(1),
		/**
		 *@Fields <font color="blue">BOTH_SIDES</font>
		 *@description 查询距离最近的两条记录
		 */
		BOTH_SIDES(2);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private HeightPercentileConvertTable(int value) {
			this.value = value;
		}
	}
}
