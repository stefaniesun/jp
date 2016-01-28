package xyz.svc.main.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.main.Address;
import xyz.model.main.User;
import xyz.model.member.XyzSessionLogin;
import xyz.model.member.XyzSessionUtil;
import xyz.svc.main.UserSvc;
import xyz.util.Constant;
import xyz.util.EncryptionUtil;
import xyz.util.UUIDUtil;

@Service
public class UserSvcImp implements UserSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> loginOper(String username, String password) {
		
		System.out.println("用户名:"+username);
		System.out.println("密码:"+password);
	
		String passwordSe = EncryptionUtil.md5(password);

		String hql ="from User  where username='"+username+"' and password='"+passwordSe+"'";
		User user =  (User) commonDao.queryUniqueByHql(hql);
		if(user==null){
			return ReturnUtil.returnMap(0,"用户名或密码错误");
		}
		
		String apikey = UUIDUtil.getUUIDStringFor32();
		XyzSessionLogin xyzSessionLogin = new XyzSessionLogin();
		xyzSessionLogin.setApikey(apikey);
		xyzSessionLogin.setExpireDate(new Date(new Date().getTime()+Constant.sessionTimes));
		xyzSessionLogin.setUsername(username);
		
		XyzSessionUtil.logins.put(apikey, xyzSessionLogin);
		
		return ReturnUtil.returnMap(1, xyzSessionLogin);
	}

	@Override
	public Map<String, Object> editPassword(String oldPassword,
			String newPassword) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		
		String passwordSe = EncryptionUtil.md5(oldPassword);

		String hql ="from User  where username='"+xyzSessionLogin.getUsername()+"' and password='"+passwordSe+"'";
		User user =  (User) commonDao.queryUniqueByHql(hql);
		if(user==null){
			return ReturnUtil.returnMap(0,"旧密码错误");
		}
		
		String newPasswordSe = EncryptionUtil.md5(newPassword);
		user.setPassword(newPasswordSe);
		commonDao.save(user);
		
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> registerOper(String username, String password) {
		
		String hql ="from User  where username='"+username+"'";
		User user =  (User) commonDao.queryUniqueByHql(hql);
		if(user!=null){
			System.out.println("1111111");
			return ReturnUtil.returnMap(0,"用户名已存在");
		}
		System.out.println("22222222222");
		String passwordSe = EncryptionUtil.md5(password);
		user =new User();
		user.setUserName(username);
		user.setNickName(username);
		user.setPassword(passwordSe);
		commonDao.save(user);
		
		String apikey = UUIDUtil.getUUIDStringFor32();
		XyzSessionLogin xyzSessionLogin = new XyzSessionLogin();
		xyzSessionLogin.setApikey(apikey);
		xyzSessionLogin.setExpireDate(new Date(new Date().getTime()+Constant.sessionTimes));
		xyzSessionLogin.setUsername(username);
		
		XyzSessionUtil.logins.put(apikey, xyzSessionLogin);
		
		return ReturnUtil.returnMap(1, xyzSessionLogin);
	}

	@Override
	public Map<String, Object> addAddress(String linkName, String linkPhone,
			String address,String addressDistrict) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		
		Address a=new Address();
		a.setNumberCode(UUIDUtil.getUUIDStringFor32());
		a.setUsername(xyzSessionLogin.getUsername());
		a.setLinkName(linkName);
		a.setLinkPhone(linkPhone);
		a.setAddress(address);
		a.setAddressDistrict(addressDistrict);
		commonDao.save(a);
		return ReturnUtil.returnMap(1, a);
	}

	@Override
	public Map<String, Object> queryAddressList() {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		String hql="from Address where username='"+xyzSessionLogin.getUsername()+"'";
		List<Address> addresses=commonDao.queryByHql(hql);
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", addresses.size());
		mapContent.put("rows",addresses);
		return ReturnUtil.returnMap(1, mapContent);
	}

	@Override
	public Map<String, Object> getUser(String username) {
		User user=(User) commonDao.getObjectByUniqueCode("User", "userName", username);
		if(user==null){
			return ReturnUtil.returnMap(0, "用户不存在");
		}
		return ReturnUtil.returnMap(1, user);
	}

}
