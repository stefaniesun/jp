package xyz.ctrl.buyer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.buyer.BuyerHotelSvc;

@Controller
@RequestMapping(value="/BuyerHotelWS")
public class BuyerHotelWS {

	@Autowired
	BuyerHotelSvc buyerHotelSvc;
	
	/**
	 * 查询产品列表
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="queryHotelList")
	@ResponseBody
	public Map<String,Object> queryHotelList(String nameCn,String provider,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return buyerHotelSvc.queryHotelList(nameCn,provider, offset, pagesize);
	}
}
