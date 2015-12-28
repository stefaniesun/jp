package xyz.ctrl.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ProviderSvc;

@Controller
@RequestMapping(value="/ProviderWS")
public class ProviderWS {

	@Autowired
	ProviderSvc providerSvc;
	
	/**
	 * 查询供应商列表
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="queryProviderList")
	@ResponseBody
	public Map<String,Object> queryProviderList(String nameCn,String providerType,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return providerSvc.queryProviderList(nameCn,providerType, offset, pagesize);
	}

	/**
	 * 添加供应商
	 * @param nameCn
	 * @param type
	 * @param level
	 * @param levelSystem
	 * @param phone
	 * @param qq
	 * @param email
	 * @param linkman
	 * @param regionFirst
	 * @param regionSecond
	 * @param regionThird
	 * @param address
	 * @return
	 */
	@RequestMapping(value="addProvider")
	@ResponseBody
	public Map<String,Object> addProvider(String nameCn,
			String type,
			String level,
			String levelSystem,
			String phone,
			String qq,
			String email,
			String linkman,
			String regionFirst,
			String regionSecond,
			String regionThird,
			String address
			){
		
		return providerSvc.addProvider(nameCn, type, level, levelSystem, phone, qq, email, linkman, regionFirst, regionSecond, regionThird, address);
	}
	
	/**
	 * 修改供应商
	 * @param numberCode
	 * @param nameCn
	 * @param type
	 * @param level
	 * @param levelSystem
	 * @param phone
	 * @param qq
	 * @param email
	 * @param linkman
	 * @param regionFirst
	 * @param regionSecond
	 * @param regionThird
	 * @param address
	 * @return
	 */
	@RequestMapping(value="editProvider")
	@ResponseBody
	public Map<String,Object> editProvider(String numberCode,String nameCn,
			String level,
			String levelSystem,
			String phone,
			String qq,
			String email,
			String linkman,
			String regionFirst,
			String regionSecond,
			String regionThird,
			String address){
		return providerSvc.editProvider(numberCode, nameCn, level, levelSystem, phone, qq, email, linkman, regionFirst, regionSecond, regionThird, address);
	}
	
	/**
	 * 删除供应商
	 * @param numberCodes
	 * @return
	 */
	@RequestMapping(value="deleteProvider")
	@ResponseBody
	public Map<String,Object> deleteProvider(String numberCodes){
		return providerSvc.deleteProvider(numberCodes);
	}
}
