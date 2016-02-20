package xyz.svc.main;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ProductSvc {

	Map<String, Object> queryProductList(String nameCn, int offset, int pagesize,String type);

	Map<String, Object> addProduct(String name, String special,String type, BigDecimal price, BigDecimal basePrice,int stock,String content,String images,int areaFlag,BigDecimal postagePrice);

	Map<String, Object> deleteProduct(String numberCode);

	Map<String, Object> editProduct(String numberCode, String name,String special,
			String type, BigDecimal price,  BigDecimal basePrice,int stock, String image,String content,String images,String deleteImages,int areaFlag,BigDecimal postagePrice);

	Map<String, Object> getProduct(String numberCode);

	Map<String, Object> calculatePostalPriceOper(String products);

}
