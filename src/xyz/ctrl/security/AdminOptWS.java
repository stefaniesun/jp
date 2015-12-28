package xyz.ctrl.security;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.model.security.TokenInfo;
import xyz.svc.security.AdminOtpSvc;

@Controller
@RequestMapping(value="/AdminOptWS")
public class AdminOptWS{
	
	@Autowired
	private AdminOtpSvc adminOtpSvc;

	/**
	 * 管理--令牌--查询令牌列表
	 */
	@RequestMapping(value="queryOtpList")
	@ResponseBody
	public Map<String, Object> queryOtpList(
			String flag,
			String tokenNum,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return adminOtpSvc.queryOtpList(offset,pagesize,flag,tokenNum);
	}
	
	/**
	 * 管理--令牌--新增令牌
	 */
	@RequestMapping(value="addOtp")
	@ResponseBody
	public Map<String, Object> addOtp( 
			String tokenNum,
			String authkey){
		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setAddDate(new Date());
		tokenInfo.setAlterDate(new Date());
		tokenInfo.setAuthkey(authkey);
		tokenInfo.setCurrentDrift(0);
		tokenInfo.setCurrentSuccess(0);
		tokenInfo.setTokenNum(tokenNum);
		return adminOtpSvc.addOtp(tokenInfo);
	}
	
	/**
	 * 管理--令牌--删除令牌
	 */
	@RequestMapping(value="deleteOtp")
	@ResponseBody
	public Map<String, Object> deleteOtp( 
			String otps){
		return adminOtpSvc.deleteOtp(otps);
	}
}
