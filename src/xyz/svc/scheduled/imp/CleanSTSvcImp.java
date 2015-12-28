package xyz.svc.scheduled.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.svc.scheduled.CleanSTSvc;

@Service
public class CleanSTSvcImp implements CleanSTSvc {
	
	@Autowired
	CommonDao commonDao;
	
	@Override
	public int cleanStockModelNullOper(){
		return 0;
	}
}
