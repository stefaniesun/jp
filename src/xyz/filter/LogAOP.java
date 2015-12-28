package xyz.filter;


import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.model.security.LogOper;
import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityLogin;
import xyz.svc.security.LogSvc;
import xyz.util.Constant;

@Aspect
@Component
public class LogAOP
{
	@Autowired
	private LogSvc logSvc;
	
	//方法执行的前后调用
	//@AfterReturning(value="@annotation(xyz.filter.LogAOPAnnotation)",returning="map")
	//@AfterReturning(value="@annotation(logAOPAnnotation)",returning="map")
	@AfterReturning(value="execution(* xyz.ctrl.*.*WS.*(..))",returning="map")
    public void afterReturnMethod(
    		JoinPoint point,
    		Object map) throws Throwable{
		LogOper logOper = new LogOper();
		{
			SecurityLogin securityLogin = MyRequestUtil.getSecurityLogin();
			if(securityLogin==null){
				return;
			}
			System.out.print(securityLogin.getUsername());
			logOper.setUsername(securityLogin.getUsername());
		}
		{
			SecurityApi securityApi = MyRequestUtil.getSecurityApi();
			if(securityApi==null){
				return;
			}
			System.out.print(securityApi.getNameCn());
			System.out.println(securityApi.getUrl());
			if(securityApi.getIsWork()==0){
				return;
			}
			logOper.setInterfacePath(securityApi.getUrl());
			logOper.setIsWork(securityApi.getIsWork());
			logOper.setRemark(securityApi.getNameCn());
		}
		{
			if(map==null){
				return;
			}
			@SuppressWarnings("rawtypes")
			Map jsonMap = MyRequestUtil.getRequest().getParameterMap();
			String jsonStr = JSON.toJosn(jsonMap);
			logOper.setDataContent(jsonStr);
			if(map instanceof Map){
				@SuppressWarnings("unchecked")
				int flagResult = (Integer)((Map<String, Object>)map).get(Constant.result_status);
				logOper.setFlagResult(flagResult);
			}else{
				return;
			}
		}
		logOper.setIpInfo(MyRequestUtil.getIp());
		logSvc.addLogOper(logOper);
		//point.proceed();如果有返回值，可继续进行,只有环绕通知有返回值
    }
}
