package xyz.ctrl.base;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.base.BaseOrderSvc;


@Controller
@RequestMapping(value="/BaseOrderWS")
public class BaseOrderWS {

	BaseOrderSvc baseOrderSvc;
	
	@RequestMapping(value="queryOrderList")
	@ResponseBody
	public Map<String,Object> queryOrderList(int status){
		return baseOrderSvc.queryOrderList(status);
	}
}
