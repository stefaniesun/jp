package xyz.svc.main.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.Provider;
import xyz.svc.main.ProviderSvc;
import xyz.util.StringTool;
import xyz.util.UUIDUtil;

@Service
public class ProviderSvcImp implements ProviderSvc {

	@Autowired
	CommonDao commonDao;
	

	@Override
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
		Provider provider =new Provider();
		provider.setNumberCode(UUIDUtil.getUUIDStringFor32());
		provider.setNameCn(nameCn);
		provider.setType(type);
		provider.setLevel(level);
		provider.setLevelSystem(levelSystem);
		provider.setPhone(phone);
		provider.setQq(qq);
		provider.setEmail(email);
		provider.setLinkman(linkman);
		provider.setRegionFirst(regionFirst);
		provider.setRegionSecond(regionSecond);
		provider.setRegionThird(regionThird);
		provider.setAddress(address);
		commonDao.save(provider);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String,Object> queryProviderList(String nameCn, String providerType,
			int offset,
			int pagesize){

		String hql=" from Provider where 1=1  ";
		if(!"".equals(nameCn)&&nameCn!=null){
			hql+=" and nameCn like '%"+nameCn+"%' ";
		}
		if(!"".equals(providerType)&&providerType!=null){
			hql+=" and type = '"+providerType+"' ";
		}
		
		String countHql = "select count(numberCode) "+hql;
		Query countQuery = commonDao.getQuery(countHql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<Provider> productScenicList=query.list();
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",productScenicList);
		return ReturnUtil.returnMap(1, mapContent);
	}


	@Override
	public Map<String, Object> editProvider(String numberCode,
			String nameCn,
			String level,
			String levelSystem, 
			String phone,
			String qq, 
			String email, 
			String linkman,
			String regionFirst,
			String regionSecond, 
			String regionThird, 
			String address) {
		Provider provider=(Provider)commonDao.getObjectByUniqueCode("Provider", "numberCode", numberCode);
		provider.setNameCn(nameCn);
		provider.setLevel(level);
		provider.setLevelSystem(levelSystem);
		provider.setPhone(phone);
		provider.setQq(qq);
		provider.setEmail(email);
		provider.setLinkman(linkman);
		provider.setRegionFirst(regionFirst);
		provider.setRegionSecond(regionSecond);
		provider.setRegionThird(regionThird);
		provider.setAddress(address);
		commonDao.update(provider);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> deleteProvider(String numberCodes) {
		if(!"".equals(numberCodes)&&numberCodes!=null){
			String sql1="delete from product_hotel where provider in ("+StringTool.StrToSqlString(numberCodes)+")";
			commonDao.getSqlQuery(sql1).executeUpdate();
			String sql2="delete from product_scenic where provider in ("+StringTool.StrToSqlString(numberCodes)+")";
			commonDao.getSqlQuery(sql2).executeUpdate();
			String sql3 = "delete from provider where number_code in ("+StringTool.StrToSqlString(numberCodes)+")";
			commonDao.getSqlQuery(sql3).executeUpdate();
			return ReturnUtil.returnMap(1, null);
		}else{
			return ReturnUtil.returnMap(0, "请先选中需要删除的对象！");
		}
	}
}
