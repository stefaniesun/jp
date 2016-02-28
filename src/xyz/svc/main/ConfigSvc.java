package xyz.svc.main;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface ConfigSvc {

	Map<String, Object> queryConfigList(int offset, int pagesize);

	Map<String, Object> addConfig(String key, String name, String value);

	Map<String, Object> editConfig(String numberCode, String key, String name, String value);

	Map<String, Object> deleteConfig(String numberCode);

	Map<String, Object> getConfig(String numberCode);

	Map<String, Object> getPostalConfig();

}
