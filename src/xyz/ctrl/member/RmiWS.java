package xyz.ctrl.member;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;
import xyz.svc.security.KeySvc;

@Controller
public class RmiWS{
	
	@Autowired
	KeySvc keySvc;
	
	@Autowired
	RmiUtil rmiUtil;
	
	@RequestMapping(value="/{ta}/{tb}")
	@ResponseBody
	public Object loadData(
			HttpServletRequest request){
		StringBuffer urlString = new StringBuffer();
		
		String getServletPath = request.getServletPath();
		if(getServletPath.contains("getPtviewListByChannelAndMatch")
		||	getServletPath.contains("getTkviewListByPtview")
		||	getServletPath.contains("getProviderListByTkview")
				){
			urlString.append("http://121.41.166.4/pberpproduct");
		}else{
			return ReturnUtil.returnMap(0,"接口未注册，请联系系统管理员！【"+getServletPath+"】");
		}
		
		urlString.append(getServletPath);
		urlString.append(request.getServletPath());
		
		String urlEnd = urlString.toString();
		System.out.println(urlEnd);
		
		Map<String, String> accessoryParam = new HashMap<String, String>();
		return rmiUtil.loadData(request,urlEnd, accessoryParam);
	}
}
