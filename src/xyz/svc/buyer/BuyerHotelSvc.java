package xyz.svc.buyer;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BuyerHotelSvc {

	Map<String, Object> queryHotelList(String nameCn, String provider,
			int offset, int pagesize);

}
