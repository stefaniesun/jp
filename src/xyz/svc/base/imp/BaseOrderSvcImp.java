package xyz.svc.base.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.main.Order;
import xyz.model.main.Product;
import xyz.model.member.XyzSessionLogin;
import xyz.model.util.OrderContent;
import xyz.svc.base.BaseOrderSvc;
import xyz.util.StringTool;

public class BaseOrderSvcImp implements BaseOrderSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryOrderList(int status) {

		String hql="from Order where  status="+status+" order by orderNum";
		List<Order> orders=commonDao.queryByHql(hql);
		
		String products="";
		for(Order order:orders){
			products+=order.getProduct()+",";
		}
		hql="from Product where numberCode in ("+StringTool.StrToSqlString(products)+")";
		List<Product> list=commonDao.queryByHql(hql);
		for(Order order:orders){
			for(Product product:list){
				if(order.getProduct().equals(product.getNumberCode())){
					order.setProductName(product.getName());
					order.setProductImage(product.getImage());
				}
			}
		}
		
	/*	String temp="";
		List<OrderContent> contents=new ArrayList<OrderContent>();
		OrderContent content=new OrderContent();
		for(Order order:orders){
			if(!temp.equals(order.getOrderNum())){
				temp=order.getOrderNum();
				content=new OrderContent();
				content.setAddDate(order.getAddDate());
				content.setAddress(order.getAddress());
				content.setLinkman(order.getLinkman());
				content.setLinkPhone(order.getLinkPhone());
				content.setOrderNum(order.getOrderNum());
				content.setStatus(order.getStatus());
			}
			content.getOrders().add(order);
			contents.add(content);
		}*/
		
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", orders.size());
		mapContent.put("rows",orders);
		return ReturnUtil.returnMap(1, mapContent);
	}

}
