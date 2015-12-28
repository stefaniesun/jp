package xyz.svc.core;

import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface PossessorSvc{
	
	public Map<String ,Object> queryPossessorList(
			int offset,
			int pagesize,
			String numberCode,
			String nameCn);
	
	public Map<String ,Object> getPossessor(String numberCode);
	
	public Map<String ,Object> queryPossessorListByInOrNotInPossessorNumberCodes(
			int offset,
			int pagesize,
			String inPossessorNumberCodes,
			String numberCode,
			String nameCn,
			boolean flag);
	
	
	public Map<String ,Object> addPossessor(
			String nameCn,
			String remark);
	
	public Map<String ,Object> editPossessor(
			String numberCode,
			String nameCn,
			String remark);
	
	public Map<String ,Object> deletePossessor(String numberCodes);
	
	public Map<String ,Object> setPossessorResourceFlag(String numberCode ,String resourceType);
	
	public Map<String ,Object> setPossessorResource(
			String numberCode,
			String resources,
			String resourceType,
			String channelNameCns);
	
	public Map<String ,Object> getPossessorList(String q);
	
}
