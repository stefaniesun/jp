package xyz.ctrl.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.app.UserSvc;


@Controller
@RequestMapping(value="/UserWS")
public class UserWS {

	@Autowired
	private UserSvc userSvc;
	
	@RequestMapping(value="loginOper")
	@ResponseBody
	public Map<String, Object> loginOper(String username,String password){
		return userSvc.loginOper(username,password);
	}
	
	
	@RequestMapping(value="logout")
	@ResponseBody
	public Map<String, Object> logout(String username){
		return userSvc.logout(username);
	}
}
