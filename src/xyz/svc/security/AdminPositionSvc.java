package xyz.svc.security;

import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.security.SecurityPosition;


@Service
public interface AdminPositionSvc {
	
	public Map<String, Object> queryPositionList(int offset,int pagesize,String nameCn);
	
	public Map<String, Object> addPosition(SecurityPosition securityPosition);
	
	public Map<String, Object> editPosition(
			String numberCode,
			String nameCn,
			String remark);
	
	public Map<String, Object> deletePosition(String positions);
	
	
	public Map<String, Object> queryPositionFunctionList(
			boolean isContain,
			String position);
	
	public Map<String, Object> addPositionFunction(String position,String functions);
	
	public Map<String, Object> deletePositionFunction(String position,String functions);
	
	public Map<String, Object> queryPositionButtonList(
			boolean isContain,
			String position);
	
	public Map<String, Object> addPositionButton(String position,String buttons);
	
	public Map<String, Object> deletePositionButton(String position,String buttons);
	
}
