package xyz.ctrl.main;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ProductSvc;


@Controller
@RequestMapping(value="/ProductWS")
public class ProductWS{
	
	@Autowired
	private ProductSvc productSvc;
	
	/**
	 * 查询产品列表
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="queryProductList")
	@ResponseBody
	public Map<String,Object> queryProductList(String nameCn,String provider,
			int page,
			int rows,
			String type){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return productSvc.queryProductList(nameCn,offset, pagesize,type);
	}
	
	@RequestMapping(value="getProduct")
	@ResponseBody
	public Map<String,Object> getProduct(String numberCode){
		return productSvc.getProduct(numberCode);
	}
	
	@RequestMapping(value="addProduct")
	@ResponseBody
	public Map<String,Object> addProduct(String name,String special,String type,
			BigDecimal price,BigDecimal basePrice,
			int stock,String content,String images,int areaFlag,BigDecimal postagePrice){
		return productSvc.addProduct(name,special,type, price,basePrice,stock,content,images,areaFlag,postagePrice);
	}
	
	@RequestMapping(value="editProduct")
	@ResponseBody
	public Map<String,Object> editProduct(String numberCode,String name,String special,String type,
			BigDecimal price,BigDecimal basePrice,
			int stock,String image,String content,String images,String deleteImages,int areaFlag,BigDecimal postagePrice){
		return productSvc.editProduct(numberCode,name,special,type, price,basePrice,stock,image,content,images,deleteImages,areaFlag,postagePrice);
	}
	
	
	@RequestMapping(value="deleteProduct")
	@ResponseBody
	public Map<String,Object> deleteProduct(String numberCode){
		return productSvc.deleteProduct(numberCode);
	}
	
	
	@RequestMapping(value="calculatePostalPriceOper")
	@ResponseBody
	public Map<String,Object> calculatePostalPriceOper(String products){
		return productSvc.calculatePostalPriceOper(products);
	}
	
	
	
	
	
}
