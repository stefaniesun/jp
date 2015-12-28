package xyz.filter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import xyz.util.Constant;


public class ReturnUtil {
	private ReturnUtil(){}
	public static Map<String, Object> returnMap(int status,Object object){
		Map<String,Object> map = new HashMap<String, Object>();
		if(status==1){
			map.put(Constant.result_status, 1);
			if(object!=null){
				map.put(Constant.result_content,object);
			}
			return map;
		}else{
			map.put(Constant.result_status, 0);
			if(object!=null){
				map.put(Constant.result_msg,"操作失败："+object.toString());
			}
			if(TransactionSynchronizationManager.getCurrentTransactionName()!=null){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			return map;
		}
	}
}
