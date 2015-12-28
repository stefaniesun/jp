package xyz.svc.security;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityLogin;

@Service
public interface KeySvc {
	
	public SecurityLogin getSecurityLogin(String apikey);
	
	public List<SecurityApi> getSecurityApi(String servletPath);
	
	public boolean decideSecurityApi(String position,String servletPath);
	
	public void updateSecurityLogin(SecurityLogin securityLogin);
	
	public void safeQuitOper();
	
	public Map<String, Object> decideLogin(SecurityLogin securityLogin);
}
