package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ProductHotelSvc {
	
	public Map<String,Object> queryProductHotelList(String nameCn, String provider,
			int offset,
			int pagesize);

	public Map<String,Object> addProductHotel(String nameCn,String provider);
	
	public Map<String,Object> editProductHotel(String numberCode,String nameCn,String provider);
	
	public Map<String,Object> deleteProductHotel(String numberCodes);

	public Map<String, Object> queryProductHotelListForStock(String nameCn,
			String provider, int offset, int pagesize);
}
