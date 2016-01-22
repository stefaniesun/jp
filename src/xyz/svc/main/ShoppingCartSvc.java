package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartSvc {

	Map<String, Object> addShoppingCart(String product, int count);

	Map<String, Object> queryShoppingCartList();

	Map<String, Object> deleteShoppingCart(String numberCode);

	Map<String, Object> editShoppingCart(String numberCode, int count);

}
