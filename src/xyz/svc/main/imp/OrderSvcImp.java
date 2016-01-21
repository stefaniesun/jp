package xyz.svc.main.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.config.AlipayConfig;
import xyz.config.AlipaySubmit;
import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.main.Order;
import xyz.model.main.Product;
import xyz.model.main.ShoppingCart;
import xyz.model.member.XyzSessionLogin;
import xyz.svc.main.OrderSvc;
import xyz.util.StringUtil;
import xyz.util.UUIDUtil;

@Service
public class OrderSvcImp implements OrderSvc{

	@Autowired
	CommonDao commonDao;
	
	public Map<String, Object> addProduct(String carts, String address,String remark) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		
		String orderNum=StringUtil.getOrderNum();
		for(String c:carts.split(",")){
			
			ShoppingCart cart=(ShoppingCart) commonDao.getObjectByUniqueCode("ShoppingCart", "numberCode", c);
			
			Order order=new Order();
			order.setAddDate(new Date());
			order.setAddress(address);
			order.setNumberCode(UUIDUtil.getUUIDStringFor32());	
			order.setPrice(cart.getPrice());
			order.setProduct(cart.getProduct());
			order.setCount(cart.getCount());
			order.setProductName(cart.getProductName());
			order.setStatus(Order.ORDER_UNPAY);
			order.setUsername(xyzSessionLogin.getUsername());
			order.setOrderNum(orderNum);
			order.setRemark(remark);
			commonDao.save(order);
			commonDao.delete(cart);
		}
		return ReturnUtil.returnMap(1, orderNum);
	}

	@Override
	public Map<String, Object> getOrder(String orderNum) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		
		String hql="from Order where orderNum='"+orderNum+"'";
		List<Order> orders=commonDao.queryByHql(hql);
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", orders.size());
		mapContent.put("rows",orders);
		return ReturnUtil.returnMap(1, mapContent);
	}

	@Override
	public Map<String, Object> orderPayOper(String orderNum) {
		//生成付款借口页面
		
		BigDecimal amout=new BigDecimal(0);
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		
		
		String hql="from Order where orderNum='"+orderNum+"'";
		List<Order> orders=commonDao.queryByHql(hql);
		for(Order order:orders){
			amout.add(order.getPrice().multiply(new BigDecimal(order.getCount())));
		}
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("seller_email", AlipayConfig.seller_email);
		sParaTemp.put("out_trade_no",orderNum);
		sParaTemp.put("subject", "订单支付"+orderNum);
		sParaTemp.put("total_fee", "0.01");
		sParaTemp.put("body", "订单支付"+orderNum);
		
		sParaTemp.put("paymethod", "directPay");
		sParaTemp.put("defaultbank", "");
		
		sParaTemp.put("show_url", "");
		sParaTemp.put("anti_phishing_key", "");
		sParaTemp.put("exter_invoke_ip", "");
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"post","确认");
		
		System.out.println("建立请求数据="+sHtmlText);
		
		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("sHtmlText", sHtmlText);
		
		return ReturnUtil.returnMap(1,dataMap);
	}

	@Override
	public Map<String, Object> queryOrderList(int status) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		String hql="from Order where username='"+xyzSessionLogin.getUsername()+"' and status="+status;
		List<Order> orders=commonDao.queryByHql(hql);
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", orders.size());
		mapContent.put("rows",orders);
		return ReturnUtil.returnMap(1, mapContent);
	}

}
