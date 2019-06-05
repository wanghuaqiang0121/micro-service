package org.module.bone.age.global;

/**
 * @author <font color="red"><b>Gong.YiYang</b></font>
 * @Date 2018年3月22日
 * @Version
 * @Description 用户
 */
public interface UserStatusCode {

	/**
	 * @author <font color="green"><b>Gong.YiYang</b></font>
	 * @param channel
	 *            下单渠道
	 * @param pay
	 *            支付渠道
	 * @param business
	 *            业务类型
	 * @param userId
	 *            用户编号
	 * @date 2018年3月28日
	 * @version 1.0
	 * @description 生成订单编号
	 */
	String GetOrderNumber(int channel, int pay, int business, int userId);

	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationStatusS
	 * @Description 机构类型
	 */
	public enum Sign {
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

	/**
	 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
	 * @Date 2018年9月7日
	 * @Version UserStatusCode 功能来源
	 * @Description
	 */
	public enum Source {
		
		/**
		 *@Fields <font color="blue">SIGNBYJGW</font>
		 *@description 1基公卫签约
		 */
		SIGNBYJGW(1),
		
		/**
		 *@Fields <font color="blue">BUYSERVICEPACKAGE</font>
		 *@description 2增值服务包购买
		 */
		BUYSERVICEPACKAGE(2),
		
		/**
		 *@Fields <font color="blue">SINGLEDIAGNOSISANDTREATMENT</font>
		 *@description 3单次诊疗
		 */
		SINGLEDIAGNOSISANDTREATMENT(3);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private Source(int value) {
			this.value = value;
		}
	}
	/**
	 * @author <font color="red"><b>Wang.Hua.Qiang</b></font>
	 * @Date 2018年9月7日
	 * @Version UserStatusCode 终端来源
	 * @Description
	 */
	public enum TerminalSource {
		
		/**
		 *@Fields <font color="blue">WECHAT</font>
		 *@description 1 微信
		 */
		WECHAT(1),
		
		/**
		 *@Fields <font color="blue">SCENE</font>
		 *@description 2现场
		 */
		SCENE(2),
		
		/**
		 *@Fields <font color="blue">IPTV</font>
		 *@description 3 IPTV
		 */
		IPTV(3),
		/**
		 *@Fields <font color="blue">APP</font>
		 *@description 4 App
		 */
		APP(4);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private TerminalSource(int value) {
			this.value = value;
		}
	}
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月22日
	 * @Version OrganizationStatusS
	 * @Description 订单
	 */
	public enum UserServicePackageOrder implements UserStatusCode {
		/**
		 * @author <font color="red"><b>Gong.YiYang</b></font>
		 * @Date 2018年3月28日
		 * @Version UserStatusCode.USERSERVICEPACKAGEORDER
		 * @Description 订单编号
		 */
		OrderNumber(3){
			public String GetOrderNumber(int channel, int pay, int business, int userId) {
				return "" + channel + pay + business + System.currentTimeMillis() + userId;

			}
		},
		/*待支付*/
        Waiting(0),

        /*已取消*/
        Cancel(1),

		/* 已支付 */
        Success(2);

		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private UserServicePackageOrder(int value) {
			this.value = value;
		}

		@Override
		public String GetOrderNumber(int channel, int pay, int business, int userId) {
			return "" + channel + pay + business + System.currentTimeMillis() + userId;
		}


	}
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月28日
	 * @Version UserStatusCode
	 * @Description 服务包类型
	 */
	public enum ServicePackageType {
		/**
		 * @Fields <font color="blue">OnLine</font>
		 * @description 基公卫
		 */
		SERVICE_PACKAGE_TYPE_BASE(1),
		/**
		 * @Fields <font color="blue">OffLine</font>
		 * @description 增值包
		 */
		SERVICE_PACKAGE_TYPE_TOLL(2);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private ServicePackageType(int value) {
			this.value = value;
		}
	}
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月28日
	 * @Version UserStatusCode
	 * @Description 用户组
	 */
	public enum UserUserGroup {
		/**
		 * @Fields <font color="blue">OnLine</font>
		 * @description 未托管
		 */
		USER_USRE_GROUP_TRUST_UNTRUSTEESHIP(1),
		/**
		 * @Fields <font color="blue">OffLine</font>
		 * @description 已托管
		 */
		USER_USRE_GROUP_TRUST_TRUSTEESHIP(2);
		private int value;
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		private UserUserGroup(int value) {
			this.value = value;
		}
	}
	
	/**
	 * @author <font color="red"><b>Gong.YiYang</b></font>
	 * @Date 2018年3月28日
	 * @Version UserStatusCode
	 * @Description 用户
	 */
	public enum User {
		/**
		 *@Fields <font color="blue">PHONE_STATUS_IS_VALIDED</font>
		 *@description 手机号码状态：为空
		 */
		PHONE_STATUS_IS_NULL(0),
		/**
		 *@Fields <font color="blue">PHONE_STATUS_IS_VALIDED</font>
		 *@description 手机号码状态：未验证
		 */
		PHONE_STATUS_NOT_VALIDED(1),
		/**
		 *@Fields <font color="blue">PHONE_STATUS_IS_VALIDED</font>
		 *@description 手机号码状态：已验证
		 */
		PHONE_STATUS_IS_VALIDED(2),
		
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
		
		private User(int value) {
			this.value = value;
		}
	}
	/*发起类型*/
	public enum Channel {
		/**
		 * @Fields <font color="blue">OnLineWechat</font>
		 * @description 线上-微信
		 */
		OnLineWechat(1),
		/**
		 * @Fields <font color="blue">OnLineItv</font>
		 * @description 线上-IPTV
		 */
		OnLineItv(2),
		/**
		 * @Fields <font color="blue">OffLine</font>
		 * @description 线下-现场执行
		 */
		OffLine(3);
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private Channel(int value) {
			this.value = value;
		}
	}

	/* 支付方式 */
	public enum Pay {

		/* 微信支付 */
		WeChat(1),

		/* 支付宝 */
		Alipay(2),

		/* 网银支付 */
		CyberBank(3),

		/* 线下支付 */
		OffLine(4);
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private Pay(int value) {
			this.value = value;
		}

	}

	/* 业务类型 */

	public enum Business {
		/* 医总管 */
		yzg(1);
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private Business(int value) {
			this.value = value;
		}
	}

	public enum ItvAccountStatus{
		/**
		 *@Fields <font color="blue">ENABLE</font>
		 *@description 正常
		 */
		ENABLE(0),
		/**
		 *@Fields <font color="blue">ENABLE</font>
		 *@description 异常
		 */
		DISABLE(1);
		private int value;

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		private ItvAccountStatus(int value) {
			this.value = value;
		}
	}
	
	/**
	 * @author <font color="red"><b>Wang.HuaQiang</b></font>
	 * @Date 2018年4月17日
	 * @Version 
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
