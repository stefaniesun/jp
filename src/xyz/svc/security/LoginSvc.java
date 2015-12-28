package xyz.svc.security;

import java.util.Map;

import org.springframework.stereotype.Service;



@Service
public interface LoginSvc {
	
	public Map<String, Object> alterUserPasswordOper(
			String username,
			String oldPassword,
			String newPassword);
	
	public Map<String, Object> loginOper(
			String username,
			String password,
			int indateHours);
	
	public Map<String, Object> loginOtpOper(
			String username,
			String password,
			String otpCode,
			int otpIsSynch,
			int indateHours);
	
	public Map<String, Object> otpOper(String tokenNum,String otpCode);
}
