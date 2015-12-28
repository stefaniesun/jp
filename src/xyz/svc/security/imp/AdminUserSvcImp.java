package xyz.svc.security.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.exception.MyExceptionForRole;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.security.SecurityPosition;
import xyz.model.security.SecurityUser;
import xyz.model.security.TokenInfo;
import xyz.svc.security.AdminUserSvc;
import xyz.util.EncryptionUtil;
import xyz.util.ListNumberCode;
import xyz.util.StringTool;

@Service
public class AdminUserSvcImp  implements AdminUserSvc{
	
	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryUserList(
			int offset,
			int pagesize,
			String username,
			String nickName,
			String position,
			String possessor) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT su.add_date AS addDate,");
		sql.append("su.alter_date AS alterDate,");
		sql.append("su.enabled AS enabled,");
		sql.append("su.is_repeat AS isRepeat,");
		sql.append("su.nick_name AS nickName,");
		sql.append("su.token_num AS tokenNum,");
		sql.append("su.username AS username,");
		sql.append("su.position AS position,");
		sql.append("su.possessor AS possessor,");
		sql.append(" (SELECT p.name_cn from possessor p WHERE p.number_code = su.possessor ) as possessorNameCn ,");
		sql.append(" (SELECT sp.name_cn FROM security_position sp WHERE sp.number_code = su.position) AS positionNameCn");
		sql.append(" FROM security_user su WHERE 1=1");

		if(username != null && !"".equals(username)){
			sql.append(" and su.username = '"+username+"'");
		}
		if(position != null && !"".equals(position)){
			sql.append(" and su.position in ("+StringTool.StrToSqlString(position)+")");
		}
		if(nickName != null && !"".equals(nickName)){
			sql.append(" and su.nick_name like '%"+nickName+"%'");
		}
		if(possessor != null && !"".equals(possessor)){
			sql.append(" and su.possessor in ("+StringTool.StrToSqlString(possessor)+")");
		}
		sql.append(" order by su.username desc");
		
		String countSQL = "select count(ttt.username) from ("+sql.toString()+") ttt";
		SQLQuery countQuery = commonDao.getSqlQuery(countSQL);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		SQLQuery query = commonDao.getSqlQuery(sql.toString());
		
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		query.addScalar("addDate").
		addScalar("alterDate").
		addScalar("enabled").
		addScalar("isRepeat").
		addScalar("nickName").
		addScalar("tokenNum").
		addScalar("username").
		addScalar("position").
		addScalar("positionNameCn").
		addScalar("possessor").
		addScalar("possessorNameCn").
		setResultTransformer(Transformers.aliasToBean(SecurityUser.class));
		
		@SuppressWarnings("unchecked")
		List<SecurityUser> userList = query.list();
		
		Map<String,Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total",count);
		mapContent.put("rows",userList);
		return ReturnUtil.returnMap(1,mapContent);
	}
	
	@Override
	public Map<String, Object> addUser(SecurityUser securityUser) {
		Date date = new Date();
		securityUser.setEnabled(0);
		securityUser.setIsRepeat(0);
		securityUser.setTokenNum(null);
		securityUser.setPosition(null);
		securityUser.setAddDate(date);
		securityUser.setAlterDate(date);
		commonDao.save(securityUser);
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> editUser(
			String username,
			String nickName) {
		String hql = "from SecurityUser su where su.username = '"+username+"'";
		SecurityUser securityUser = (SecurityUser)commonDao.queryUniqueByHql(hql);
		securityUser.setNickName(nickName);
		securityUser.setAlterDate(new Date());
		commonDao.update(securityUser);
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> editUserEnabled(String username) {
		String hql = "from SecurityUser su where su.username = '"+username+"'";
		SecurityUser securityUser = (SecurityUser)commonDao.queryUniqueByHql(hql);
		if(securityUser.getEnabled()==0){
			securityUser.setEnabled(1);
		}else{
			securityUser.setEnabled(0);
		}
		securityUser.setAlterDate(new Date());
		commonDao.update(securityUser);
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> deleteUser(String users) {
		String [] userStrs = users.split(",");
		for(int i = 0;i<userStrs.length;i++) {
			String username = userStrs[i];
			String hql = "from SecurityUser su where su.username = '"+username+"'";
			SecurityUser securityUser = (SecurityUser)commonDao.queryUniqueByHql(hql);
			commonDao.delete(securityUser);
			hql = "delete from SecurityLogin s where s.username = '"+securityUser.getUsername()+"'";
			commonDao.updateByHql(hql);
		}
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> setUserPosition(String username,String position) {
		SecurityUser securityUser = (SecurityUser)commonDao.getObjectByUniqueCode("SecurityUser", "username", username);
		if(securityUser==null){
			return ReturnUtil.returnMap(0,"用户有误！");
		}
		
		SecurityPosition securityPosition = (SecurityPosition)commonDao.getObjectByUniqueCode("SecurityPosition", "numberCode", position);
		if(securityPosition==null){
			return ReturnUtil.returnMap(0,"岗位有误！");
		}
		
		SecurityPosition securityPositionC = (SecurityPosition)commonDao.getObjectByUniqueCode("SecurityPosition", "numberCode",MyRequestUtil.getSecurityLogin().getPosition());
		if(securityPositionC==null){
			return ReturnUtil.returnMap(0,"岗位有误！");
		}
		
		if(securityPositionC.getPriority()>=securityPosition.getPriority()){
			return ReturnUtil.returnMap(0,"您只能针对更低级优先级授权！");
		}

		securityUser.setPosition(position);
		securityUser.setAlterDate(new Date());
		commonDao.update(securityUser);
		String hql = "delete from SecurityLogin s where s.username = '"+securityUser.getUsername()+"'";
		commonDao.updateByHql(hql);
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> setUserOtp(String username,String otp) {
		
		if(otp!=null && !"".equals(otp)){
			String hql = "from TokenInfo su where su.tokenNum = '"+otp+"'";
			TokenInfo tokenInfo = (TokenInfo)commonDao.queryUniqueByHql(hql);
			if(tokenInfo==null){
				return ReturnUtil.returnMap(0,"动态令牌不存在！");
			}
		}
		
		String hql = "from SecurityUser su where su.username = '"+username+"'";
		SecurityUser securityUser = (SecurityUser)commonDao.queryUniqueByHql(hql);
		if(securityUser==null){
			return ReturnUtil.returnMap(0,"用户不存在！");
		}
		
		
		Date date = new Date();
		securityUser.setTokenNum(otp);
		securityUser.setAlterDate(date);
		commonDao.update(securityUser);
		
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> getAllPosition() {
		String cPosition = MyRequestUtil.getSecurityLogin().getPosition();
		SecurityPosition securityPosition = (SecurityPosition)commonDao.getObjectByUniqueCode("SecurityPosition","numberCode",cPosition);
		if(securityPosition==null){
			throw new MyExceptionForRole("岗位不存在，请联系系统管理员！");
		}
		int priority = securityPosition.getPriority();
		
		StringBuffer hql = new StringBuffer();
		hql.append("from SecurityPosition t where 1=1 and t.priority > "+priority);
		
		@SuppressWarnings("unchecked")
		List<SecurityPosition> positionList = commonDao.queryByHql(hql.toString());
		return ReturnUtil.returnMap(1,positionList);
	}
	
	@Override
	public List<Map<String, Object>> getUserListForDecideStr(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("su.username AS username,");
		sql.append("su.nick_name AS nickName,");
		sql.append("su.decide_str AS decideStr");
		sql.append(" FROM security_user su WHERE 1=1");
		@SuppressWarnings("unchecked")
		List<Object[]> tt = commonDao.getSqlQuery(sql.toString()).list();
		List<Map<String, Object>> ttt = new ArrayList<Map<String,Object>>();
		for(Object[] t : tt){
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("username",t[0]);
			p.put("nickName",t[1]);
			p.put("decideStr",t[2]);
			ttt.add(p);
		}
		return ttt;
	}
	
	@Override
	public Map<String, Object> getSecurityUserList() {
		String sql = "SELECT p.username AS value,p.nick_name AS text FROM security_user p where 1=1"; 
		SQLQuery query = commonDao.getSqlQuery(sql);
		query.addScalar("value").
		addScalar("text").
		setResultTransformer(Transformers.aliasToBean(ListNumberCode.class));
		
		@SuppressWarnings("unchecked")
		List<ListNumberCode> results = query.list();
		return ReturnUtil.returnMap(1, results);
	}

	@Override
	public Map<String, Object> getSecurityUser(String username) {
		SecurityUser securityUser = (SecurityUser)commonDao.getObjectByUniqueCode("SecurityUser", "username", username);
		return ReturnUtil.returnMap(1, securityUser);
	}
	
	@Override
	public Map<String, Object> setUserPossessor(String username, String possessor) {
		SecurityUser securityUser = (SecurityUser)commonDao.getObjectByUniqueCode("SecurityUser", "username", username);
		if(securityUser==null){
			return ReturnUtil.returnMap(0,"用户有误！");
		}

		securityUser.setPossessor(possessor);
		securityUser.setAlterDate(new Date());
		commonDao.update(securityUser);
		
		return ReturnUtil.returnMap(1,null);
	}


	@Override
	public Map<String, Object> editUserPassword(String username, String password) {
		
		SecurityUser user = (SecurityUser) commonDao.getObjectByUniqueCode("SecurityUser", "username", username);
		user.setPassword(EncryptionUtil.md5(password+"{"+username+"}"));
		commonDao.update(user);
		
		return ReturnUtil.returnMap(1,null);
	}
	
	
	@Override
	public Map<String, Object> getSecurityUserList(String q) {
		String sql = "SELECT p.username AS value,p.nick_name AS text FROM security_user p where 1=1"; 
		if(q != null && !"".equals(q)){
			sql += " and p.nick_name like '%"+q+"%'";
		}
		
		SQLQuery query = commonDao.getSqlQuery(sql);
		query.addScalar("value").
		addScalar("text").
		setResultTransformer(Transformers.aliasToBean(ListNumberCode.class));
		
		@SuppressWarnings("unchecked")
		List<ListNumberCode> results = query.list();
		return ReturnUtil.returnMap(1, results);
	}
}
