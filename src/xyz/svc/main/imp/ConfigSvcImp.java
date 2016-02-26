package xyz.svc.main.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.Config;
import xyz.model.main.Product;
import xyz.svc.main.ConfigSvc;
import xyz.util.UUIDUtil;

@Service
public class ConfigSvcImp implements ConfigSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryConfigList(int offset, int pagesize) {

		String hql=" from Config";

		
		String countHql = "select count(numberCode) "+hql;
		Query countQuery = commonDao.getQuery(countHql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<Config> configs=query.list();
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",configs);
		return ReturnUtil.returnMap(1, mapContent);
	
	}

	@Override
	public Map<String, Object> addConfig(String key, String name, String value) {
		Config config=new Config();
		config.setNumberCode(UUIDUtil.getUUIDStringFor32());
		config.setKey(key);
		config.setName(name);
		config.setValue(value);
		commonDao.save(config);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> editConfig(String numberCode, String key, String name, String value) {
		Config config=(Config) commonDao.getObjectByUniqueCode("Config", "numberCode", numberCode);
		if(config==null){
			return ReturnUtil.returnMap(0,"对象不存在");
		}
		config.setKey(key);
		config.setName(name);
		config.setValue(value);
		commonDao.update(config);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> deleteConfig(String numberCode) {
		Config config=(Config) commonDao.getObjectByUniqueCode("Config", "numberCode", numberCode);
		if(config==null){
			return ReturnUtil.returnMap(0,"对象不存在");
		}
		commonDao.delete(config);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> getConfig(String numberCode) {
		Config config=(Config) commonDao.getObjectByUniqueCode("Config", "numberCode", numberCode);
		if(config==null){
			return ReturnUtil.returnMap(0,"对象不存在");
		}
		return ReturnUtil.returnMap(1, config);
	}

}
