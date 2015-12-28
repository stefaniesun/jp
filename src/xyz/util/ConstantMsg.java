package xyz.util;



/**
 * 常量
 * @author 姚成成
 */
public final class ConstantMsg {
	
	public static final String format_date = "数据提交不合格：日期格式错误";
	public static final String format_number = "数据提交不合格：数字格式错误";
	public static final String format_email = "数据提交不合格：邮件格式错误";
	public static final String format_data_null = "数据提交不合格：空值";
	public static final String format_count_error = "数据提交不合格：数量错误";
	
	
	public static final String scope_date = "数据提交不合格：日期范围";
	public static final String scope_current = "数据提交不合格：操作对象已失效";
	public static final String scope_number = "数据提交不合格：数字范围";
	public static final String scope_iidd = "操作了不该操作的对象";
	
	public static final String resource_date = "资源被占用：可能是日期重叠";
	public static final String resource_value = "数据值不合格";
	public static final String resource_status = "对象状态异常";
	public static final String resource_short = "资源短缺";
	
	
	
	
	public static final String login_username = "用户名不存在";
	public static final String login_password= "密码错误";
	public static final String login_enabled= "用户受限";
	public static final String login_otp_no= "用户没有已授权的动态令牌";
	public static final String login_otp_synch_param= "令牌同步参数错误";
	public static final String login_otp_synch_fail= "令牌同步口令错误";
	public static final String login_otp_synch_success= "同步成功，请稍后重新登录";
	public static final String login_otp_fail= "令牌口令错误";
	public static final String login_otp_count= "令牌口令只能使用一次，请一分钟后再试";
	public static final String login_otp_exception= "动态令牌出现异常";
	
	public static final String order_out= "余下数量不足";
	
	public static final String password_old= "旧密码输入错误";
	public static final String password_trade= "交易密码输入错误";
	public static final String balance_out= "余额不足";
	
	public static final String auth_role_error= "您没有相关权限";
	public static final String auth_login= "不存在有效登录信息,请重新登录";
	public static final String auth_login_date= "超过时限，请重新登录";
	public static final String auth_decide_data= "缺乏关键数据";
	
	public static final String exception_file_path= "可能是文件路径问题";
	
	public static final String validateNumberMatch_3= "【除去商户牌照】编号必须是大写字母或者数字组成的3位字符！";
	public static final String validateNumberMatch_6= "【除去商户牌照】编号必须是大写字母或者数字组成的6位字符！";
	public static final String validateBusiness= "商户编号异常！";
	
}
