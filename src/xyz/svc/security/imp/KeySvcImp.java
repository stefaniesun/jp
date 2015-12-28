package xyz.svc.security.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityFunction;
import xyz.model.security.SecurityLogin;
import xyz.svc.security.KeySvc;
import xyz.util.StringTool;

@Service
public class KeySvcImp implements KeySvc{
	
	@Resource
	CommonDao commonDao;
	
	@Override
	public SecurityLogin getSecurityLogin(String apikey) {
		String hql = "from SecurityLogin s where s.apikey = '"+apikey+"'";
		SecurityLogin securityLogin = (SecurityLogin)commonDao.queryUniqueByHql(hql);
		return securityLogin;
	}
	
	@Override
	public List<SecurityApi> getSecurityApi(String servletPath) {
		String hql = "from SecurityApi s where s.url = '"+servletPath+"'";
		@SuppressWarnings("unchecked")
		List<SecurityApi> securityApiList = commonDao.queryByHql(hql);
		return securityApiList;
	}
	
	@Override
	public boolean decideSecurityApi(String position,String buttons){
		String hql = "select s.iidd from SecurityPositionButton s where s.position = '"+position+"' and s.button in ("+buttons+")";
		int t = commonDao.queryByHql(hql).size();
		boolean result = t>0?true:false;
		return result;
	}
	
	@Override
	public void updateSecurityLogin(SecurityLogin securityLogin) {
		commonDao.update(securityLogin);
	}
	
	@Override
	public void safeQuitOper() {
		String username = MyRequestUtil.getSecurityLogin().getUsername();
		if(username!=null && !"".equals(username)){
			String hql = "delete SecurityLogin s where s.username = '"+username+"'";
			commonDao.updateByHql(hql);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> decideLogin(SecurityLogin securityLogin){
		
		String hql = "select t.button from SecurityPositionButton t where t.position = '"+securityLogin.getPosition()+"'";
		List<String> buttonList = commonDao.queryByHql(hql);
		
		hql = "select s.function from SecurityApi s where s.buttonCode in ("+StringTool.listToSqlString(buttonList)+")";
		List<String> functionList = commonDao.queryByHql(hql);
		
		hql = "from SecurityFunction s where s.numberCode in ("+StringTool.listToSqlString(functionList)+")";
		List<SecurityFunction> securityFunctionList = commonDao.queryByHql(hql);
		
		Map<String, Object> mapContent = new HashMap<String, Object>();
		mapContent.put("securityLogin", securityLogin);
		mapContent.put("securityFunctionList", securityFunctionList);
		mapContent.put("buttonList", buttonList);
		return ReturnUtil.returnMap(1,mapContent);
	}
}
