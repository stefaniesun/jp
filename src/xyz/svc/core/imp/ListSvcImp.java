package xyz.svc.core.imp;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;
import xyz.svc.core.ListSvc;
import xyz.util.ListNumberCode;



@Service
public class ListSvcImp  implements ListSvc{
	
	@Autowired()
	CommonDao commonDao;
	
	@Autowired
	RmiUtil rmiUtil;
	
	@Override
	public Map<String, Object> getSecurityUserList(){
		String sql = "SELECT p.username AS value,p.nick_name AS text FROM security_user p ";
		SQLQuery query = commonDao.getSqlQuery(sql);
		query.addScalar("value").
		addScalar("text").
		setResultTransformer(Transformers.aliasToBean(ListNumberCode.class));
		
		@SuppressWarnings("unchecked")
		List<ListNumberCode> results = query.list();
		return ReturnUtil.returnMap(1, results);
	}

	@Override
	public Map<String, Object> getProviderList(String providerType) {
		if(!"".equals(providerType)&&providerType!=null){
				String sql="select name_cn AS text, number_code AS value from provider where type='"+providerType+"' ";
				SQLQuery query=commonDao.getSqlQuery(sql);
				query.addScalar("text").addScalar("value").
				setResultTransformer(Transformers.aliasToBean(ListNumberCode.class));
				@SuppressWarnings("unchecked")
				List<ListNumberCode> providerList=query.list();
				return ReturnUtil.returnMap(1, providerList);
		}else{
			return ReturnUtil.returnMap(0, "供应商类别出错,请联系管理员！");
		}
		
		
	}
}

