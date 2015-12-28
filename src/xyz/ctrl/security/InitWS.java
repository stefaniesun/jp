package xyz.ctrl.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.exception.MyExceptionForRole;
import xyz.filter.XmlUtil;
import xyz.model.security.SecurityUser;
import xyz.svc.security.InitSvc;
import xyz.util.Constant;

@Controller
@RequestMapping(value="/InitWS")
public class InitWS {
	
	@Autowired
	private InitSvc initSvc;
	
	@RequestMapping(value="init_1239127awdasd_api")
	public String init_1239127awdasd_api(){		
		Map<String, Object> t = XmlUtil.XmlUtilFunction("config/sysFunction.xml");
		Map<String, Object> map = initSvc.initApiOper(t);
		if((Integer)map.get(Constant.result_status)!=1){
			System.out.println(map.get(Constant.result_msg));
			throw new MyExceptionForRole("初始化失败");
		}
		map = initSvc.initAdminPositionOper();
		if((Integer)map.get(Constant.result_status)!=1){
			System.out.println(map.get(Constant.result_msg));
			throw new MyExceptionForRole("初始化失败");
		}
		SecurityUser securityUser = XmlUtil.XmlUtilAdmin("config/sysAdmin.xml");
		map = initSvc.initAdminOper(securityUser);
		if((Integer)map.get(Constant.result_status)!=1){
			System.out.println(map.get(Constant.result_msg));
			throw new MyExceptionForRole("初始化失败");
		}
		return "forward:../xyzsecurity/200_init.html";
	}
}
