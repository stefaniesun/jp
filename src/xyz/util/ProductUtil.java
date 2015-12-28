package xyz.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import xyz.dao.CommonDao;
import xyz.model.main.ProductHotel;
import xyz.model.main.ProductObject;
import xyz.model.main.ProductScenic;

@Component
public class ProductUtil {

	@Resource
	CommonDao commonDao;
	
	public ProductObject getProductByNumberCode(String numberCode) {
		ProductObject productObject=null;
		if(numberCode.length()>2){
			if(numberCode.substring(0,2).endsWith("SC")){
				ProductScenic scenic= (ProductScenic) commonDao.getObjectByUniqueCode("ProductScenic", "numberCode", numberCode);
				productObject=new ProductObject();
				productObject.setNumberCode(scenic.getNumberCode());
				productObject.setProvider(scenic.getProvider());
				productObject.setNameCn(scenic.getNameCn());
			}else if(numberCode.substring(0,2).endsWith("HO")){
				ProductHotel hotel= (ProductHotel) commonDao.getObjectByUniqueCode("ProductHotel", "numberCode", numberCode);
				productObject=new ProductObject();
				productObject.setNumberCode(hotel.getNumberCode());
				productObject.setProvider(hotel.getProvider());
				productObject.setNameCn(hotel.getNameCn());
			}
		}
		return productObject;
	}
}
