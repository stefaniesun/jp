package xyz.svc.seller.imp;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.member.Customer;
import xyz.model.member.XyzSessionLogin;
import xyz.model.member.XyzSessionUtil;
import xyz.model.security.SecurityUser;
import xyz.svc.seller.SellerSvc;
import xyz.util.Constant;
import xyz.util.EncryptionUtil;
import xyz.util.UUIDUtil;

@Service
public class SellerSvcImp implements SellerSvc {

	@Resource
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> loginOper(String username, String password) {

		System.out.println("用户名:"+username);
		System.out.println("密码:"+password);
		
		String passwordSe = EncryptionUtil.md5(password+"{"+username+"}");
		String hql ="from  SecurityUser s where s.username='"+username+"' and s.password='"+passwordSe+"'";
		SecurityUser securityUser =  (SecurityUser) commonDao.queryUniqueByHql(hql);
		if(securityUser==null){
			return ReturnUtil.returnMap(0,"用户名或密码错误");
		}
		if(securityUser.getEnabled()==0){
			return ReturnUtil.returnMap(0, "用户受限！");
		}
		if(securityUser.getIsRepeat()==0){
			hql = "delete SecurityLogin s where s.username = '"+securityUser.getUsername()+"'";
			commonDao.updateByHql(hql);
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
