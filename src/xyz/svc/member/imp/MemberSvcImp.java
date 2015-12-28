package xyz.svc.member.imp;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;
import xyz.model.member.Customer;
import xyz.model.member.XyzSessionLogin;
import xyz.model.member.XyzSessionUtil;
import xyz.svc.member.MemberSvc;
import xyz.util.Constant;
import xyz.util.EncryptionUtil;
import xyz.util.UUIDUtil;

@Service
public class MemberSvcImp extends HttpServlet implements MemberSvc{

	private static final long serialVersionUID = 1L;

	@Resource
	CommonDao commonDao;
	
	@Autowired
	RmiUtil rmiUtil;
	
	@Override
	public Map<String, Object> loginOper(
			String username,
			String password) {
		String passwordSe = EncryptionUtil.md5(password+"{"+username+"}");
		
		String hql1 = "from Customer t where t.username = '"+username+"'  and t.password = '"+passwordSe+"'"; 
		Customer customer = (Customer) commonDao.queryUniqueByHql(hql1);
		
		/**
		 * 验证用户登录的账号是否合格
		 */
		if(customer == null){
			return ReturnUtil.returnMap(0,"用户名或密码错误！");
		}
		if(customer.getEnabled()==0){
			return ReturnUtil.returnMap(0,"账户受限,暂不允许登录!");
		}
		
		String apikey = UUIDUtil.getUUIDStringFor32();
		XyzSessionLogin xyzSessionLogin = new XyzSessionLogin();
		xyzSessionLogin.setApikey(apikey);
		xyzSessionLogin.setUsername(customer.getUsername());
		xyzSessionLogin.setExpireDate(new Date(new Date().getTime()+Constant.sessionTimes));
		XyzSessionUtil.logins.put(apikey, xyzSessionLogin);
		
		return ReturnUtil.returnMap(1, xyzSessionLogin);
	}

	@Override
	public Map<String, Object> memberExit() {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		if(xyzSessionLogin==null){
			return ReturnUtil.returnMap(1,null);
		}
		String apikey = xyzSessionLogin.getApikey();
		XyzSessionUtil.logins.remove(apikey);
		return ReturnUtil.returnMap(1,null);
	}
}
