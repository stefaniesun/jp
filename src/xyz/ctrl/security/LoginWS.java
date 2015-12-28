package xyz.ctrl.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.security.SecurityLogin;
import xyz.svc.security.KeySvc;
import xyz.svc.security.LoginSvc;

@Controller
@RequestMapping(value="/LoginWS")
public class LoginWS{
	
	@Autowired
	private LoginSvc loginSvc;
	
	@Autowired
	private KeySvc keySvc;
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="alterPassword")
	@ResponseBody
	public Map<String, Object> alterPassword(
			String username,
			String oldPassword,
			String newPassword){
		return loginSvc.alterUserPasswordOper(username,oldPassword, newPassword);
	}
	
	/**
	 * 安全退出
	 */
	@RequestMapping(value="safeQuit")
	@ResponseBody
	public Map<String, Object> safeQuit(){
		keySvc.safeQuitOper();
		return ReturnUtil.returnMap(1, null);
	}
	
	/**
	 * 普通登录
	 */
	@RequestMapping(value="login")
	@ResponseBody
	public Map<String, Object> login(
			String username,
			String password, 
			Integer indateHours, 
			String phoneType, 
			String phoneCode){
		return loginSvc.loginOper(username, password, indateHours==null?0:indateHours);
	}
	
	/**
	 * 口令登录
	 */
	@RequestMapping(value="loginOtp")
	@ResponseBody
	public Map<String, Object> loginOtp(
		String username,
		String password,
		String otpCode,
		int otpIsSynch,
		Integer indateHours, 
		String phoneType, 
		String phoneCode){
		return loginSvc.loginOtpOper(username, password, otpCode, otpIsSynch, indateHours);
	}
	
	/**
	 * 登录验证
	 */
	@RequestMapping(value="decideLogin")
	@ResponseBody
	public Map<String, Object> decideLogin(){
		SecurityLogin securityLogin = MyRequestUtil.getSecurityLogin();
		return keySvc.decideLogin(securityLogin);
	}

}
