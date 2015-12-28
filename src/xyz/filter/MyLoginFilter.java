package xyz.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.exception.MyExceptionForLogin;
import xyz.model.security.SecurityLogin;
import xyz.svc.security.KeySvc;
import xyz.util.Constant;

@Component
public class MyLoginFilter implements Filter{

	@Autowired
	private KeySvc keySvc;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		;
	}

	@Override
	public void doFilter(
			ServletRequest request1, 
			ServletResponse response1,
			FilterChain chain) 
					throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)request1;
		String apikey = null;
		Cookie[] ttt = request.getCookies();
		if(ttt!=null){
			for(Cookie cookie : ttt){
				if("XZTRIP_LOGIN_KEY".equals(cookie.getName())){
					apikey = cookie.getValue();
					break;
				}
			}
		}
		if(apikey==null){
			apikey = request.getParameter("apikey");
		}
		if(apikey==null){
			throw new MyExceptionForLogin("不存在有效登录信息,请重新登录！");
		}
		SecurityLogin securityLogin = keySvc.getSecurityLogin(apikey);
		if(securityLogin==null){
			throw new MyExceptionForLogin("不存在有效登录信息,请重新登录！");
		}else{
			Date date = new Date();
			long temp = securityLogin.getExpireDate().getTime()-date.getTime();
			if(temp<0){
				throw new MyExceptionForLogin("超过时限，请重新登录！");
			}else if(temp<Constant.sessionTimes){
				Date expireDate = new Date();
				expireDate.setTime(date.getTime()+Constant.sessionTimes);
				securityLogin.setExpireDate(expireDate);
				keySvc.updateSecurityLogin(securityLogin);
			}
		}
		request1.setAttribute("securityLogin", securityLogin);
		chain.doFilter(request1, response1);
	}
	
	@Override
	public void destroy() {
		;
	}
}
