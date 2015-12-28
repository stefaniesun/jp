package xyz.svc.member;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface MemberSvc {

	public Map<String, Object> loginOper(
			String username,
			String password);
	
	public Map<String, Object> memberExit();
}
