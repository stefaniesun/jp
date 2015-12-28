package xyz.svc.buyer.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.ProductHotel;
import xyz.svc.buyer.BuyerHotelSvc;

@Service
public class BuyerHotelSvcImp implements BuyerHotelSvc {

	
	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryHotelList(String nameCn, String provider,
			int offset, int pagesize) {
		String hql=" from ProductHotel where 1=1 ";
		if(!"".equals(nameCn)&&nameCn!=null){
			hql+=" and nameCn like '%"+nameCn+"%'";
		}
		
		if(!"".equals(provider)&&provider!=null){
			hql+=" and provider = '"+provider+"'";
		}
		
		String countHql = "select count(numberCode) "+hql;
		Query countQuery = commonDao.getQuery(countHql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<ProductHotel> productHotelList=query.list();
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",productHotelList);
		return ReturnUtil.returnMap(1, mapContent);
	}

}
