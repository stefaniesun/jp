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
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return productSvc.queryProductList(nameCn,offset, pagesize);
	}
	
	@RequestMapping(value="addProduct")
	@ResponseBody
	public Map<String,Object> addProduct(String name,String type,
			BigDecimal price,
			int stock,String content){
		System.out.println("----------22222------"+content);
		return productSvc.addProduct(name,type, price,stock,content);
	}
	
	
	@RequestMapping(value="deleteProduct")
	@ResponseBody
	public Map<String,Object> deleteProduct(String numberCode){
		return productSvc.deleteProduct(numberCode);
	}
	
	
	
	
	
}
