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
	
	
	/**
	 * 用户注册
	 */
	@RequestMapping(value="registerOper")
	@ResponseBody
	public Map<String,Object> registerOper(String username,String password){
		return userSvc.registerOper(username,password);
	}
	
	@RequestMapping(value="getUser")
	@ResponseBody
	public Map<String,Object> getUser(String username){
		return userSvc.getUser(username);
	}
	
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value="editPassword")
	@ResponseBody
	public Map<String,Object> editPassword(String oldPassword,String newPassword){
		return userSvc.editPassword(oldPassword,newPassword);
	}
	
	@RequestMapping(value="addAddress")
	@ResponseBody
	public Map<String,Object> addAddress(String linkName,String linkPhone,String address,String addressDistrict){
		return userSvc.addAddress(linkName,linkPhone,address,addressDistrict);
	}
	
	@RequestMapping(value="editAddress")
	@ResponseBody
	public Map<String,Object> editAddress(String numberCode,String linkName,String linkPhone,String address,String addressDistrict){
		return userSvc.editAddress(numberCode,linkName,linkPhone,address,addressDistrict);
	}
	
	@RequestMapping(value="deleteAddress")
	@ResponseBody
	public Map<String,Object> deleteAddress(String numberCode){
		return userSvc.deleteAddress(numberCode);
	}
	
	@RequestMapping(value="getAddress")
	@ResponseBody
	public Map<String,Object> getAddress(String numberCode){
		return userSvc.getAddress(numberCode);
	}
	
	@RequestMapping(value="queryAddressList")
	@ResponseBody
	public Map<String,Object> queryAddressList(){
		return userSvc.queryAddressList();
	}
}
