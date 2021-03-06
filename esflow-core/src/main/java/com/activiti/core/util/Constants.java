package com.activiti.core.util;

public class Constants {
	
	/**
	 * 权限前缀标识
	 */
	public final static String BIZ_TASKASSIGNEE = "GROUP:";
	
	/**
	 * 签收标识
	 */
	public final static String SIGN = "SIGN";

	/**
	 * 处理标识
	 */
	public final static String HANDLE = "HANDLE";

	
	//============== 流程参数相关 =============
	/**
	 * 工单创建人
	 */
	public final static String SYS_BIZ_CREATEUSER = "SYS_BIZ_CREATEUSER";
	/**
	 * 工单ID
	 */
	public final static String SYS_BIZ_ID = "SYS_BIZ_ID";
	
	//=====================工单状态===========================
	
	//草稿
	public final static String BIZ_TEMP = "草稿";
	//新建
	public final static String BIZ_NEW = "新建";
	
	//结束
	public final static String BIZ_END = "已完成";
	
	//删除
	public final static String BIZ_DELETE = "已删除";

	//=====================会签结果类型===========================

	//通过
	public final static int MEET_YES = 1;
	//驳回
	public final static int MEET_NO = 0;
}
