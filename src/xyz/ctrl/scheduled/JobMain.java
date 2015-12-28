package xyz.ctrl.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 计划任务启动类
 * @author Administrator
 *
 */
//@Component
public class JobMain {
	@Autowired
	private PlanWorkST planWorkST;
	
	@Autowired
	private CleanST cleanST;
	
	@Scheduled(fixedDelay=1*5*1000)
//	@Scheduled(cron="15 0/2 * * * ?")
	public void autoPlanWork(){
		System.out.print("开始尝试排班任务---");
		int t = planWorkST.autoPlanWork();
		if(t==1){
			System.out.println("实施了一项排班任务！");
		}
	}
	
	@Scheduled(fixedDelay=1*9*1000)
//	@Scheduled(cron="11 0/1 * * * ?")
	public void cleanStockModelNullOper(){
		System.out.print("开始尝试测试,如果发现有客户使用需申请模式申请失败,则自动把该单品该天的库存设为0---");
		System.out.println("实施了"+cleanST.cleanStockModelNullOper()+"项排班任务！");
	}
}
