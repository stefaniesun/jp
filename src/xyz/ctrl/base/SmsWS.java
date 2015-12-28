package xyz.ctrl.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;
import xyz.svc.core.SmsSvc;
import xyz.util.Constant;

@Controller
@RequestMapping(value="/SmsWS")
public class SmsWS{
	
	@Autowired
	private SmsSvc smsSvc;
	
	/**
	 * 应用--作业--短信管理--查询已存在的短信
	 */
	@RequestMapping(value="querySmsList")
	@ResponseBody
	public Map<String, Object> querySmsList(
			int page,
			int rows,
			String clientCode,
			String phone,
			String content,
			String username,
			String status,
			Date dateStart,
			Date dateEnd){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return smsSvc.querySmsList(offset, pagesize,clientCode, phone,content, username, status, dateStart, dateEnd);
	}
	
	/**
	 * 直接发送短信
	 */
	@RequestMapping(value="addSms")
	@ResponseBody
	public Map<String, Object> addSms(
			String phone, 
			String content){
		return smsSvc.addSms(phone, content);
	}
	
	/**
	 * 应用--作业--短信管理--修改短信状态
	 */
	@RequestMapping(value="updateSmsStatus")
	@ResponseBody
	public Map<String, Object> updateSmsStatus(String numberCode){
		return smsSvc.updateSmsStatus(numberCode);
	}
	
	/**
	 * 应用--作业--短信管理--查询短信剩余条数
	 */
	@RequestMapping(value="querySmsCount")
	@ResponseBody
	public Map<String, Object> querySmsCount(){
		Map<String, String> accessoryParam = new HashMap<String, String>();
		accessoryParam.put("business", "ABC");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>)new RmiUtil().loadData(Constant.smsUrl_smsCount, accessoryParam);
		if(result==null){
			return ReturnUtil.returnMap(0,"请稍后再试！");
		}
		Integer status = (Integer)result.get(Constant.result_status);
		if(status==null){
			return ReturnUtil.returnMap(0,"请稍后再试！");
		}
		return result;
	}
}
