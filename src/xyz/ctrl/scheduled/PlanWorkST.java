package xyz.ctrl.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.svc.scheduled.PlanWorkSTSvc;

/**
 * 计划任务类
 * @author Administrator
 *
 */
@Component
public class PlanWorkST {
	
	@Autowired
	private PlanWorkSTSvc planWorkSTSvc;
	
	/**
	 * 排班表自动运行
	 */
	public int autoPlanWork(){
		return planWorkSTSvc.autoPlanWorkOper();
	}
}
