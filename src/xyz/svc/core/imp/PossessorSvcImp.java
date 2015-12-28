package xyz.svc.core.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.JSON;
import xyz.filter.ReturnUtil;
import xyz.model.base.Possessor;
import xyz.svc.core.PossessorSvc;
import xyz.util.ListNumberCode;
import xyz.util.StringTool;
import xyz.util.UUIDUtil;

@Service
public class PossessorSvcImp  implements PossessorSvc{
	
	@Autowired
	CommonDao commonDao;

	@Override
	public Map<String, Object> queryPossessorList(
			int offset, 
			int pagesize,
			String numberCode, 
			String nameCn) {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.number_code as numberCode, p.name_cn as nameCn, p.decide_str as decideStr, p.remark as remark ,p.add_date as addDate, p.alter_date as alterDate from possessor p where 1 = 1");
		if(numberCode != null && !"".equals(numberCode)){
			sql.append(" and p.number_code = '"+numberCode+"'");
		}
		if(nameCn != null && !"".equals(nameCn)){
			sql.append(" and p.name_cn like '%"+nameCn+"%'");
		}
		
		String countSql = "select count(tt.numberCode) from ("+sql.toString()+")tt";
		Query countQuery = commonDao.getSqlQuery(countSql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		SQLQuery query = commonDao.getSqlQuery(sql.toString());
		query.addScalar("numberCode")
		.addScalar("nameCn")
		.addScalar("decideStr")
		.addScalar("remark")
		.addScalar("addDate")
		.addScalar("alterDate")
		.setResultTransformer(Transformers.aliasToBean(Possessor.class));
		
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<Possessor> possessorList = query.list();
		
		/*
		 * 如果需要在前台展示 单品信息 请开启一下代码 谢谢!
		 * */
//		List<String> tempNumberCodes = new ArrayList<String>();
//		for(Possessor p : possessorList){
//			tempNumberCodes.add(p.getNumberCode());
//		}
//		
//		String sql_tkview = "SELECT t.name_cn,pt.possessor FROM tkview t LEFT JOIN possessor_tkview pt ON t.number_code = pt.tkview where 1 = 1 and pt.possessor in ("+StringTool.listToSqlString(tempNumberCodes)+") GROUP BY pt.possessor ";
//		@SuppressWarnings("unchecked")
//		List<Object[]> list_tkview = commonDao.getSqlQuery(sql_tkview).list();
//		for(Possessor p : possessorList){
//			for(Object[] objs : list_tkview){
//				objs[0] = objs[0] == null?"":objs[0];//判空处理
//				objs[1] = objs[1] == null?"":objs[1];
//				if(p.getNumberCode().equals(objs[1].toString())){
//					p.setField(objs[0].toString());
//				}
//			}
//		}
		
		Map<String,Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total",count);
		mapContent.put("rows",possessorList);
		return ReturnUtil.returnMap(1,mapContent);
	}

	@Override
	public Map<String, Object> queryPossessorListByInOrNotInPossessorNumberCodes(
			int offset, 
			int pagesize,
			String inPossessorNumberCodes,
			String numberCode, 
			String nameCn,
			boolean flag) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.number_code as numberCode, t.name_cn as nameCn from possessor t where 1=1");
		String not = flag?"":" not ";
		sql.append(" and t.number_code "+not+" in ("+StringTool.StrToSqlString(inPossessorNumberCodes)+")");
		if(numberCode != null && !"".equals(numberCode)){
			sql.append(" and t.number_code = '"+numberCode+"'");
		}
		if(nameCn != null && !"".equals(nameCn)){
			sql.append(" and t.name_cn like '%"+nameCn+"%'");
		}
		
		String countSql = "select count(tt.numberCode) from ("+sql.toString()+") tt";
		SQLQuery countQuery = commonDao.getSqlQuery(countSql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		SQLQuery query = commonDao.getSqlQuery(sql.toString());
		query.addScalar("numberCode")
		.addScalar("nameCn")
		.setResultTransformer(Transformers.aliasToBean(Possessor.class));
		
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		
		@SuppressWarnings("unchecked")
		List<Possessor> possessorList = query.list();
		
		Map<String,Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total",count);
		mapContent.put("rows",possessorList);
		
		return ReturnUtil.returnMap(1, mapContent);
	}

	@Override
	public Map<String, Object> getPossessor(String numberCode) {
		
		Possessor possessor = (Possessor)commonDao.getObjectByUniqueCode("Possessor", "numberCode", numberCode);

		return ReturnUtil.returnMap(1,possessor);
	}
	
	
	@Override
	public Map<String, Object> addPossessor(
			String nameCn,
			String remark) {
		Possessor possessor = new Possessor();
		
		possessor.setAddDate(new Date());
		possessor.setNumberCode(UUIDUtil.getUUIDStringFor32());
		possessor.setAlterDate(new Date());
		possessor.setNameCn(nameCn);
		possessor.setRemark(remark);
		commonDao.save(possessor);
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> editPossessor(
			String numberCode, 
			String nameCn,
			String remark) {
		
		Possessor possessor = (Possessor)commonDao.getObjectByUniqueCode("Possessor", "numberCode", numberCode);
		if(nameCn != null && !"".equals(nameCn)){
			possessor.setNameCn(nameCn);
		}
		possessor.setRemark(remark);
		possessor.setAlterDate(new Date());
		commonDao.update(possessor);
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> deletePossessor(String numberCodes) {
		String deletePossessorSql = "delete from possessor where number_code in ("+StringTool.StrToSqlString(numberCodes)+")";
		commonDao.getSqlQuery(deletePossessorSql).executeUpdate();
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> setPossessorResource(
			String numberCode,
			String resources,
			String resourceType,
			String channelNameCns) {
		Possessor possessor = (Possessor)commonDao.getObjectByUniqueCode("Possessor", "numberCode", numberCode);
		
		if(possessor == null){
			return ReturnUtil.returnMap(0,"资源组有误！");
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> decideMap = JSON.toObject(possessor.getDecideStr(), Map.class);
		Set<String> resourceList = new HashSet<String>();
		if(resources !=null && !"".equals(resources)){
			for(String t : resources.split(",")){
				resourceList.add(t);
			}
		}
		
		if("channels".equals(resourceType)){
			if(channelNameCns != null && !"".equals(channelNameCns)){
				Set<String> channelNameCnList = new HashSet<String>();
				for(String t : channelNameCns.split(",")){
					channelNameCnList.add(t);
				}
				decideMap.put("channelNameCns", channelNameCnList);
			}
		}
		
		decideMap.put(resourceType, resourceList);
		possessor.setDecideStr(JSON.toJosn(decideMap));
		possessor.setAlterDate(new Date());
		commonDao.update(possessor);
		return ReturnUtil.returnMap(1,null);
	}
	
	
	@Override
	public Map<String, Object> setPossessorResourceFlag(String numberCode ,String resourceType) {
		Possessor possessor = (Possessor)commonDao.getObjectByUniqueCode("Possessor", "numberCode", numberCode);
		
		if(possessor == null){
			return ReturnUtil.returnMap(0,"资源组有误！");
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> decideMap = JSON.toObject(possessor.getDecideStr()==null||"".equals(possessor.getDecideStr())?"{}":possessor.getDecideStr(), Map.class);
		if("Channel".equals(resourceType)){
			if("1".equals(decideMap.get("channelFlag"))){
				decideMap.put("channelFlag", "0");
				decideMap.put("channels",new ArrayList<String>());
				decideMap.put("channelNameCns",new ArrayList<String>());
			}else{
				decideMap.put("channelFlag", "1");
			}
		}else if("GroupTitle".equals(resourceType)){
			if("1".equals(decideMap.get("groupTitleFlag"))){
				decideMap.put("groupTitleFlag", "0");
				decideMap.put("groupTitles",new ArrayList<String>());
			}else{
				decideMap.put("groupTitleFlag", "1");
			}
		}else if("OrderTkview".equals(resourceType)){
			if("1".equals(decideMap.get("orderTkviewFlag"))){
				decideMap.put("orderTkviewFlag", "0");
				decideMap.put("orderTkviews",new ArrayList<String>());
			}else{
				decideMap.put("orderTkviewFlag", "1");
			}
		}else{
			return ReturnUtil.returnMap(0,"资源类型有误！");
		}
		
		possessor.setDecideStr(JSON.toJosn(decideMap));
		possessor.setAlterDate(new Date());
		commonDao.update(possessor);
		return ReturnUtil.returnMap(1,null);
	
	}
	
	
	@Override
	public Map<String, Object> getPossessorList(String q) {

		String sql = "SELECT p.number_code AS value,p.name_cn AS text FROM possessor p";
		if(q!=null && !"".equals(q)){
			sql += " and p.name_cn like '%"+q+"%'";
		}
		
		SQLQuery query = commonDao.getSqlQuery(sql);
		query.addScalar("value").
		addScalar("text").
		setResultTransformer(Transformers.aliasToBean(ListNumberCode.class));
		
		query.setMaxResults(100);
		
		@SuppressWarnings("unchecked")
		List<ListNumberCode> results = query.list();
		return ReturnUtil.returnMap(1, results);
	}
}
