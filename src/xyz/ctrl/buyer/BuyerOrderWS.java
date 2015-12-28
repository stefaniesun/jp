package xyz.ctrl.buyer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.buyer.BuyerOrderSvc;


@Controller
@RequestMapping(value="/BuyerOrderWS")
public class BuyerOrderWS {

	@Autowired
	BuyerOrderSvc buyerOrderSvc;
	
	/**
	 * 生成订单接口
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="createOrder")
	@ResponseBody
	public Map<String,Object> createOrder(
			String product,
			String type,
			int count,
			BigDecimal price,
			Date dateInfo,
			String personInfo,
			String remarkBuy){
		return buyerOrderSvc.createOrder(product,type, count, price,dateInfo,personInfo,remarkBuy);
	}
	
	/**
	 * 查询订单接口
	 */
	@RequestMapping(value="queryOrderList")
	@ResponseBody
	public Map<String,Object> queryOrderList(
			int flagPay,
			int page,
			int rows){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		return buyerOrderSvc.queryOrderList(flagPay,offset,pagesize);
	}
	
	/**
	 * 查询订单详情
	 */
	@RequestMapping(value="getOrder")
	@ResponseBody
	public Map<String,Object> getOrder(String clientCode){
		return buyerOrderSvc.getOrder(clientCode);
	}
}
