package xyz.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import xyz.model.member.XyzSessionLogin;
import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityLogin;
import xyz.util.DecideObject;

public class MyRequestUtil{
	
	public static HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	public static SecurityLogin getSecurityLogin(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityLogin securityLogin = (SecurityLogin)request.getAttribute("securityLogin");
		return securityLogin;
	}
	
	public static SecurityApi getSecurityApi(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		SecurityApi securityApi = (SecurityApi)request.getAttribute("securityApi");
		return securityApi;
	}
	
	public static XyzSessionLogin getXyzSessionLogin(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		XyzSessionLogin xyzSessionLogin = (XyzSessionLogin)request.getAttribute("xyzSessionLogin");
		return xyzSessionLogin;
	}
	
	public static String getIp(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
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
	
	@SuppressWarnings("unchecked")
	public static DecideObject getDecideChannel(){
		String decideStr = getSecurityLogin().getDecideStr();
		decideStr = decideStr==null||"".equals(decideStr)?"{}":decideStr;
		Map<String, Object> t = JSON.toObject(decideStr, Map.class);
		DecideObject decideObject = new DecideObject();
		if("1".equals(t.get("channelFlag"))){
			decideObject.setFlag(1);
			List<String> tt = (List<String>)t.get("channels");
			decideObject.setList(tt);
			List<String> tt2 = (List<String>)t.get("channelNameCns");
			decideObject.setList2(tt2);
		}else{
			decideObject.setFlag(0);
		}
		return decideObject;
	}
	
	@SuppressWarnings("unchecked")
	public static DecideObject getDecideGroupTitle(){
		String decideStr = getSecurityLogin().getDecideStr();
		decideStr = decideStr==null||"".equals(decideStr)?"{}":decideStr;
		Map<String, Object> t = JSON.toObject(decideStr, Map.class);
		DecideObject decideObject = new DecideObject();
		if("1".equals(t.get("groupTitleFlag"))){
			decideObject.setFlag(1);
			List<String> tt = (List<String>)t.get("groupTitles");
			decideObject.setList(tt);
		}else{
			decideObject.setFlag(0);
		}
		return decideObject;
	}
	
	@SuppressWarnings("unchecked")
	public static DecideObject getDecideOrderTkview(){
		String decideStr = getSecurityLogin().getDecideStr();
		decideStr = decideStr==null||"".equals(decideStr)?"{}":decideStr;
		Map<String, Object> t = JSON.toObject(decideStr, Map.class);
		DecideObject decideObject = new DecideObject();
		if("1".equals(t.get("orderTkviewFlag"))){
			decideObject.setFlag(1);
			List<String> tt = (List<String>)t.get("orderTkviews");
			decideObject.setList(tt);
		}else{
			decideObject.setFlag(0);
		}
		return decideObject;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean decideUtil(String flag,String numberCode){
		String decideStr = getSecurityLogin().getDecideStr();
		decideStr = decideStr==null||"".equals(decideStr)?"{}":decideStr;
		Map<String, Object> t = JSON.toObject(decideStr, Map.class);
		if("channel".equals(flag)){
			if("1".equals(t.get("channelFlag"))){
				List<String> tt = (List<String>)t.get("channels");
				for(String o : tt){
					if(o.equals(numberCode)){
						return false;
					}
				}
				return true;
			}else{
				return false;
			}
		}else if("groupTitle".equals(flag)){
			if("1".equals(t.get("groupTitleFlag"))){
				List<String> tt = (List<String>)t.get("groupTitles");
				for(String o : tt){
					if(o.equals(numberCode)){
						return false;
					}
				}
				return true;
			}else{
				return false;
			}
		}else if("orderTkview".equals(flag)){
			if("1".equals(t.get("orderTkviewFlag"))){
				List<String> tt = (List<String>)t.get("orderTkviews");
				for(String o : tt){
					if(o.equals(numberCode)){
						return false;
					}
				}
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
}
