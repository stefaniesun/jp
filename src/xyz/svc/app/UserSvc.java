package xyz.svc.app;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface UserSvc {

	Map<String, Object> loginOper(String username, String password);

}
