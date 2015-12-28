package xyz.svc.security;

import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.security.SecurityUser;




@Service
public interface InitSvc {
	
	public Map<String, Object> initApiOper(Map<String, Object> content);
	
	public Map<String, Object> initAdminPositionOper();
	
	public Map<String, Object> initAdminOper(SecurityUser securityUser);
	
	public Map<String, Object> cleanPositionFunctionApi();
}
