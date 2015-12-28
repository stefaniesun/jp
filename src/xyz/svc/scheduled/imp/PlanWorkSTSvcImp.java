package xyz.svc.scheduled.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.model.base.PlanWork;
import xyz.svc.scheduled.PlanWorkSTSvc;

@Service
public class PlanWorkSTSvcImp implements PlanWorkSTSvc {
	
	@Autowired
	CommonDao commonDao;
	
	@Override
	public int autoPlanWorkOper(){
		String hql = "from PlanWork t order by t.alterDate asc";
		PlanWork planWork = (PlanWork)commonDao.queryUniqueByHql(hql);
		
		if(planWork==null){
			List<String> ttList = new ArrayList<String>();
			ttList.add("hxTid");
			ttList.add("sendJoint");
			Random random = new Random();
			while(ttList.size()>0){
				String tt = ttList.get(random.nextInt(ttList.size()));
				ttList.remove(tt);
				int pppp = 0;
				if("hxTid".equals(tt)){
					System.out.print("开始尝试核销----");
					//pppp = hxTid();
				}else if("sendJoint".equals(tt)){
					System.out.print("开始尝试 对接----");
					//pppp = sendJoint();
				}
				System.out.println("解决了个"+pppp+"问题");
				if(pppp>0){
					return pppp;
				}
			}
		}
		return 0;
	}
}
