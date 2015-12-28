package xyz.svc.buyer.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;
import xyz.model.base.Sms;
import xyz.model.member.Customer;
import xyz.model.member.XyzSessionLogin;
import xyz.model.member.XyzSessionUtil;
import xyz.model.security.LogOper;
import xyz.svc.buyer.CustomerSvc;
import xyz.util.Constant;
import xyz.util.EncryptionUtil;
import xyz.util.StringUtil;
import xyz.util.UUIDUtil;

@Service
public class CustomerSvcImp implements CustomerSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> getRandCodeOper(String phone) {
		if(phone.length()!=11 || !"1".equals(phone.substring(0, 1))){
			return ReturnUtil.returnMap(0,"发送短信失败:手机号码不符合规范");
		}
		
		String randCode=StringUtil.getRandomStr(6);
		String content="【西藏翔腾】手机效验码是"+randCode;
		String dataKey=UUIDUtil.getUUIDStringFor32();
		Map<String,String> accessoryParam = new HashMap<String, String>();
		accessoryParam.put("content", content);
		accessoryParam.put("phone", phone);
		accessoryParam.put("dataKey",dataKey);
		accessoryParam.put("business","ABC");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>)new RmiUtil().loadData(Constant.smsUrl_smsSend, accessoryParam);
		if(result==null){
			return ReturnUtil.returnMap(0,"短信发送失败！");
		}
		Integer status = (Integer)result.get(Constant.result_status);
		if(status==null){
			return ReturnUtil.returnMap(0,"短信发送失败！");
		}
		if(status==0){
			return ReturnUtil.returnMap(0,"短信发送失败！失败原因【"+result.get(Constant.result_msg)+"】 ");
		}
	
		Date date=new Date();
		Sms sms = new Sms();
		sms.setContent(content);
		sms.setNumberCode(UUIDUtil.getUUIDStringFor32());
		sms.setPhone(phone);
        sms.setBusiness("ABC");
        sms.setUsername(phone);
        sms.setAlterDate(date);
        sms.setAddDate(date);
        sms.setRandCode(randCode);
        sms.setDataKey(dataKey);
        sms.setStatus("已发送");
		commonDao.save(sms);
		
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> registerOper(String phone, String password) {
		Customer customer=(Customer) commonDao.getObjectByUniqueCode("Customer", "username", phone);
		if(customer!=null){
			return ReturnUtil.returnMap(0,"请勿重复注册");
		}
		Date date=new Date();
		customer=new Customer();
		customer.setUsername(phone);
		customer.setAddDate(date);
		customer.setAlterDate(date);
		customer.setPassword(password);
		commonDao.save(customer);
		
		String apikey = UUIDUtil.getUUIDStringFor32();
		XyzSessionLogin xyzSessionLogin = new XyzSessionLogin();
		xyzSessionLogin.setApikey(apikey);
		xyzSessionLogin.setUsername(customer.getUsername());
		xyzSessionLogin.setExpireDate(new Date(new Date().getTime()+Constant.sessionTimes));
		XyzSessionUtil.logins.put(apikey, xyzSessionLogin);
		
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> loginOper(String username, String password) {
	
	
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
			return ReturnUtil.returnMap(0,"用户受限,暂不允许登录!");
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
	public Map<String, Object> alterPasswordOper(String oldPassword, String newPassword) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		
		if(xyzSessionLogin==null){
			return ReturnUtil.returnMap(0,"无有效登录信息");
		}
		String username=xyzSessionLogin.getUsername();
		String hql = "from Customer  where username = '"+username+"'";
		Customer customer = (Customer)commonDao.queryUniqueByHql(hql);
		
		if(customer==null){
			return ReturnUtil.returnMap(0,"用户不存在");
		}else{
			String password = EncryptionUtil.md5(oldPassword+"{"+customer.getUsername()+"}");
			
			if(!password.equals(customer.getPassword())){
				return ReturnUtil.returnMap(0,"密码输入错误");
			}else{
				password = EncryptionUtil.md5(newPassword+"{"+customer.getUsername()+"}");
				customer.setPassword(password);
				commonDao.update(customer);
				
				LogOper logOper = new LogOper();
				logOper.setAddDate(new Date());
				logOper.setDataContent(null);
				logOper.setFlagResult(1);
				logOper.setInterfacePath("/CustomerWS/alterPassword.app");
				logOper.setIpInfo(MyRequestUtil.getIp());
				logOper.setIsWork(1);
				logOper.setRemark("用户主动修改密码");
				logOper.setUsername(username);
				
				return ReturnUtil.returnMap(1,null);
			}
		}
	}

	@Override
	public Map<String, Object> customerExit() {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		if(xyzSessionLogin==null){
			return ReturnUtil.returnMap(1,null);
		}
		String apikey = xyzSessionLogin.getApikey();
		XyzSessionUtil.logins.remove(apikey);
		return ReturnUtil.returnMap(1,null);
	}



	@Override
	public Map<String, Object> recoverPasswordOper(String newPassword) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		if(xyzSessionLogin==null){
			return ReturnUtil.returnMap(0,"用户未登录");
		}
		String username=xyzSessionLogin.getUsername();
		String hql = "from Customer  where username = '"+username+"'";
		Customer customer = (Customer)commonDao.queryUniqueByHql(hql);
		
		if(customer==null){
			return ReturnUtil.returnMap(0,"用户不存在");
		}else{
			String password = EncryptionUtil.md5(newPassword+"{"+customer.getUsername()+"}");
			customer.setPassword(password);
			commonDao.update(customer);
			
			LogOper logOper = new LogOper();
			logOper.setAddDate(new Date());
			logOper.setDataContent(null);
			logOper.setFlagResult(1);
			logOper.setInterfacePath("/CustomerWS/recoverPassword.app");
			logOper.setIpInfo(MyRequestUtil.getIp());
			logOper.setIsWork(1);
			logOper.setRemark("用户找回重置密码");
			logOper.setUsername(username);
		}	
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> verifyRandCodeOper(String phone, String randCode) {
		String hql="From Sms where phone='"+phone+"' order by addDate desc";
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<Sms> smsList=commonDao.queryByHql(hql);
		if(smsList.size()<1){
			return ReturnUtil.returnMap(0,"短信验证码不正确");
		}else{
			Sms sms=smsList.get(0);
			if(!randCode.equals(sms.getRandCode())){
				return ReturnUtil.returnMap(0,"短信验证码不正确");
			}
		}
		return ReturnUtil.returnMap(1,null);
	}

}
