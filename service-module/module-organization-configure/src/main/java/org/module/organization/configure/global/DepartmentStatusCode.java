package org.module.organization.configure.global;

public interface DepartmentStatusCode {

/**
 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
 * @Date 2018年8月24日
 * @Version DepartmentStatusCode
 * @Description 医疗科室
 */
public enum Department{
		
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

		private Department(int value) {
			this.value = value;
		}
	}
}
