package org.module.work.order.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月22日
 * @Version 
 * @Description 
 */
public interface SignStatusCode {

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationStatusCode
	 * @Description 签约
	 */
	public enum Sign{
		
		          
        /**
         *@Fields <font color="blue">WAITRESPONSE</font>
         *@description 待响应
         */
        WAITRESPONSE(0),
                  
        /**
         *@Fields <font color="blue">EXECUTIONED</font>
         *@description 已执行
         */
        EXECUTIONED(1),
                   
        /**
         *@Fields <font color="blue">REFUSED</font>
         *@description 已拒绝
         */
        REFUSED(2),
        
		/**
		 *@Fields <font color="blue">LAUNCHTTYPEONLINE</font>
		 *@description 线下
		 */
		LAUNCHTTYPEONLINE(1),
		
		/**
		 *@Fields <font color="blue">LAUNCHTTYPELINE</font>
		 *@description 线上
		 */
		LAUNCHTTYPELINE(2),
		
		/**
		 *@Fields <font color="blue">SIGNLOGTYPEOPERATIONLOG</font>
		 *@description 0：操作日志
		 */
		SIGNLOGTYPEOPERATIONLOG(0),
		
		/**
		 *@Fields <font color="blue">SIGNLOGTYPESYNERGETICLOG</font>
		 *@description 1：协同日志
		 */
		SIGNLOGTYPESYNERGETICLOG(1);
		
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private Sign(int value) {
			this.value = value;
		}
	}
	public enum WorkOrderStatus{
		
		
		/**
		 *@Fields <font color="blue">WAITRESPONSE</font>
		 *@description 待响应:1
		 */
		PENDINGRESPONSE(1),
		
		/**
		 *@Fields <font color="blue">EXECUTIONED</font>
		 *@description 待执行:2
		 */
		TOBEEXECUTED(2),
		
		/**
		 *@Fields <font color="blue">REFUSED</font>
		 *@description 已关闭:3
		 */
		CLOSED(3),
		
		/**
		 *@Fields <font color="blue">LAUNCHTTYPEONLINE</font>
		 *@description 执行中:4
		 */
	
		INTHEEXECUTION(4),
		
		/**
		 *@Fields <font color="blue">LAUNCHTTYPELINE</font>
		 *@description 已执行（待评价）:5
		 */
		EXECUTED(5),
		
		/**
		 *@Fields <font color="blue">SIGNLOGTYPEOPERATIONLOG</font>
		 *@description 已评价:6
		 */
		EVALUATED(6),
		
		/**
		 *@Fields <font color="blue">ORDERRESOURCEONLINE</font>
		 *@description 工单来源 1：在线-微信
		 */
		ORDERRESOURCEONLINEWECHAT(1),
		
		/**
		 *@Fields <font color="blue">ORDERRESOURCEONLINE</font>
		 *@description 工单来源 2：在线-itv
		 */
		ORDERRESOURCEONLINEITV(2),
		
		/**
		 *@Fields <font color="blue">ORDERRESOURCESCENE</font>
		 *@description  工单来源 3：现场
		 */
		ORDERRESOURCESCENE(3),
		
		/**
		 *@Fields <font color="blue">ORDERRESOURCEPLAN</font>
		 *@description  工单来源 4：计划
		 */
		ORDERRESOURCEPLAN(4),
		
		
		/**
		 *@Fields <font color="blue">SIGNLOGTYPEOPERATIONLOG</font>
		 *@description 0：操作日志
		 */
		WORKORDERLOGTYPEOPERATIONLOG(0),
		
		/**
		 *@Fields <font color="blue">SIGNLOGTYPESYNERGETICLOG</font>
		 *@description 1：协同日志
		 */
		WORKORDERLOGTYPESYNERGETICLOG(1),
		
		/**
		 *@Fields <font color="blue">SIGNLOGTYPESYNERGETICLOG</font>
		 *@description 0：医生发起
		 */
		WORKORDERLOGTYPEDOCTORSPONSOR(0),
		
		/**
		 *@Fields <font color="blue">SIGNLOGTYPESYNERGETICLOG</font>
		 *@description 1：用户发起
		 */
		WORKORDERLOGTYPEUSERSPONSOR(1);
		
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private WorkOrderStatus(int value) {
			this.value = value;
		}
	}
}
