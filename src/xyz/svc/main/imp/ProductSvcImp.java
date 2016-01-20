package xyz.svc.main.imp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.Product;
import xyz.model.main.ProductImage;
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
		List<Product> productHotelList=query.list();
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",productHotelList);
		return ReturnUtil.returnMap(1, mapContent);
	
	}

	@Override
	public Map<String, Object> addProduct(String name, String special,String type, BigDecimal price,BigDecimal basePrice, int stock,String content,String images) {
		Product product =new Product();
		product.setNumberCode(UUIDUtil.getUUIDStringFor32());
		product.setName(name);
		product.setSpecial(special);
		product.setType(type);
		product.setPrice(price);
		product.setBasePrice(basePrice);
		product.setStock(stock);
		product.setContent(content);
		commonDao.save(product);
		
		if(images!=null&&!images.equals("")){
			for(String image:images.split(",")){
				ProductImage productImage=(ProductImage) commonDao.getObjectByUniqueCode("ProductImage", "numberCode", image);
				productImage.setProduct(product.getNumberCode());
				commonDao.update(productImage);
			}
		}
		
		
		
		return ReturnUtil.returnMap(1, null);
	
	}

	@Override
	public Map<String, Object> deleteProduct(String numberCode) {
		Product product=(Product) commonDao.getObjectByUniqueCode("Product", "numberCode", numberCode);
		if(product==null){
			return ReturnUtil.returnMap(0, "产品不存在");
		}
		commonDao.delete(product);
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> editProduct(String numberCode, String name,String special,
			String type, BigDecimal price, BigDecimal basePrice,int stock, String image,String content,String images,String deleteImages) {
		Product product=(Product) commonDao.getObjectByUniqueCode("Product", "numberCode", numberCode);
		if(product==null){
			return ReturnUtil.returnMap(0, "产品不存在");
		}
		product.setName(name);
		product.setSpecial(special);
		product.setType(type);
		product.setPrice(price);
		product.setBasePrice(basePrice);
		product.setStock(stock);
		product.setImage(image);
		product.setContent(content);
		commonDao.update(product);
		
		
		if(deleteImages!=null&&!deleteImages.equals("")){
			for(String deleteImage:deleteImages.split(",")){
				ProductImage productImage=(ProductImage) commonDao.getObjectByUniqueCode("ProductImage", "numberCode", deleteImage);
				if(productImage!=null){
					commonDao.delete(productImage);
				}
			}
		}
		
		if(images!=null&&!images.equals("")){
			for(String img:images.split(",")){
				ProductImage productImage=(ProductImage) commonDao.getObjectByUniqueCode("ProductImage", "numberCode", img);
				productImage.setProduct(product.getNumberCode());
				commonDao.update(productImage);
			}
		}
		
		
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> getProduct(String numberCode) {
		Product product=(Product) commonDao.getObjectByUniqueCode("Product", "numberCode", numberCode);
		if(product==null){
			return ReturnUtil.returnMap(0, "产品不存在");
		}
		
		String hql="from ProductImage where product='"+product.getNumberCode()+"'";
		List<ProductImage> productImages=commonDao.queryByHql(hql);
		
		JSONArray jsonArray=JSONArray.fromObject(productImages);
		
		product.setImages(jsonArray.toString());
		return ReturnUtil.returnMap(1, product);
	}

}
