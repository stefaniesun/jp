package xyz.ctrl.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ConfigSvc;
import xyz.svc.main.OrderSvc;

@Controller
@RequestMapping(value="/ConfigWS")
public class ConfigWS {

	@Autowired
	private ConfigSvc configSvc;
	
	@RequestMapping(value="queryConfigList")
	@ResponseBody
	public Map<String,Object> queryConfigList(	int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return configSvc.queryConfigList(offset,pagesize);
	}
	
	@RequestMapping(value="getConfig")
	@ResponseBody
	public Map<String,Object> getConfig(String key){
		return configSvc.getConfig(key);
	}
	
	@RequestMapping(value="getPostalConfig")
	@ResponseBody
	public Map<String,Object> getPostalConfig(){
		return configSvc.getPostalConfig();
	}
	
	@RequestMapping(value="addConfig")
	@ResponseBody
	public Map<String,Object> addConfig(	String key,String name,String value){
		return configSvc.addConfig(key,name,value);
	}
	
	@RequestMapping(value="editConfig")
	@ResponseBody
	public Map<String,Object> editConfig(	String numberCode,String key,String name,String value){
		return configSvc.editConfig(numberCode,key,name,value);
	}
	
	@RequestMapping(value="deleteConfig")
	@ResponseBody
	public Map<String,Object> deleteConfig(	String numberCode){
		return configSvc.deleteConfig(numberCode);
	}
	
	
}
