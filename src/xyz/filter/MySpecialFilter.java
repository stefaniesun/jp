package xyz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import xyz.exception.MyExceptionForRole;

@Component
public class MySpecialFilter implements Filter{
	private static String[] publicUrls = new String[]{
		"/SellerWS/loginOper.xyz",
		"/UserWS/loginOper.xyz",
		"/UserWS/logout.xyz",
		"/InitWS/init_1239127awdasd_api.xyz",
		"/CustomerWS/recoverPassword.app",
	};
	
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
		String path = request.getServletPath();
		System.out.println(path);
		boolean flag = false;
		for(String url : publicUrls){
			if(path.equals(url)){
				flag = true;
			}
		}
		if(flag){
			chain.doFilter(request1, response1);
		}else{
			throw new MyExceptionForRole("您无权访问！");
		}
	}
	
	@Override
	public void destroy() {
		;
	}
}
