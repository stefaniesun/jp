package xyz.svc.main.imp;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
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

}
