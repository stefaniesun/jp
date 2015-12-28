package xyz.ctrl.base;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.core.ListSvc;


@Controller
@RequestMapping(value="/ListWS")
public class ListWS{
	
	@Autowired
	private ListSvc listSvc;
	
	/**
	 * 下拉框-获取指定类型供应商
	 * @param providerType
	 * @return
	 */
	@RequestMapping(value="getProviderList")
	@ResponseBody
	public Map<String, Object> getProviderList(String providerType){
		Map<String, Object> results =listSvc.getProviderList(providerType) ;
		return results;
	}
}
