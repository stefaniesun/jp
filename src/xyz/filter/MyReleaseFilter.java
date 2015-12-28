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
import xyz.util.SysPropertyTool;

@Component
public class MyReleaseFilter implements Filter{
	
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
		String ip = getIp(request);
		if(SysPropertyTool.getValue("allowIps")!=null && SysPropertyTool.getValue("allowIps").contains(ip)){
			chain.doFilter(request1, response1);
		}else{
			String path = request.getServletPath();
			if(path.contains("JointCallbackWS/notice")){
				chain.doFilter(request1, response1);
			}else if(path.contains("OrderYueWS/getStat")){
				chain.doFilter(request1, response1);
			}else{
				throw new MyExceptionForRole("您所在的ip无此权限！");
			}
		}
	}
	
	@Override
	public void destroy() {
		;
	}
	
	private static String getIp(HttpServletRequest request){
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getHeader("X-Forwarded-For");   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("Proxy-Client-IP");   
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("WL-Proxy-Client-IP");   
		}   
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("HTTP_CLIENT_IP");
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
			ip = request.getRemoteAddr();   
		}   
		return ip;
	}
}
