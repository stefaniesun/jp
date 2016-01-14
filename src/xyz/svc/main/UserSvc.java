package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface UserSvc {

	Map<String, Object> loginOper(String username, String password);

	Map<String, Object> editPassword(String oldPassword, String newPassword);

}
