package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface OrderSvc {

	Map<String, Object> addProduct(String carts, String address,String remark);

	Map<String, Object> getOrder(String orderNum);

	Map<String, Object> orderPayOper(String orderNum);

	Map<String, Object> queryOrderList(int status);

	Map<String, Object> cancelOrderOper(String orderNum);

}
