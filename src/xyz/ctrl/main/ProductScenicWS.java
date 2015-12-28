package xyz.ctrl.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ProductScenicSvc;

@Controller
@RequestMapping(value="/ProductScenicWS")
public class ProductScenicWS {

	@Autowired
	ProductScenicSvc productScenicSvc;
	
	/**
	 * 查询景点产品列表
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="queryProductScenicList")
	@ResponseBody
	public Map<String,Object> queryProductScenicList(String nameCn,String provider,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return productScenicSvc.queryProductScenicList(nameCn,provider, offset, pagesize);
	}

	/**
	 * 添加产品
	 * @param nameCn
	 * @param provider
	 * @return
	 */
	@RequestMapping(value="addProductScenic")
	@ResponseBody
	public Map<String,Object> addProductScenic(String nameCn,String provider){
		
		return productScenicSvc.addProductScenic(nameCn, provider);
	}
	
	/**
	 * 修改指定产品
	 * @param numberCode
	 * @param nameCn
	 * @param provider
	 * @return
	 */
	@RequestMapping(value="editProductScenic")
	@ResponseBody
	public Map<String,Object> editProductScenic(String numberCode,String nameCn,String provider){
		return productScenicSvc.editProductScenic(numberCode, nameCn, provider);
	}
	
	/**
	 * 删除产品
	 * @param numberCodes
	 * @return
	 */
	@RequestMapping(value="deleteProductScenic")
	@ResponseBody
	public Map<String,Object> deleteProductScenic(String numberCodes){
		return productScenicSvc.deleteProductScenic(numberCodes);
	}
}
