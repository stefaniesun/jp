package xyz.ctrl.buyer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.buyer.BuyerScenicSvc;

@Controller
@RequestMapping(value="/BuyerScenicWS")
public class BuyerScenicWS {

	@Autowired
	BuyerScenicSvc buyerScenicSvc;
	
	/**
	 * 查询产品列表
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="queryScenicList")
	@ResponseBody
	public Map<String,Object> queryScenicList(String nameCn,String provider,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return buyerScenicSvc.queryScenicList(nameCn,provider, offset, pagesize);
	}
}
