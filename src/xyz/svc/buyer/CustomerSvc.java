package xyz.svc.buyer;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface CustomerSvc {

	Map<String, Object> getRandCodeOper(String phone);

	Map<String, Object> registerOper(String phone,String password);

	Map<String, Object> loginOper(String username, String password);

	Map<String, Object> alterPasswordOper(String oldPassword,String newPassword);

	Map<String, Object> customerExit();

	Map<String, Object> recoverPasswordOper(String newPassword);

	Map<String, Object> verifyRandCodeOper(String phone, String randCode);

}
