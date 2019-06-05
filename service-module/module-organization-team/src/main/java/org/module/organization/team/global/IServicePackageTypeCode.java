package org.module.organization.team.global;

public interface IServicePackageTypeCode {
	
	public enum Type{
		
		/**
		 * @Fields <font color="blue">JGW</font>
		 * @description 基本公共卫生服务包
		 */
		JGW("JGW");
		
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		private Type(String value) {
			this.value = value;
		}
	}
}
