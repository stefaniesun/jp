package xyz.ctrl.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.svc.scheduled.CleanSTSvc;

/**
 * 计划任务类
 * @author Administrator
 *
 */
@Component
public class CleanST {
	
	@Autowired
	private CleanSTSvc cleanSTSvc;
	
	/**
	 * 排班表自动运行
	 */
	public int cleanStockModelNullOper(){
		return cleanSTSvc.cleanStockModelNullOper();
	}
}
