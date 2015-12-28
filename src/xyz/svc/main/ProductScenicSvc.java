package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ProductScenicSvc {
	
	public Map<String,Object> queryProductScenicList(String nameCn, String provider,
			int offset,
			int pagesize);

	public Map<String,Object> addProductScenic(String nameCn,String provider);
	
	public Map<String,Object> editProductScenic(String numberCode,String nameCn,String provider);
	
	public Map<String,Object> deleteProductScenic(String numberCodes);

	public Map<String, Object> queryProductScenicListForStock(String nameCn,
			String provider, int offset, int pagesize);
}
