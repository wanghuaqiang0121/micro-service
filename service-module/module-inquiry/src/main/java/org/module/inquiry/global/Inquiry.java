package org.module.inquiry.global;

public interface Inquiry {

	public enum Status {
		/**
		 *@Fields <font color="blue">TOBEACCEPTED</font>
		 *@description 1：待接受（未回复）
		 */
		TOBEACCEPTED(1),
		
		/**
		 *@Fields <font color="blue">ACCEPT</font>
		 *@description 1：已接受
		 */
		ACCEPT(2),
		/**
		 * @Fields <font color="blue">REPLIES</font>
		 * @description 3：已回复
		 */
		REPLIES(3),
		
		/**
		 *@Fields <font color="blue">INQUIRIES</font>
		 *@description 4：有追问
		 */
		INQUIRIES(4),
		
		/**
		 *@Fields <font color="blue">CLOSED</font>
		 *@description 5：已关闭
		 */
		CLOSED(5),
		/**
		 *@Fields <font color="blue">FINISHED</font>
		 *@description 6：已结束
		 */
		FINISHED(6);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private Status(int value) {
			this.value = value;
		}
	}
	
	public enum Type {
		/**
		 *@Fields <font color="blue">REPLY</font>
		 *@description 1：回复
		 */
		REPLY(1),
		
		/**
		 * @Fields <font color="blue">INQUIRIES</font>
		 * @description 2：追问
		 */
		INQUIRIES(2);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private Type(int value) {
			this.value = value;
		}
	}
	/**
	 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
	 * @Date 2018年10月16日
	 * @Version Inquiry
	 * @Description 追问次数
	 */
	public enum InquiriesNum {
		
		/**
		 *@Fields <font color="blue">TOTAL</font>
		 *@description 2次
		 */
		TOTAL(2);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private InquiriesNum(int value) {
			this.value = value;
		}
	}
	
}
