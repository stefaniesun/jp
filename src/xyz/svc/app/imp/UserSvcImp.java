package xyz.svc.app.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.TUser;
import xyz.svc.app.UserSvc;
import xyz.util.EncryptionUtil;

@Service
public class UserSvcImp implements UserSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> loginOper(String username, String password) {
		System.out.println("用户名:"+username);
		System.out.println("密码:"+password);
		
		String hql="from TUser where userName='"+username+"'";
		TUser user=(TUser) commonDao.queryUniqueByHql(hql);
		if(user==null){
			return ReturnUtil.returnMap(0, "登陆用户不存在");
		}
		String passwordSe = EncryptionUtil.md5(password);
		if(!passwordSe.equals(user.getPassword())){
			return ReturnUtil.returnMap(0, "登陆密码不正确");
		}
		return ReturnUtil.returnMap(1,user);
	}

	@Override
	public Map<String, Object> logout(String username) {
		return ReturnUtil.returnMap(1,0);
	}

}
