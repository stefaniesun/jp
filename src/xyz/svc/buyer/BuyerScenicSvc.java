package xyz.svc.buyer;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BuyerScenicSvc {

	Map<String, Object> queryScenicList(String nameCn, String provider,
			int offset, int pagesize);

}
