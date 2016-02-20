package xyz.svc.base;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BaseOrderSvc {

	Map<String, Object> queryOrderList(BigDecimal status);

}
