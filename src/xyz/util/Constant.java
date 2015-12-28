package xyz.util;

import java.util.Date;


/**
 * 常量
 * @author 姚成成
 */
public final class Constant {
	
	//返回值
	public static final String result_status = "status";//
	public static final String result_msg = "msg";//
	public static final String result_content = "content";//
	//session过期时间
	public static final long sessionTimes = 1000*60*45;
	
	public static final String smsUrl_smsSend = "http://"+SysPropertyTool.getValue("smsip")+"/pberpsms/SmsWS/sendSms.do";
	public static final String smsUrl_smsStatus = "http://"+SysPropertyTool.getValue("smsip")+"/pberpsms/SmsWS/querySmsStatus.do";
	public static final String smsUrl_smsCount = "http://"+SysPropertyTool.getValue("smsip")+"/pberpsms/SmsWS/querySmsCount.do";
	
	//增值接口限定
	public static int count_sms = 989;
	public static Date currentDate = DateUtil.shortStringToDate(DateUtil.dateToShortString(new Date()));
	   
	public static boolean decideCountSms(){
		Date date = new Date();
		if(DateUtil.dateToShortString(date).equals(DateUtil.dateToShortString(Constant.currentDate))){
			if(Constant.count_sms>0){
				Constant.count_sms--;
				return true;
			}else{
				return false;
			}
		}else{
			initCurrentDate(date);
			return true;
		}
	}
	
	public static void initCurrentDate(Date date){
		Constant.currentDate = DateUtil.shortStringToDate(DateUtil.dateToShortString(date));
		Constant.count_sms = 998;
	}
}
