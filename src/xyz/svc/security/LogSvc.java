package xyz.svc.security;

import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.security.LogOper;




@Service
public interface LogSvc {
	public Map<String, Object> addLogOper(LogOper logOper);
}
