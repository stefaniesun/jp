package xyz.svc.security.imp;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.security.LogOper;
import xyz.svc.security.LogSvc;

@Service
public class LogSvcImp implements LogSvc{

	@Resource
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> addLogOper(LogOper logOper) {
		logOper.setAddDate(new Date());
		if(logOper.getDataContent()!=null && logOper.getDataContent().length()>20000){
			String newStr = logOper.getDataContent().substring(0,20000);
			logOper.setDataContent(newStr);
		}
		commonDao.save(logOper);
		return ReturnUtil.returnMap(1, null);
	}
}
