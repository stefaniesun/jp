package xyz.svc.security.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.exception.MyExceptionForRole;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityFunction;
import xyz.model.security.SecurityPosition;
import xyz.model.security.SecurityPositionButton;
import xyz.svc.security.AdminPositionSvc;
import xyz.util.StringTool;

@Service
public class AdminPositionSvcImp  implements AdminPositionSvc{
	
	@Resource
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryPositionList(int offset,int pagesize,String nameCn) {
		String cPosition = MyRequestUtil.getSecurityLogin().getPosition();
		SecurityPosition securityPosition = (SecurityPosition)commonDao.getObjectByUniqueCode("SecurityPosition","numberCode",cPosition);
		if(securityPosition==null){
			throw new MyExceptionForRole("岗位不存在，请联系系统管理员！");
		}
		int priority = securityPosition.getPriority();
		
		StringBuffer hql = new StringBuffer();
		hql.append("from SecurityPosition t where 1=1 and t.priority > "+priority);
		if(nameCn != null && !"".equals(nameCn)){
			hql.append(" and t.nameCn like '%"+nameCn+"%'");
		}
		
		Query query = commonDao.getQuery(hql.toString());
		int count = query.list().size();
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<SecurityPosition> positionList = query.list();
		commonDao.clear();
		
		for(SecurityPosition position:positionList){
			String sql = "select count(t.iidd) from security_user t where t.position = '"+position.getNumberCode()+"'";
			Number number = (Number)commonDao.getSqlQuery(sql).uniqueResult();
			int countUser = number==null?0:number.intValue();
			position.setCountUser(countUser);
		}
		
		Map<String,Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total",count);
		mapContent.put("rows",positionList);

		return ReturnUtil.returnMap(1, mapContent);
	}
	
	@Override
	public Map<String, Object> addPosition(SecurityPosition securityPosition) {
		String cPosition = MyRequestUtil.getSecurityLogin().getPosition();
		SecurityPosition securityPositionT = (SecurityPosition)commonDao.getObjectByUniqueCode("SecurityPosition","numberCode",cPosition);
		if(securityPositionT==null){
			throw new MyExceptionForRole("岗位不存在，请联系系统管理员！");
		}
		
		int priority = securityPositionT.getPriority();
		if(securityPosition.getPriority()<=priority){
			throw new MyExceptionForRole("您所创建的岗位级别必须大于或等于"+priority);
		}
		
		Date date = new Date();
		securityPosition.setAddDate(date);
		securityPosition.setAlterDate(date);
		commonDao.save(securityPosition);
		return ReturnUtil.returnMap(1, null);
	}
	
	@Override
	public Map<String, Object> editPosition(
			String numberCode,
			String nameCn,
			String remark) {
		String hql = "from SecurityPosition sp where sp.numberCode = '"+numberCode+"'";
		SecurityPosition securityPosition = (SecurityPosition)commonDao.queryUniqueByHql(hql);
		
		securityPosition.setNameCn(nameCn);
		securityPosition.setRemark(remark);
		securityPosition.setAlterDate(new Date());
		commonDao.update(securityPosition);
		return ReturnUtil.returnMap(1, null);
	}
	
	@Override
	public Map<String, Object> deletePosition(String positions) {
		String [] positionStrs = positions.split(",");
		for(int i = 0;i<positionStrs.length;i++) {
			String numberCode = positionStrs[i];
			String hql = "from SecurityPosition sp where sp.numberCode = '"+numberCode+"'";
			SecurityPosition securityPosition = (SecurityPosition)commonDao.queryUniqueByHql(hql);
			
			commonDao.delete(securityPosition);
			hql = "delete from SecurityPositionButton s where s.position = '"+numberCode+"'";
			commonDao.updateByHql(hql);
			hql = "delete from SecurityLogin s where s.position = '"+numberCode+"'";
			commonDao.updateByHql(hql);
		}
		return ReturnUtil.returnMap(1, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryPositionFunctionList(
			boolean isContain,
			String position){
		String currentUserPosition = MyRequestUtil.getSecurityLogin().getPosition();
		
		String hql = "select t.button from SecurityPositionButton t where t.position = '"+currentUserPosition+"'";
		List<String> currentUserButtons = commonDao.queryByHql(hql);
		
		hql = "select t.button from SecurityPositionButton t where t.position = '"+position+"'";
		List<String> currentPositionButtons = commonDao.queryByHql(hql);
		
		hql = "select t.function from SecurityApi t where t.isDecide = 1 and t.buttonCode in ("+StringTool.listToSqlString(currentUserButtons)+")";
		List<String> currentUserFunctions = commonDao.queryByHql(hql);
		
		hql = "select t.function from SecurityApi t where t.isDecide = 1 and t.buttonCode in ("+StringTool.listToSqlString(currentPositionButtons)+")";
		List<String> currentPositionFunctions = commonDao.queryByHql(hql);
		
		Set<String> functions = new HashSet<String>();
		if(isContain){
			for(String f1 : currentUserFunctions){
				for(String f2 : currentPositionFunctions){
					if(f1.equals(f2)){
						functions.add(f1);
						break;
					}
				}
			}
		}else{
			for(String f1 : currentUserFunctions){
				boolean flag = true;
				for(String f2 : currentPositionFunctions){
					if(f1.equals(f2)){
						flag = false;
						break;
					}
				}
				if(flag){
					functions.add(f1);
				}
			}
		}
		
		hql = "from SecurityFunction t where t.numberCode in ("+StringTool.listToSqlString(functions)+") order by t.groupCn";
		List<SecurityFunction> securityFunctionList = commonDao.queryByHql(hql);
		
		Map<String, Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total",securityFunctionList.size());
		mapContent.put("rows", securityFunctionList);
		return ReturnUtil.returnMap(1, mapContent);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> addPositionFunction(String position,String functions) {
		String currentUserPosition = MyRequestUtil.getSecurityLogin().getPosition();
		
		String hql = "select t.button from SecurityPositionButton t where t.position = '"+currentUserPosition+"'";
		List<String> currentUserButtons = commonDao.queryByHql(hql);
		
		hql = "select t.buttonCode from SecurityApi t where t.isDecide = 1 and t.function in ("+StringTool.StrToSqlString(functions)+")";
		List<String> currentFunctionButtons = commonDao.queryByHql(hql);
		
		Set<String> newButtons= new HashSet<String>();
		for(String t1 : currentUserButtons){
			for(String t2 : currentFunctionButtons){
				if(t1.equals(t2)){
					newButtons.add(t1);
				}
			}
		}
		
		for(String button : newButtons){
			if(button==null || "".equals(button)){
				continue;
			}
			hql = "from SecurityPositionButton t where t.position = '"+position+"' and t.button = '"+button+"'";
			if(commonDao.queryByHql(hql).size()>0){
				continue;
			}
			SecurityPositionButton securityPositionButton = new SecurityPositionButton();
			securityPositionButton.setPosition(position);
			securityPositionButton.setButton(button);
			commonDao.save(securityPositionButton);
		}
		return ReturnUtil.returnMap(1, null);
	}
	
	@Override
	public Map<String, Object> deletePositionFunction(String position,String functions) {
		String hql = "select t.buttonCode from SecurityApi t where t.isDecide = 1 and t.function in ("+StringTool.StrToSqlString(functions)+")";
		@SuppressWarnings("unchecked")
		List<String> currentFunctionButtons = commonDao.queryByHql(hql);
		
		hql = "delete from SecurityPositionButton t where t.position = '"+position+"' and t.button in("+StringTool.listToSqlString(currentFunctionButtons)+")";
		commonDao.updateByHql(hql);
		return ReturnUtil.returnMap(1, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryPositionButtonList(
			boolean isContain,
			String position){
		String currentUserPosition = MyRequestUtil.getSecurityLogin().getPosition();
		
		String hql = "select t.button from SecurityPositionButton t where t.position = '"+currentUserPosition+"'";
		List<String> currentUserButtons = commonDao.queryByHql(hql);
		
		hql = "select t.button from SecurityPositionButton t where t.position = '"+position+"'";
		List<String> currentPositionButtons = commonDao.queryByHql(hql);
		
		Set<String> buttons = new HashSet<String>();
		if(isContain){
			for(String f1 : currentUserButtons){
				for(String f2 : currentPositionButtons){
					if(f1.equals(f2)){
						buttons.add(f1);
						break;
					}
				}
			}
		}else{
			for(String f1 : currentUserButtons){
				boolean flag = true;
				for(String f2 : currentPositionButtons){
					if(f1.equals(f2)){
						flag = false;
						break;
					}
				}
				if(flag){
					buttons.add(f1);
				}
			}
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(" t1.button_code AS buttonCode,");
		sql.append(" t2.name_cn AS functionNameCn,");
		sql.append(" GROUP_CONCAT(t1.name_cn) AS nameCn");
		sql.append(" FROM security_api t1");
		sql.append(" LEFT JOIN security_function t2 ON t1.`function` = t2.number_code");
		sql.append(" WHERE t1.is_decide = 1");
		sql.append(" and t1.button_code in ("+StringTool.listToSqlString(buttons)+")");
		sql.append(" GROUP BY t1.button_code order by functionNameCn");
		SQLQuery query = commonDao.getSqlQuery(sql.toString());
		query.addScalar("buttonCode").
		addScalar("functionNameCn").
		addScalar("nameCn").
		setResultTransformer(Transformers.aliasToBean(SecurityApi.class));
		
		List<SecurityApi> securityApiList = query.list();
		
		Map<String, Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total", securityApiList.size());
		mapContent.put("rows", securityApiList);
		return ReturnUtil.returnMap(1, mapContent);
	}
	
	@Override
	public Map<String, Object> addPositionButton(String position,String buttons) {
		if(buttons==null || "".equals(buttons)){
			return ReturnUtil.returnMap(1, "您未选中任何操作!");
		}
		String currentUserPosition = MyRequestUtil.getSecurityLogin().getPosition();
		
		String hql = "select t.button from SecurityPositionButton t where t.position = '"+currentUserPosition+"'";
		@SuppressWarnings("unchecked")
		List<String> currentUserButtons = commonDao.queryByHql(hql);
		
		Set<String> newButtons= new HashSet<String>();
		for(String t1 : currentUserButtons){
			for(String t2 : buttons.split(",")){
				if(t1.equals(t2)){
					newButtons.add(t1);
				}
			}
		}
		
		for(String button : newButtons){
			if(button==null || "".equals(button)){
				continue;
			}
			hql = "from SecurityPositionButton t where t.position = '"+position+"' and t.button = '"+button+"'";
			if(commonDao.queryByHql(hql).size()>0){
				continue;
			}
			SecurityPositionButton securityPositionButton = new SecurityPositionButton();
			securityPositionButton.setPosition(position);
			securityPositionButton.setButton(button);
			commonDao.save(securityPositionButton);
		}
		return ReturnUtil.returnMap(1, null);
	}
	
	@Override
	public Map<String, Object> deletePositionButton(String position,String buttons) {
		String hql = "delete from SecurityPositionButton t where t.position = '"+position+"' and t.button in("+StringTool.StrToSqlString(buttons)+")";
		commonDao.updateByHql(hql);
		return ReturnUtil.returnMap(1, null);
	}
}
