package xyz.svc.core;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ListSvc {
	public Map<String, Object> getSecurityUserList();
	
	public Map<String,Object> getProviderList(String providerType);
}
