package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface UserSvc {

	Map<String, Object> loginOper(String username, String password);

	Map<String, Object> editPassword(String oldPassword, String newPassword);

	Map<String, Object> registerOper(String username, String password);

	Map<String, Object> addAddress(String linkName, String linkPhone,
			String address,String addressDistrict);

	Map<String, Object> queryAddressList();

	Map<String, Object> getUser(String username);

	Map<String, Object> deleteAddress(String numberCode);

	Map<String, Object> editAddress(String numberCode, String linkName, String linkPhone, String address,
			String addressDistrict);

	Map<String, Object> getAddress(String numberCode);

}
