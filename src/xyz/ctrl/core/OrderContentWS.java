package xyz.ctrl.core;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.model.form.Q_OrderContent;
import xyz.svc.core.OrderContentSvc;

@Controller
@RequestMapping(value="/OrderContentWS")
public class OrderContentWS {

	@Autowired
	OrderContentSvc orderContentSvc;
	
	/**
	 * 查询订单列表
	 * @param nameCn
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="queryOrderContentList")
	@ResponseBody
	public Map<String,Object> queryOrderContentList(
			int page,
			int rows,
			Q_OrderContent q_OrderContent){
		int pagesize = rows;
		int offset = (page-1)*pagesize;
		
		
		return orderContentSvc.queryOrderContentList(offset, pagesize,q_OrderContent);
	}
	
	@RequestMapping(value="updateOrderContentForFlagClient")
	@ResponseBody
	public Map<String,Object> updateOrderContentForFlagClient(String orderContents){
		return orderContentSvc.updateOrderContentForFlagClient(orderContents);
	}
	@RequestMapping(value="updateOrderContentForFlagRefund")
	@ResponseBody
	public Map<String,Object> updateOrderContentForFlagRefund(String orderContents){
		return orderContentSvc.updateOrderContentForFlagRefund(orderContents);
	}

}
