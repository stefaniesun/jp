package xyz.svc.main.imp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.Product;
import xyz.model.main.ProductHotel;
import xyz.model.main.ProductScenic;
import xyz.svc.main.ProductSvc;
import xyz.util.StringUtil;
import xyz.util.UUIDUtil;

@Service
public class ProductSvcImp implements ProductSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryProductList(String nameCn, int offset, int pagesize) {

		String hql=" from Product where 1=1 ";
		if(!"".equals(nameCn)&&nameCn!=null){
			hql+=" and nameCn like '%"+nameCn+"%'";
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

	@Override
	public Map<String, Object> addProduct(String name, String type, BigDecimal price, int stock) {
		Product product =new Product();
		product.setNumberCode(UUIDUtil.getUUIDStringFor32());
		product.setName(name);
		product.setType(type);
		product.setPrice(price);
		product.setStock(stock);
		commonDao.save(product);
		return ReturnUtil.returnMap(1, null);
	
	}

}
