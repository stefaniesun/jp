package xyz.svc.buyer.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.core.OrderContent;
import xyz.model.main.ProductObject;
import xyz.model.main.Stock;
import xyz.model.member.Customer;
import xyz.model.member.XyzSessionLogin;
import xyz.svc.buyer.BuyerOrderSvc;
import xyz.util.DateUtil;
import xyz.util.ProductUtil;
import xyz.util.StockPriceUtil;
import xyz.util.StringUtil;

@Service
public class BuyerOrderSvcImp implements BuyerOrderSvc {

	@Resource
	CommonDao commonDao;
	
	@Autowired
	StockPriceUtil stockPriceUtil;
	
	@Autowired
	ProductUtil productUtil;
	
	@Override
	public Map<String, Object> createOrder(String product, String type,
			int count, BigDecimal price,Date dateInfo, String personInfo, String remarkBuy) {
		if(dateInfo==null){
			return  ReturnUtil.returnMap(0, "出行日期不能为空！");
		}
		if(dateInfo.getTime()<DateUtil.shortStringToDate(DateUtil.dateToShortString(new Date())).getTime()){
			return  ReturnUtil.returnMap(0, "日期不能小于当天！");
		}
		String username = MyRequestUtil.getXyzSessionLogin().getUsername();
		Customer customer =  (Customer) commonDao.getObjectByUniqueCode("Customer", "username", username);
		if(customer==null){
			return  ReturnUtil.returnMap(0, "用户不存在！");
		}
		
		ProductObject productObject=productUtil.getProductByNumberCode(product);
		if(productObject==null){
			return  ReturnUtil.returnMap(0, "产品不存在！");
		}
				
		Stock stock=stockPriceUtil.getStockByDate(product,dateInfo);
		if(stock==null||stock.getCount()==0){
			return  ReturnUtil.returnMap(0, "库存为空，请重新选择！");
		}
		if(price.compareTo(stock.getPrice())!=0){
			return  ReturnUtil.returnMap(0, "价格有变动，建议重新下单");
		}
		
		BigDecimal amount = new BigDecimal(count).multiply(price);
		
		String orderNum = StringUtil.get_new_orderNum();
		String clientCode = StringUtil.get_new_orderNum();
		
		
		OrderContent orderContent=new OrderContent();
		orderContent.setOrderNum(orderNum);
		orderContent.setClientCode(clientCode);
		orderContent.setCount(count);
		orderContent.setPrice(price);
		orderContent.setBuyer(customer.getUsername());
		orderContent.setDateInfo(dateInfo);
		orderContent.setMoney(amount);
		orderContent.setAddDate(new Date());
		orderContent.setAlterDate(new Date());
		orderContent.setPersonInfo(personInfo);
		orderContent.setRemarkBuy(remarkBuy);
		
		commonDao.save(orderContent);
		
		
		//减库存
		stockPriceUtil.cutStock(product,dateInfo,count,clientCode, "b2b", remarkBuy);
		
		return ReturnUtil.returnMap(1, null);
	}

	@Override
	public Map<String, Object> queryOrderList(int flagPay,int offset, int pagesize) {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		if(xyzSessionLogin==null){
			return ReturnUtil.returnMap(0,"无有效登录信息");
		}
		String hql=" from OrderContenr where buyer='"+xyzSessionLogin.getUsername()+"'";
		hql+=" and flagPay = '"+flagPay+"'";
		
		String countHql = "select count(numberCode) "+hql;
		Query countQuery = commonDao.getQuery(countHql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<OrderContent> orderContents=query.list();
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",orderContents);
		return ReturnUtil.returnMap(1, mapContent);
	}

	@Override
	public Map<String, Object> getOrder(String clientCode) {
		OrderContent orderContent=(OrderContent) commonDao.getObjectByUniqueCode("OrderContent", "clientCode", clientCode);
		if(orderContent==null){
			return ReturnUtil.returnMap(0, null);
		}
		return ReturnUtil.returnMap(1, orderContent);
	}

}
