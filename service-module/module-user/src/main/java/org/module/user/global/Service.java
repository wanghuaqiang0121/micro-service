package org.module.user.global;

public interface Service {

	public enum ServiceType{
		
		/**
		 *@Fields <font color="blue">VIDEOCONSULTATION</font>
		 *@description 视频咨询
		 */
		VIDEOCONSULTATION("videoConsultation");
		
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		private ServiceType(String value) {
			this.value = value;
		}
	}
	
	public enum BusinessType{
		
		/**
		 *@Fields <font color="blue">SERVICETYPE</font>
		 *@description 服务类
		 */
		SERVICETYPE("service:type"),
		/**
		 *@Fields <font color="blue">APPOINTMENTTYPE</font>
		 *@description 预约类
		 */
		APPOINTMENTTYPE("appointment:type");
		
		private String value;
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		private BusinessType(String value) {
			this.value = value;
		}
	}
}
