package xyz.svc.security;

import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.security.TokenInfo;




@Service
public interface AdminOtpSvc {
	
	public Map<String, Object> queryOtpList(
			int offset,
			int pagesize,
			String flag,
			String tokenNum
	);
	
	public Map<String, Object> addOtp(TokenInfo tokenInfo);
	
	public Map<String, Object> deleteOtp(String otps);
	
}
