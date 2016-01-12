package xyz.ctrl.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ProviderSvc;
import xyz.svc.main.UserSvc;


@Controller
@RequestMapping(value="/UserWS")
public class UserWS {

	@Autowired
	UserSvc userSvc;
	
	/**
	 * 用户登陆
	 */
	@RequestMapping(value="loginOper")
	@ResponseBody
	public Map<String,Object> loginOper(String username,String password){
		return userSvc.loginOper(username,password);
	}
}
