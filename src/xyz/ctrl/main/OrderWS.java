package xyz.ctrl.main;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.OrderSvc;
@Controller
@RequestMapping(value="/OrderWS")
public class OrderWS {

	@Autowired
	private OrderSvc orderSvc;
	
	
	@RequestMapping(value="addOrder")
	@ResponseBody
	public Map<String,Object> addOrder(String carts,String address,String remark){
		return orderSvc.addProduct(carts,address,remark);
	}
	
	@RequestMapping(value="getOrder")
	@ResponseBody
	public Map<String,Object> getOrder(String orderNum){
		return orderSvc.getOrder(orderNum);
	}
	
	@RequestMapping(value="queryOrderList")
	@ResponseBody
	public Map<String,Object> queryOrderList(int status){
		return orderSvc.queryOrderList(status);
	}
	
	
	@RequestMapping(value="orderPayOper")
	@ResponseBody
	public Map<String,Object> orderPayOper(String orderNum){
		return orderSvc.orderPayOper(orderNum);
	}
	
	@RequestMapping(value="orderPayConfirmOper")
	@ResponseBody
	public Map<String,Object> orderPayConfirmOper(String orderNum){
		return orderSvc.orderPayConfirmOper(orderNum);
	}
	
	@RequestMapping(value="cancelOrderOper")
	@ResponseBody
	public Map<String,Object> cancelOrderOper(String orderNum){
		return orderSvc.cancelOrderOper(orderNum);
	}
}
