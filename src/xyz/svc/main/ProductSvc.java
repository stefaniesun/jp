package xyz.svc.main;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ProductSvc {

	Map<String, Object> queryProductList(String nameCn, int offset, int pagesize);

	Map<String, Object> addProduct(String name, String type, BigDecimal price, int stock,String content);

	Map<String, Object> deleteProduct(String numberCode);

}
