package xyz.svc.security;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.security.SecurityUser;


@Service
public interface AdminUserSvc {
	
	public Map<String, Object> queryUserList(
			int offset,
			int pagesize,
			String username,
			String nickName,
			String position,
			String modelStr);
	
	public Map<String, Object> addUser(SecurityUser securityUser);
	
	public Map<String, Object> editUser(
			String username,
			String nickName);
	
	public Map<String, Object> editUserEnabled(String username);
	
	public Map<String, Object> deleteUser(String users);
	
	public Map<String, Object> setUserPosition(String username,String position);
	
	public Map<String, Object> getAllPosition();
	
	public Map<String, Object> setUserOtp(String username,String otp);
	
	public List<Map<String, Object>> getUserListForDecideStr();
	
	public Map<String, Object> getSecurityUserList();
	
	public Map<String, Object> getSecurityUser(String username);
	
	public Map<String, Object> setUserPossessor(
			String username,
			String possessor);
	
	public Map<String, Object> editUserPassword(String username,String password);
	
	public Map<String, Object> getSecurityUserList(String q);
	
}
