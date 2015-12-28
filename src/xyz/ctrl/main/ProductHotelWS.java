package xyz.ctrl.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ProductHotelSvc;

@Controller
@RequestMapping(value="/ProductHotelWS")
public class ProductHotelWS {

	@Autowired
	ProductHotelSvc productHotelSvc;
	
	/**
	 * 查询景点产品列表
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="queryProductHotelList")
	@ResponseBody
	public Map<String,Object> queryProductHotelList(String nameCn,String provider,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return productHotelSvc.queryProductHotelList(nameCn,provider, offset, pagesize);
	}

	/**
	 * 添加产品
	 * @param nameCn
	 * @param provider
	 * @return
	 */
	@RequestMapping(value="addProductHotel")
	@ResponseBody
	public Map<String,Object> addProductHotel(String nameCn,String provider){
		
		return productHotelSvc.addProductHotel(nameCn, provider);
	}
	
	/**
	 * 修改指定产品
	 * @param numberCode
	 * @param nameCn
	 * @param provider
	 * @return
	 */
	@RequestMapping(value="editProductHotel")
	@ResponseBody
	public Map<String,Object> editProductHotel(String numberCode,String nameCn,String provider){
		return productHotelSvc.editProductHotel(numberCode, nameCn, provider);
	}
	
	/**
	 * 删除产品
	 * @param numberCodes
	 * @return
	 */
	@RequestMapping(value="deleteProductHotel")
	@ResponseBody
	public Map<String,Object> deleteProductHotel(String numberCodes){
		return productHotelSvc.deleteProductHotel(numberCodes);
	}
}
