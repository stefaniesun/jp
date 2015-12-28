package xyz.ctrl.base;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.core.PossessorSvc;

@Controller
@RequestMapping(value="/PossessorWS")
public class PossessorWS {

	@Autowired
	PossessorSvc possessorSvc;
	
	/**
	 * 管理员--资源组--查询资源组列表
	 */
	@RequestMapping(value="queryPossessorList")
	@ResponseBody
	public Map<String, Object> queryPossessorList(
			int page,
			int rows,
			String numberCode,
			String nameCn){
		
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		
		return possessorSvc.queryPossessorList(offset, pagesize, numberCode, nameCn);
	}
	
	/**
	 *  管理员--资源组--查询资源组有的资源
	 */
	@RequestMapping(value="getPossessor")
	@ResponseBody
	public Map<String ,Object> getPossessor(String possessor){
		
		return possessorSvc.getPossessor(possessor);
	}
	
	/**
	 * 管理员--用户管理--查询用户的资源组
	 */
	@RequestMapping(value="queryUserPossessorTrueList")
	@ResponseBody
	public Map<String, Object> queryUserPossessorTrueList(
			int page,
			int rows,
			String inUserPossessorNumberCodes,
			String numberCode,
			String nameCn){
		
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		
		return possessorSvc.queryPossessorListByInOrNotInPossessorNumberCodes(offset, pagesize, inUserPossessorNumberCodes, numberCode, nameCn, true);
	}
	
	/**
	 * 管理员--用户管理--查询用户没有的资源组
	 */
	@RequestMapping(value="queryUserPossessorFalseList")
	@ResponseBody
	public Map<String, Object> queryUserPossessorFalseList(
			int page,
			int rows,
			String inUserPossessorNumberCodes,
			String numberCode,
			String nameCn){
		
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		
		return possessorSvc.queryPossessorListByInOrNotInPossessorNumberCodes(offset, pagesize, inUserPossessorNumberCodes, numberCode, nameCn, false);
	}
	
	/**
	 *  管理员--资源组--添加资源组
	 */
	@RequestMapping(value="addPossessor")
	@ResponseBody
	public Map<String ,Object> addPossessor(
			String nameCn,
			String remark){
		
		return possessorSvc.addPossessor(nameCn ,remark);
	}
	
	/**
	 *  管理员--资源组--编辑资源组
	 */
	@RequestMapping(value="editPossessor")
	@ResponseBody
	public Map<String ,Object> editPossessor(
			String numberCode,
			String nameCn,
			String remark){
		
		return possessorSvc.editPossessor(numberCode ,nameCn ,remark);
	}
	
	/**
	 *  管理员--资源组--删除资源组
	 */
	@RequestMapping(value="deletePossessor")
	@ResponseBody
	public Map<String ,Object> deletePossessor(String numberCodes){
		return possessorSvc.deletePossessor(numberCodes);
	}
	
	/**
	 *  管理员--资源组--设置资源组资源受限
	 */
	@RequestMapping(value="setPossessorResourceFlag")
	@ResponseBody
	public Map<String ,Object> setPossessorResourceFlag(String numberCode ,String resourceType){
		return possessorSvc.setPossessorResourceFlag(numberCode ,resourceType);
	}
	
	/**
	 *  管理员--资源组--添加预约商品给资源组
	 */
	@RequestMapping(value="addPossessorGroupTitle")
	@ResponseBody
	public Map<String ,Object> addPossessorGroupTitle(
			String possessor,
			String possessorResource){
		return possessorSvc.setPossessorResource(possessor, possessorResource, "groupTitles", null);
	}
	
	/**
	 *  管理员--资源组--从资源组删除预约商品
	 */
	@RequestMapping(value="deletePossessorGroupTitle")
	@ResponseBody
	public Map<String ,Object> deletePossessorGroupTitle(
			String possessor,
			String possessorResource){
		return possessorSvc.setPossessorResource(possessor, possessorResource,"groupTitles", null);
	}
	
	/**
	 *  管理员--资源组--添加产品给资源组
	 */
	@RequestMapping(value="addPossessorOrderTkview")
	@ResponseBody
	public Map<String ,Object> addPossessorOrderTkview(
			String possessor,
			String possessorResource){
		return possessorSvc.setPossessorResource(possessor, possessorResource, "orderTkviews", null);
	}
	
	/**
	 *  管理员--资源组--从资源组删除产品
	 */
	@RequestMapping(value="deletePossessorOrderTkview")
	@ResponseBody
	public Map<String ,Object> deletePossessorOrderTkview(
			String possessor,
			String possessorResource){
		return possessorSvc.setPossessorResource(possessor, possessorResource,"orderTkviews", null);
	}
	
	/**
	 *  管理员--资源组--添加渠道给资源组
	 */
	@RequestMapping(value="addPossessorChannel")
	@ResponseBody
	public Map<String ,Object> addPossessorChannel(
			String possessor,
			String possessorResource,
			String channelNameCns){
		return possessorSvc.setPossessorResource(possessor, possessorResource ,"channels", channelNameCns);
	}
	
	/**
	 *  管理员--资源组--添加单品给资源组
	 */
	@RequestMapping(value="deletePossessorChannel")
	@ResponseBody
	public Map<String ,Object> deletePossessorChannel(
			String possessor,
			String possessorResource,
			String channelNameCns){
		return possessorSvc.setPossessorResource(possessor, possessorResource, "channels" ,channelNameCns);
	}
	
	/**
	 * 下拉框--获取资源组列表
	 */
	@RequestMapping(value="getPossessorList")
	@ResponseBody
	public Map<String,Object> getPossessorList(String q){
		Map<String,Object> results = possessorSvc.getPossessorList(q);
		return results;
	}
}
