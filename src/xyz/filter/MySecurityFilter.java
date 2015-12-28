package xyz.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityLogin;
import xyz.svc.security.KeySvc;
import xyz.svc.security.LogSvc;
import xyz.util.ConstantMsg;
import xyz.util.StringTool;

@Component
public class MySecurityFilter implements Filter{

	@Autowired
	private LogSvc logSvc;
	
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
		SecurityLogin securityLogin = (SecurityLogin)request.getAttribute("securityLogin");
		String servletPath = request.getServletPath();
		List<SecurityApi> securityApiList = keySvc.getSecurityApi(servletPath);
		if(securityApiList.size()==0){
			System.out.println(servletPath);
			throw new MyExceptionForRole("接口未建档，请联系系统管理员！");
		}
		SecurityApi securityApi = securityApiList.get(0);
		request.setAttribute("securityApi", securityApi);
		if(securityApi.getIsDecide()==1){
			Set<String> buttons = new HashSet<String>();
			for(SecurityApi s : securityApiList){
				buttons.add(s.getButtonCode());
			}
			boolean isOk = keySvc.decideSecurityApi(securityLogin.getPosition(),StringTool.listToSqlString(buttons));
			if(!isOk){
				throw new MyExceptionForRole(ConstantMsg.auth_role_error);
			}
		}
		chain.doFilter(request1, response1);
	}
	
	@Override
	public void destroy() {
		;
	}
}
