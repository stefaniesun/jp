package xyz.ctrl.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value="/ProductWS")
public class ProductWS{
	
	/*@Autowired
	private ListSvc listSvc;
	
	@RequestMapping(value="getSecurityUserList")
	@ResponseBody
	public Map<String, Object> getSecurityUserList(){
		Map<String, Object> results = listSvc.getSecurityUserList();
		return results;
	}*/
}
