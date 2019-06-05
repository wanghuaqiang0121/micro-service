package org.module.platform.configuration.global;

public interface OrganizationUserCertificateCode {
	
	public enum CertificateTypeCode {

		
		/**
		 *@Fields <font color="blue">IDCARD</font>
		 *@description 身份证
		 */
		IDCARD(1);

		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private CertificateTypeCode(int value) {
			this.value = value;
		}
	}
}
