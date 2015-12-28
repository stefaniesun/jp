package xyz.svc.core;

import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.form.Q_OrderContent;


@Service
public interface OrderContentSvc {
	
	public Map<String,Object> queryOrderContentList(int offset,
			int pagesize,
			Q_OrderContent q_OrderContent);
	
	public Map<String,Object> updateOrderContentForFlagClient(String orderContents);
	
	public Map<String,Object> updateOrderContentForFlagRefund(String orderContents);
}
