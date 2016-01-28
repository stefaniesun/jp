package xyz.ctrl.base;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.base.BaseOrderSvc;
import xyz.svc.base.BaseUserSvc;

@Controller
@RequestMapping(value="/BaseUserWS")
public class BaseUserWS {

	@Autowired
	BaseUserSvc baseUserSvc;
	
	@RequestMapping(value="queryUserList")
	@ResponseBody
	public Map<String,Object> queryUserList(){
		return baseUserSvc.queryUserList();
	}
}
