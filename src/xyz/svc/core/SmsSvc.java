package xyz.svc.core;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface SmsSvc{
	
	public Map<String, Object> querySmsList(
			int offset, 
			int pagesize,
			String clientCode,
			String phone,
			String content,
			String username,
			String status,
			Date dateStart,
			Date dateEnd);
	
	public Map<String, Object> addSms(
			String phone,
			String content);
	
	public Map<String, Object> updateSmsStatus(String numberCode);
	
}
