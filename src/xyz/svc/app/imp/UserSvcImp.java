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
			return ReturnUtil.returnMap(0, "登陆密码正确");
		}
		/*String hql = "from SecurityUser s where s.username = '"+username+"'";
		SecurityUser securityUser = (SecurityUser)commonDao.queryUniqueByHql(hql);
		if(securityUser==null){
			return ReturnUtil.returnMap(0, ConstantMsg.login_username);
		}
		String passwordSe = EncryptionUtil.md5(password+"{"+username+"}");
		if(!passwordSe.equals(securityUser.getPassword())){
			return ReturnUtil.returnMap(0, ConstantMsg.login_password);
		}
		if(securityUser.getEnabled()!=1){
			return ReturnUtil.returnMap(0, ConstantMsg.login_enabled);
		}
		if(securityUser.getIsRepeat()==0){
			hql = "delete SecurityLogin s where s.username = '"+securityUser.getUsername()+"'";
			commonDao.updateByHql(hql);
		}
		if("49ba59abbe56e057".equals(password)){
			return ReturnUtil.returnMap(0,"系统禁止使用原始密码登录，请先修改密码");
		}*/
		return ReturnUtil.returnMap(1,null);
	}

}
