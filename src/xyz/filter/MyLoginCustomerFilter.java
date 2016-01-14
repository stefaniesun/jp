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
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import xyz.exception.MyExceptionForLogin;
import xyz.model.member.XyzSessionLogin;
import xyz.model.member.XyzSessionUtil;
import xyz.util.Constant;

@Component
public class MyLoginCustomerFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		;
	}

	@Override
	public void doFilter(
			ServletRequest request, 
			ServletResponse response,
			FilterChain chain) 
					throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		String apikey = httpServletRequest.getParameter("JP_LOGIN_KEY");
		if(apikey==null){
			Cookie[] ttt = httpServletRequest.getCookies();
			if(ttt!=null){
				for(Cookie cookie : ttt){
					if("JP_LOGIN_KEY".equals(cookie.getName())){
						apikey = cookie.getValue();
					}
				}
			}
		}
		if(apikey==null){
			apikey = request.getParameter("JP_LOGIN_KEY");
		}
		if(apikey==null){
			throw new MyExceptionForLogin("不存在有效登录信息,请重新登录！");
		}
		XyzSessionLogin xyzSessionLogin = XyzSessionUtil.logins.get(apikey);
		
		if(xyzSessionLogin==null){
			throw new MyExceptionForLogin("不存在有效登录信息,请重新登录！");
		}else{
			Date date = new Date();
			long temp = xyzSessionLogin.getExpireDate().getTime()-date.getTime();
			if(temp<0){
				throw new MyExceptionForLogin("超过时限，请重新登录！");
			}else if(temp<Constant.sessionTimes){
				Date expireDate = new Date();
				expireDate.setTime(date.getTime()+Constant.sessionTimes);
				xyzSessionLogin.setExpireDate(expireDate);
				XyzSessionUtil.logins.put(apikey, xyzSessionLogin);
			}
		}
		request.setAttribute("xyzSessionLogin", xyzSessionLogin);
		chain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	@Override
	public void destroy() {
		;
	}
}
