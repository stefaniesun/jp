package xyz.ctrl.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.model.security.SecurityUser;
import xyz.svc.security.AdminUserSvc;
import xyz.util.EncryptionUtil;

@Controller
@RequestMapping(value="/AdminUserWS")
public class AdminUserWS{
	
	@Autowired
	private AdminUserSvc adminUserSvc;
	
	@RequestMapping(value="queryUserList")
	@ResponseBody
	public Map<String, Object> queryUserList(
			int page,
			int rows,
			String username,
			String nickName,
			String position,
			String modelStr){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return adminUserSvc.queryUserList(offset, pagesize,username, nickName, position, modelStr);
	}
	
	
	@RequestMapping(value="addUser")
	@ResponseBody
	public Map<String, Object> addUser(
			String username,
			String nickName){
		SecurityUser securityUser = new SecurityUser();
		securityUser.setUsername(username);
		securityUser.setPassword(EncryptionUtil.md5("49ba59abbe56e057{"+username+"}"));
		securityUser.setNickName(nickName);
		return adminUserSvc.addUser(securityUser);
	}
	
	@RequestMapping(value="editUser")
	@ResponseBody
	public Map<String, Object> editUser(
			String username,
			String nickName){
		return adminUserSvc.editUser(username, nickName);
	}

	
	@RequestMapping(value="deleteUser")
	@ResponseBody
	public Map<String, Object> deleteUser(
			String users){
		return adminUserSvc.deleteUser(users);
	}
	
	@RequestMapping(value="editUserEnabled")
	@ResponseBody
	public Map<String, Object> editUserEnabled(
			String username){
		return adminUserSvc.editUserEnabled(username);
	}
	
	@RequestMapping(value="setUserPosition")
	@ResponseBody
	public Map<String, Object> setUserPosition(
			String username,
			String position){
		return adminUserSvc.setUserPosition(username, position);
	}
	
	
	@RequestMapping(value="setUserOtp")
	@ResponseBody
	public Map<String, Object> setUserOtp(
			String username,
			String userOtp){
		return adminUserSvc.setUserOtp(username, userOtp);
	}
	
	@RequestMapping(value="getAllPosition")
	@ResponseBody
	public Map<String, Object> getAllPosition(){
		return adminUserSvc.getAllPosition();
	}
	
	@RequestMapping(value="setUserPossessor")
	@ResponseBody
	public Map<String, Object> setUserPossessor(String username,String possessor){
		return adminUserSvc.setUserPossessor(username, possessor);
	}
	
	@RequestMapping(value="editUserPassword")
	@ResponseBody
	public Map<String, Object> editUserPassword(String username,String password){
		return adminUserSvc.editUserPassword(username, password);
	}
	
	
	@RequestMapping(value="getSecurityUserList")
	@ResponseBody
	public Map<String, Object> getSecurityUserList(String q){
		return adminUserSvc.getSecurityUserList(q);
	}
}
