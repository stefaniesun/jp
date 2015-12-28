package xyz.svc.security.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityFunction;
import xyz.model.security.SecurityPosition;
import xyz.model.security.SecurityPositionButton;
import xyz.model.security.SecurityUser;
import xyz.svc.security.InitSvc;
import xyz.util.StringTool;

@Service
public class InitSvcImp implements InitSvc{
	
	@Autowired
	CommonDao commonDao;

	@Override
	public Map<String, Object> initAdminOper(SecurityUser securityUser) {
		String hql = "delete from SecurityUser where username = 'admin'";
		commonDao.updateByHql(hql);
		commonDao.flush();
		
		Date date = new Date();
		securityUser.setAddDate(date);
		securityUser.setAlterDate(date);
		securityUser.setEnabled(1);
		securityUser.setIsRepeat(0);
		securityUser.setNickName("超级管理员");
		securityUser.setPosition("admin");
		commonDao.save(securityUser);
		return ReturnUtil.returnMap(1,null);
	}
	
	public Map<String, Object> initAdminPositionOper(){
		Date date = new Date();
		String hql = "delete from SecurityPosition where numberCode = 'admin'";
		commonDao.updateByHql(hql);
		commonDao.flush();
		
		SecurityPosition securityPosition = new SecurityPosition();
		securityPosition.setNumberCode("admin");
		securityPosition.setNameCn("超级管理员");
		securityPosition.setRemark("全部权限");
		securityPosition.setPriority(0);
		securityPosition.setAddDate(date);
		securityPosition.setAlterDate(date);
		commonDao.save(securityPosition);
		
		hql = "select t.buttonCode from SecurityApi t where t.isDecide = 1 group by t.buttonCode";
		@SuppressWarnings("unchecked")
		List<String> buttons = commonDao.queryByHql(hql);
		
		hql = "delete from SecurityPositionButton where position = 'admin'";
		commonDao.updateByHql(hql);
		commonDao.flush();
		for(String button : buttons){
			SecurityPositionButton securityPositionButton = new SecurityPositionButton();
			securityPositionButton.setPosition(securityPosition.getNumberCode());
			securityPositionButton.setButton(button);
			commonDao.save(securityPositionButton);
		}
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> initApiOper(Map<String, Object> content) {
		Date date = new Date();
		String hql = "delete from SecurityFunction";
		commonDao.updateByHql(hql);
		commonDao.flush();
		hql = "delete from SecurityApi";
		commonDao.updateByHql(hql);
		commonDao.flush();
		
		@SuppressWarnings("unchecked")
		List<Object[]> t = (List<Object[]>)content.get("functionList");
		@SuppressWarnings("unchecked")
		List<SecurityApi> t2 = (List<SecurityApi>)content.get("noList");
		
		for(Object[] tt:t){
			SecurityFunction securityFunction = (SecurityFunction)tt[0];
			if(securityFunction.getUrl()==null || "".equals(securityFunction.getUrl())){
				if(securityFunction.getIsApi()==0){
					return ReturnUtil.returnMap(0,"url为空！"+securityFunction.getNameCn());
				}
			}
			securityFunction.setAddDate(date);
			securityFunction.setAlterDate(date);
			commonDao.save(securityFunction);
			@SuppressWarnings("unchecked")
			List<SecurityApi> securityApiList = (List<SecurityApi>)tt[1];
			for(SecurityApi securityApi : securityApiList){
				securityApi.setAddDate(date);
				securityApi.setAlterDate(date);
				commonDao.save(securityApi);
			}
		}
		
		for(SecurityApi securityApi : t2){
			securityApi.setAddDate(date);
			securityApi.setAlterDate(date);
			commonDao.save(securityApi);
		}
		
		hql = "from SecurityApi t where t.isDecide = 1 and (t.buttonCode is null or t.buttonCode = '')";
		SecurityApi securityApi = (SecurityApi)commonDao.queryUniqueByHql(hql);
		if(securityApi!=null){
			return ReturnUtil.returnMap(0,"buttonCode为空！"+securityApi.getUrl());
		}
		
		String sql = "select count(DISTINCT t.button_code) from security_api t where t.is_decide = 1";
		Number num1 = (Number)commonDao.getSqlQuery(sql).uniqueResult();
		sql = "select count(DISTINCT t.function,t.button_code) from security_api t where t.is_decide = 1";
		Number num2 = (Number)commonDao.getSqlQuery(sql).uniqueResult();
		if(num1.intValue()!=num2.intValue()){
			sql = "select t1.button_code from security_api t1 inner join security_api t2 on t1.button_code = t2.button_code and t1.function != t2.function where t1.is_decide = 1 and t2.is_decide = 1";
			String tt = (String)commonDao.getSqlQuery(sql).setMaxResults(1).uniqueResult();
			return ReturnUtil.returnMap(0,"相同buttonCode的function不同！"+tt);
		}
		
		sql = "select count(DISTINCT t.url) from security_api t where t.url is not null and t.url != ''";
		Number num3 = (Number)commonDao.getSqlQuery(sql).uniqueResult();
		sql = "select count(DISTINCT t.url,t.is_decide,t.is_work,t.flag_server) from security_api t where t.url is not null and t.url != ''";
		Number num4 = (Number)commonDao.getSqlQuery(sql).uniqueResult();
		if(num3.intValue()!=num4.intValue()){
			sql = "select t1.url from security_api t1 inner join security_api t2 on t1.url = t2.url and t1.url is not null and t2.url is not null and t1.url!='' and t2.url !='' and (t1.is_decide != t2.is_decide or t1.is_work!= t2.is_work or t1.flag_server != t2.flag_server) where t1.is_decide = 1 and t2.is_decide = 1";
			String tt = (String)commonDao.getSqlQuery(sql).setMaxResults(1).uniqueResult();
			return ReturnUtil.returnMap(0,"相同url的关键属性不同！"+tt);
		}
		
		hql = "select t.buttonCode from SecurityApi t where t.isDecide = 1 group by t.buttonCode";
		@SuppressWarnings("unchecked")
		List<String> buttons = commonDao.queryByHql(hql);
		if(buttons.size()>0){
			hql = "delete from SecurityPositionButton t where t.button not in ("+StringTool.listToSqlString(buttons)+")";
			commonDao.updateByHql(hql);
		}
		
		return ReturnUtil.returnMap(1,null);
	}
	
	public Map<String, Object> cleanPositionFunctionApi(){
		/**
		 * 删除关系表中不存在的功能和操作
		 * 添加
		 */
		return ReturnUtil.returnMap(1,null);
	}
}
