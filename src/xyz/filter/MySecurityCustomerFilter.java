package xyz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.exception.MyExceptionForRole;
import xyz.svc.security.KeySvc;
import xyz.svc.security.LogSvc;

@Component
public class MySecurityCustomerFilter implements Filter{

	@Autowired
	private LogSvc logSvc;
	
	@Autowired
	private KeySvc keySvc;
	
	private static String[] publicUrls = new String[]{
		"",
		""
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
		String servletPath = request.getServletPath();
		boolean flagSuccess = false;
		
		
		
		for(String url : publicUrls){
			if(servletPath.equals(url)){
				flagSuccess = true;
				break;
			}
		}
		
		flagSuccess = true;
		if(flagSuccess==false){
			throw new MyExceptionForRole("您无权访问此接口！");
		}
		chain.doFilter(request1, response1);
	}
	
	@Override
	public void destroy() {
		;
	}
}
