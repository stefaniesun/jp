package xyz.svc.security.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.security.TokenInfo;
import xyz.svc.security.AdminOtpSvc;

@Service
public class AdminOtpSvcImp implements AdminOtpSvc{

	@Resource
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryOtpList(
							int offset,
							int pagesize,
							String flag,
							String tokenNum
							) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.iidd AS iidd,");
		sql.append("t.token_num AS tokenNum,");
		sql.append("t.authkey AS authkey,");
		sql.append("COUNT(su.username) AS countUser,");
		sql.append("GROUP_CONCAT(su.username) AS usernames,");
		sql.append("t.add_date AS addDate,");
		sql.append("t.alter_date AS alterDate");
		sql.append(" FROM token_info t LEFT JOIN security_user su ON t.token_num = su.token_num WHERE 1=1");
		if(tokenNum != null && !"".equals(tokenNum)){
			sql.append(" and t.token_num = '"+tokenNum+"'");
		}
		sql.append(" GROUP BY t.token_num HAVING 1=1");
		if(flag != null && !"".equals(flag)){
			if("0".equals(flag)){
				sql.append(" and countUser = 0");
			}else{
				sql.append(" and countUser > 0");
			}
		}
		
		sql.append(" order by alterDate desc");
		
		String countsql = "select count(tt.iidd) from ("+sql.toString()+") tt";
		SQLQuery countQuery = commonDao.getSqlQuery(countsql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		SQLQuery query = commonDao.getSqlQuery(sql.toString());
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		query.addScalar("iidd").
		addScalar("tokenNum").
		addScalar("authkey").
		addScalar("countUser").
		addScalar("usernames").
		addScalar("addDate").
		addScalar("alterDate").
		setResultTransformer(Transformers.aliasToBean(TokenInfo.class));
		
		@SuppressWarnings("unchecked")
		List<TokenInfo> tokenInfoList = query.list();
		
		Map<String,Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total",count);
		mapContent.put("rows",tokenInfoList);

		return ReturnUtil.returnMap(1,mapContent);
	}
	
	@Override
	public Map<String,Object> addOtp(TokenInfo tokenInfo) {
		commonDao.save(tokenInfo);
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String,Object> deleteOtp(String otps) {
		String [] ids = otps.split(",");
		for(String id : ids){
			int iidd = Integer.parseInt(id);
			TokenInfo tokenInfo = (TokenInfo)commonDao.getObjectById(TokenInfo.class, iidd);
			commonDao.delete(tokenInfo);
		}
		return ReturnUtil.returnMap(1,null);
	}
}
