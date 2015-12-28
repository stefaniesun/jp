package xyz.svc.core.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.core.OrderContent;
import xyz.model.form.Q_OrderContent;
import xyz.svc.core.OrderContentSvc;
import xyz.util.DateUtil;

@Service
public class OrderContentSvcImp implements OrderContentSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> queryOrderContentList(int offset,
			int pagesize,Q_OrderContent q_OrderContent) {
		String hql=" from OrderContent "+getOrderContentWhere(q_OrderContent)+" ";
		
		String countHql = "select count(iidd) "+hql;
		Query countQuery = commonDao.getQuery(countHql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<OrderContent> orderContentList=query.list();
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", count);
		mapContent.put("rows",orderContentList);
		return ReturnUtil.returnMap(1, mapContent);
	}

	@Override
	public Map<String, Object> updateOrderContentForFlagClient(
			String orderContents) {
		int flag=0;
		if(!"".equals(orderContents)&&orderContents!=null){
			String[] orderContentArray=orderContents.split(",");
			for(int i=0;i<orderContentArray.length;i++){
				OrderContent orderContent=(OrderContent)commonDao.getObjectByUniqueCode("OrderContent", "clientCode", orderContentArray[i].toString());
				if(orderContent.getFlagPay()==0){
					flag=1;
					return ReturnUtil.returnMap(0, "您选中的订单中有未支付票单请支付后出票！");
				}else if(orderContent.getFlagClient()==1){
					flag=1;
					return ReturnUtil.returnMap(0, "您选中的订单下有票单已经出票无法重复出票");
				}
			}
			
			if(flag==0){
				for(int ii=0;ii<orderContentArray.length;ii++){
					OrderContent orderContent=(OrderContent)commonDao.getObjectByUniqueCode("OrderContent", "clientCode", orderContentArray[ii].toString());
					orderContent.setFlagRefund(1);
					commonDao.update(orderContent);
				}
				return ReturnUtil.returnMap(1, null);
			}else{
				return ReturnUtil.returnMap(0,"订单出现异常，请联系管理员!");
			}
		}else{
			return ReturnUtil.returnMap(0, "请选择出票对象！");
		}
	}

	@Override
	public Map<String, Object> updateOrderContentForFlagRefund(
			String orderContents) {
		int flag=0;
		if(!"".equals(orderContents)&&orderContents!=null){
			String[] orderContentArray=orderContents.split(",");
			//验证
			for(int i=0;i<orderContentArray.length;i++){
				OrderContent orderContent=(OrderContent)commonDao.getObjectByUniqueCode("OrderContent", "clientCode", orderContentArray[i].toString());
				if(orderContent.getFlagApply()==0){
					flag=1;
					return ReturnUtil.returnMap(0, "该订单下票单未申请退款无法进行退款操作！");
				}else if(orderContent.getFlagPay()==0){
					flag=1;
					return ReturnUtil.returnMap(0, "您选中的订单中有未支付票单请支付后进行退款操作！");
				}else if(orderContent.getFlagRefund()==1){
					flag=1;
					return ReturnUtil.returnMap(0, "您选中的订单下票单已经退款无法重复退款！");
				}
			}
			if(flag==0){
				for(int ii=0;ii<orderContentArray.length;ii++){
					OrderContent orderContent=(OrderContent)commonDao.getObjectByUniqueCode("OrderContent", "clientCode", orderContentArray[ii].toString());
					orderContent.setFlagRefund(1);
					commonDao.update(orderContent);
				}
				return ReturnUtil.returnMap(1, null);
			}else{
				return ReturnUtil.returnMap(0,"订单出现异常，请联系管理员!");
			}
		}else{
			return ReturnUtil.returnMap(0, "请选择退款对象！");
		}
	}
	
	private String getOrderContentWhere(Q_OrderContent q_OrderContent){
		StringBuffer resultStr = new StringBuffer();
		resultStr.append("  WHERE 1=1 ");
		String orderNum = q_OrderContent.getOrderNum();
		if(orderNum!=null && !"".equals(orderNum)){
			resultStr.append(" and orderNum = '"+orderNum+"'");
			
		}
		
		
		String flagStr = q_OrderContent.getFlagStr();
		if(flagStr!=null && !"".equals(flagStr)){
			String[] tt = flagStr.split(",");
			for(int i=0;i<tt.length;i++){
				//是否出票
				if("noClient".equals(tt[i])){
					resultStr.append(" and flagClient = 0");
				}else if("yesClient".equals(tt[i])){
					resultStr.append(" and flagClient = 1");
				}else if("noRefund".equals(tt[i])){
					//是否退票
					resultStr.append(" and flagRefund = 0");
				}else if("yesRefund".equals(tt[i])){
					resultStr.append(" and flagRefund = 1");
				}else if("noUse".equals(tt[i])){
					//是否使用
					resultStr.append(" and flagUse = 0");
				}else if("yesUse".equals(tt[i])){
					resultStr.append(" and flagUse = 1");
				}else if("noOver".equals(tt[i])){
					//是否冻结
					resultStr.append(" and flagOver = 0");
				}else if("yesOver".equals(tt[i])){
					resultStr.append(" and flagOver = 1");
				}else if("noPay".equals(tt[i])){
					//是否支付
					resultStr.append(" and flagPay = 0");
				}else if("yesPay".equals(tt[i])){
					resultStr.append(" and flagPay = 1");
				}else if("noApply".equals(tt[i])){
					//是否有申请退款
					resultStr.append(" and flagApply = 0");
				}else if("yesApply".equals(tt[i])){
					resultStr.append(" and flagApply = 1");
				}
			}
		}

		String buyer=q_OrderContent.getBuyer();
		if(!"".equals(buyer)&&buyer!=null){
			resultStr.append(" and buyer = '"+buyer+"' ");
		}
		
		String productNameCn=q_OrderContent.getProductNameCn();
		if(!"".equals(productNameCn)&&productNameCn!=null){
			resultStr.append(" and productNameCn like '%"+productNameCn+"%' ");
		}
		
		String providerNameCn=q_OrderContent.getProviderNameCn();
		if(!"".equals(providerNameCn)&&providerNameCn!=null){
			resultStr.append(" and providerNameCn like '%"+providerNameCn+"%' ");
		}
			
		String dateStr = q_OrderContent.getDateStr();
		
		Date dateStart = q_OrderContent.getDateStart();
		if(dateStart!=null){
			if("payDate".equals(dateStr)){
				resultStr.append(" and payDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("dateInfo".equals(dateStr)){
				resultStr.append(" and dateInfo >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("addDate".equals(dateStr)){
				resultStr.append(" and addDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("clientDate".equals(dateStr)){
				resultStr.append(" and clientDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("useDate".equals(dateStr)){
				resultStr.append(" and useDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("refundDate".equals(dateStr)){
				resultStr.append(" and refundDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("overDate".equals(dateStr)){
				resultStr.append(" and overDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("cleanDate".equals(dateStr)){
				resultStr.append(" and cleanDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("alterDate".equals(dateStr)){
				resultStr.append(" and alterDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}else if("applyDate".equals(dateStr)){
				resultStr.append(" and applyDate >= '"+DateUtil.dateToShortString(dateStart)+"'");
			}
		}
		
		Date dateEnd = q_OrderContent.getDateEnd();
		if(dateEnd!=null){
			if("payDate".equals(dateStr)){
				resultStr.append(" and payDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("dateInfo".equals(dateStr)){
				resultStr.append(" and dateInfo <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("addDate".equals(dateStr)){
				resultStr.append(" and addDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("clientDate".equals(dateStr)){
				resultStr.append(" and clientDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("useDate".equals(dateStr)){
				resultStr.append(" and useDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("refundDate".equals(dateStr)){
				resultStr.append(" and refundDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("sendDate".equals(dateStr)){
				resultStr.append(" and sendDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("overDate".equals(dateStr)){
				resultStr.append(" and overDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("cleanDate".equals(dateStr)){
				resultStr.append(" and cleanDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("alterDate".equals(dateStr)){
				resultStr.append(" and alterDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}else if("applyDate".equals(dateStr)){
				resultStr.append(" and applyDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'");
			}
		}
		
		String remarkStr = q_OrderContent.getRemarkStr();
		String remark = q_OrderContent.getRemark();
		if(remark!=null && !"".equals(remark)){
			if("orderRemark".equals(remarkStr)){
				resultStr.append(" and remark like '%"+remark+"%'");
			}else if("flagRemark".equals(remarkStr)){
				resultStr.append(" and flagRemark like '%"+remark+"%'");
			}else if("remarkBuy".equals(remarkStr)){
				resultStr.append(" and remarkBuy like '%"+remark+"%'");
			}
		}
		
		return resultStr.toString();
	}
}
