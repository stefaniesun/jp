package xyz.ctrl.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.model.security.SecurityPosition;
import xyz.svc.security.AdminPositionSvc;

@Controller
@RequestMapping(value="/AdminPositionWS")
public class AdminPositionWS{
	
	@Autowired
	private AdminPositionSvc adminPositionSvc;
	
	/**
	 * 管理--岗位--查询岗位列表
	 */
	@RequestMapping(value="queryPositionList")
	@ResponseBody
	public Map<String, Object> queryPositionList(
			int page,
			int rows,
			String nameCn){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return adminPositionSvc.queryPositionList(offset, pagesize,nameCn);
	}
	
	/**
	 * 管理--岗位--新增岗位
	 */
	@RequestMapping(value="addPosition")
	@ResponseBody
	public Map<String, Object> addPosition(
			String numberCode,
			String nameCn,
			String remark,
			int priority){
		SecurityPosition securityPosition = new SecurityPosition();
		securityPosition.setNumberCode(numberCode);
		securityPosition.setNameCn(nameCn);
		securityPosition.setRemark(remark);
		securityPosition.setPriority(priority);
		return adminPositionSvc.addPosition(securityPosition);
	}
	
	/**
	 * 管理--岗位--编辑岗位
	 */
	@RequestMapping(value="editPosition")
	@ResponseBody
	public Map<String, Object> editPosition(
			String numberCode,
			String nameCn,
			String remark){
		return adminPositionSvc.editPosition(numberCode,nameCn, remark);
	}
	
	@RequestMapping(value="deletePosition")
	@ResponseBody
	public Map<String, Object> deletePosition(
			String positions){
		return adminPositionSvc.deletePosition(positions);
	}
	
	@RequestMapping(value="queryPositionFunctionTrueList")
	@ResponseBody
	public Map<String, Object> queryPositionFunctionTrueList(
			String position){
		return adminPositionSvc.queryPositionFunctionList(true,position);
	}
	
	@RequestMapping(value="queryPositionFunctionFalseList")
	@ResponseBody
	public Map<String, Object> queryPositionFunctionFalseList(
			String position){
		return adminPositionSvc.queryPositionFunctionList(false,position);
	}
	
	@RequestMapping(value="addPositionFunction")
	@ResponseBody
	public Map<String, Object> addPositionFunction(
			String position,
			String functions){
		return adminPositionSvc.addPositionFunction(position, functions);
	}
	
	@RequestMapping(value="deletePositionFunction")
	@ResponseBody
	public Map<String, Object> deletePositionFunction(
			String position,
			String functions){
		return adminPositionSvc.deletePositionFunction(position, functions);
	}
	
	@RequestMapping(value="queryPositionButtonTrueList")
	@ResponseBody
	public Map<String, Object> queryPositionButtonTrueList(
			String position){
		return adminPositionSvc.queryPositionButtonList(true,position);
	}
	
	@RequestMapping(value="queryPositionButtonFalseList")
	@ResponseBody
	public Map<String, Object> queryPositionButtonFalseList(
			String position){
		return adminPositionSvc.queryPositionButtonList(false,position);
	}
	
	@RequestMapping(value="addPositionButton")
	@ResponseBody
	public Map<String, Object> addPositionButton(
			String position,
			String buttons){
		return adminPositionSvc.addPositionButton(position, buttons);
	}
	
	@RequestMapping(value="deletePositionButton")
	@ResponseBody
	public Map<String, Object> deletePositionButton(
			String position,
			String buttons){
		return adminPositionSvc.deletePositionButton(position, buttons);
	}
}
