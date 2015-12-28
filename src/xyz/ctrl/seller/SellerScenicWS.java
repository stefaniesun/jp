package xyz.ctrl.seller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ProductScenicSvc;

@Controller
@RequestMapping(value="/SellerScenicWS")
public class SellerScenicWS {

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
	public Map<String,Object> queryProductScenicListForStock(String nameCn,String provider,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return productScenicSvc.queryProductScenicListForStock(nameCn,provider, offset, pagesize);
	}
}
