package xyz.ctrl.buyer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.member.XyzSessionLogin;
import xyz.svc.buyer.CustomerSvc;


@Controller
@RequestMapping(value="/CustomerWS")
public class CustomerWS {

	@Autowired
	CustomerSvc customerSvc;
	
	/**
	 * 获取验证码
	 */
	@RequestMapping(value="getRandCode")
	@ResponseBody
	public Map<String,Object> getRandCode(String phone,String content){
		return customerSvc.getRandCodeOper(phone);
	}
	
	/**
	 * 验证验证码
	 */
	@RequestMapping(value="verifyRandCode")
	@ResponseBody
	public Map<String,Object> verifyRandCode(String phone,String randCode){
		return customerSvc.verifyRandCodeOper(phone,randCode);
	}
	
	/**
	 * 用户注册
	 */
	@RequestMapping(value="register")
	@ResponseBody
	public Map<String,Object> register(String phone,String password){
		return customerSvc.registerOper(phone,password);
	}
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value="login")
	@ResponseBody
	public Map<String, Object> login(
			String username,
			String password){
		return customerSvc.loginOper(username, password);
	}
	
	
	/**
	 * 用户修改密码
	 */
	@RequestMapping(value="alterPassword")
	@ResponseBody
	public Map<String, Object> alterPassword(
			String oldPassword,
			String newPassword){
		return customerSvc.alterPasswordOper(oldPassword,newPassword);
	}
	
	
	/**
	 * 用户找回密码
	 */
	@RequestMapping(value="recoverPassword")
	@ResponseBody
	public Map<String, Object> recoverPassword(String newPassword){
		return customerSvc.recoverPasswordOper(newPassword);
	}
	
	
	/**
	 * 验证登录
	 * @return
	 */
	@RequestMapping(value="decideLogin")
	@ResponseBody
	public Map<String, Object> decideLogin(){
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		if(xyzSessionLogin==null){
			return ReturnUtil.returnMap(0, "无有效登录信息");
		}else{
			return ReturnUtil.returnMap(1,xyzSessionLogin);
		}
	}
	
	/**
	 * 用户退出
	 */
	@RequestMapping(value="exit")
	@ResponseBody
	public Map<String, Object> customerExit(){
		return customerSvc.customerExit();
	}

}
