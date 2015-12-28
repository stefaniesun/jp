package xyz.svc.main.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.ProductScenic;
import xyz.model.main.Provider;
import xyz.svc.main.ProductScenicSvc;
import xyz.util.StringTool;
import xyz.util.StringUtil;
import xyz.util.UUIDUtil;

@Service
public class ProductScenicSvcImp implements ProductScenicSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryProductScenicList(String nameCn, String provider,
			int offset,
			int pagesize) {
		String hql=" from ProductScenic where 1=1 ";
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
		List<ProductScenic> productScenicList=query.list();
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",productScenicList);
		return ReturnUtil.returnMap(1, mapContent);
	}

	@Override
	public Map<String, Object> addProductScenic(String nameCn, String provider) {
		ProductScenic productscenic =new ProductScenic();
		productscenic.setNameCn(nameCn);
		productscenic.setNumberCode(StringUtil.get_new_product("SE"));
		productscenic.setProvider(provider);
		commonDao.save(productscenic);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> editProductScenic(String numberCode,
			String nameCn, String provider) {
		ProductScenic productscenic=(ProductScenic)commonDao.getObjectByUniqueCode("ProductScenic", "numberCode", numberCode);
		productscenic.setNameCn(nameCn);
		productscenic.setProvider(provider);
		commonDao.update(productscenic);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> deleteProductScenic(String numberCodes) {
		if(!"".equals(numberCodes)&&numberCodes!=null){
			String sql = "delete from product_scenic where number_code in ("+StringTool.StrToSqlString(numberCodes)+")";
			commonDao.getSqlQuery(sql).executeUpdate();
			return ReturnUtil.returnMap(1, null);
		}else{
			return ReturnUtil.returnMap(0, "请先选中需要删除的对象！");
		}
	}

	@Override
	public Map<String, Object> queryProductScenicListForStock(String nameCn,
			String provider, int offset, int pagesize) {
		String hql=" from ProductScenic where 1=1 ";
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
		List<ProductScenic> productScenicList=query.list();
		
		Set<String> productNumberCodeList = new HashSet<String>();
		for(ProductScenic product: productScenicList){
			productNumberCodeList.add(product.getNumberCode());
		}
		String hqlDetail = "from Provider p where p.numberCode in ("+StringTool.listToSqlString(productNumberCodeList)+")";
		@SuppressWarnings("unchecked")
		List<Provider> providerList = commonDao.queryByHql(hqlDetail);
		
		List<Object> resultList = new ArrayList<Object>();
		
		//拼装数据
		for(ProductScenic product: productScenicList){
			Map<String,Object> productDetailMap = new HashMap<String,Object>();
			productDetailMap.put("nameCn", product.getNameCn());
			productDetailMap.put("numberCode", product.getNumberCode());
			for(Provider p : providerList){
				if(product.getProvider().equals(p.getNumberCode())){
					productDetailMap.put("provider",p.getNameCn());
					break;
				}
			}
			resultList.add(productDetailMap);
		}
		
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",resultList);
		
		return ReturnUtil.returnMap(1, mapContent);
	
	}
}
