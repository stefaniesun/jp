package xyz.svc.base;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BaseUserSvc {

	Map<String, Object> queryUserList();

}
