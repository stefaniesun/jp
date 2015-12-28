package xyz.ctrl.seller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.model.main.ProductHotel;
import xyz.svc.main.ProductHotelSvc;

@Controller
@RequestMapping(value="/SellerHotelWS")
public class SellerHotelWS {

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
	public Map<String,Object> queryProductHotelListForStock(String nameCn,String provider,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return productHotelSvc.queryProductHotelListForStock(nameCn,provider, offset, pagesize);
	}
}
