package xyz.svc.base;

import java.util.Map;

public interface BaseOrderSvc {

	Map<String, Object> queryOrderList(int status);

}
