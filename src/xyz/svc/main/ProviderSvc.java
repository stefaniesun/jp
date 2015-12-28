package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ProviderSvc {
	
	public Map<String,Object> queryProviderList(String nameCn, String providerType,
			int offset,
			int pagesize);

	public Map<String,Object> addProvider(String nameCn,
			String type,
			String level,
			String levelSystem,
			String phone,
			String qq,
			String email,
			String linkman,
			String regionFirst,
			String regionSecond,
			String regionThird,
			String address
			);
	
	public Map<String,Object> editProvider(String numberCode,String nameCn,
			String level,
			String levelSystem,
			String phone,
			String qq,
			String email,
			String linkman,
			String regionFirst,
			String regionSecond,
			String regionThird,
			String address);
	
	public Map<String,Object> deleteProvider(String numberCodes);
}
