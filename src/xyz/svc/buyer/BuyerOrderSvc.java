package xyz.svc.buyer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BuyerOrderSvc {

	Map<String, Object> createOrder(String product, String type, int count,BigDecimal price,
			Date dateInfo, String personInfo, String remarkBuy);

	Map<String, Object> queryOrderList(int flagPay,int offset, int pagesize);

	Map<String, Object> getOrder(String clientCode);

}
