package xyz.svc.core.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;
import xyz.model.base.Sms;
import xyz.svc.core.SmsSvc;
import xyz.util.Constant;
import xyz.util.DateUtil;
import xyz.util.UUIDUtil;

@Service
public class SmsSvcImp implements SmsSvc{
	
	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> querySmsList(
			int offset, 
			int pagesize,
			String clientCode,
			String phone,
			String content,
			String username,
			String status,
			Date dateStart,
			Date dateEnd) {
		String hql ="from Sms t where 1 = 1";
		if(username!=null && !"".equals(username)){
			hql +=" and t.username = '"+username+"'";
		}
		if(phone!=null && !"".equals(phone)){
			hql +=" and t.phone = '"+phone+"'";
		}
		if(clientCode!=null && !"".equals(clientCode)){
			hql +=" and t.dataKey = '"+clientCode+"'";
		}
		if(dateStart!=null){
			hql +=" and t.addDate >= '"+DateUtil.dateToShortString(dateStart)+"'";
		}
		if(dateEnd!=null){
			hql +=" and t.addDate <= '"+DateUtil.getDateEndForQuery(dateEnd)+"'";
		}
		if(status!=null && !"".equals(status)){
			hql +=" and t.status like '%"+status+"%'";
		}
		if(content!=null && !"".equals(content)){
			hql +=" and t.content like '%"+content+"%'";
		}
		hql += " order by t.addDate desc";
		
		String countHql = "select count(iidd) "+hql;
		Query countQuery = commonDao.getQuery(countHql);
		Number countTemp = (Number)countQuery.uniqueResult();
		int count = countTemp==null?0:countTemp.intValue();
		
		Query query = commonDao.getQuery(hql);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		@SuppressWarnings("unchecked")
		List<Sms> smsList = query.list();
		Map<String,Object> mapContent = new HashMap<String, Object>();
		mapContent.put("total",count);
		mapContent.put("rows",smsList);
		return ReturnUtil.returnMap(1,mapContent);
	}
	
	@Override
	public Map<String, Object> addSms(
			String phone,
			String content) {
		String business = "ABC";
		String username = MyRequestUtil.getSecurityLogin().getUsername();
		Date date = new Date();
			
		String numberCode = UUIDUtil.getUUIDStringFor32();
				
		Map<String, String> accessoryParam = new HashMap<String, String>();
		accessoryParam.put("content", content);
		accessoryParam.put("phone", phone);
		accessoryParam.put("dataKey", numberCode);
		accessoryParam.put("business",business);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>)new RmiUtil().loadData(Constant.smsUrl_smsSend, accessoryParam);
		if(result==null){
			return ReturnUtil.returnMap(0,"短信发送失败！");
		}
		Integer status = (Integer)result.get(Constant.result_status);
		if(status==null){
			return ReturnUtil.returnMap(0,"短信发送失败！");
		}
		if(status==0){
			return ReturnUtil.returnMap(0,"短信发送失败！失败原因【"+result.get(Constant.result_msg)+"】 ");
		}
		Sms sms = new Sms();
		sms.setAddDate(date);
		sms.setAlterDate(date);
		sms.setDataKey(null);
		sms.setContent(content);
		sms.setBusiness(business);
		sms.setUsername(username);
		sms.setPhone(phone);
		sms.setNumberCode(numberCode);
		sms.setStatus("已发送！");
		commonDao.save(sms);
		return ReturnUtil.returnMap(1,null);
	}
	
	@Override
	public Map<String, Object> updateSmsStatus(String numberCode){
		Sms sms = (Sms)commonDao.getObjectByUniqueCode("Sms", "numberCode", numberCode);
		if(sms==null){
			return ReturnUtil.returnMap(0,"短信不存在，无法更新状态!");
		}
		
		Map<String, String> accessoryParam = new HashMap<String, String>();
		accessoryParam.put("dataKey", numberCode);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>)new RmiUtil().loadData(Constant.smsUrl_smsStatus, accessoryParam);
		if(result==null){
			return ReturnUtil.returnMap(0,"短信状态更新失败！");
		}
		Integer status = (Integer)result.get(Constant.result_status);
		if(status==null){
			return ReturnUtil.returnMap(0,"短信状态更新失败！");
		}
		if(status==0){
			return result;
		}
		
		Date date = new Date();
		sms.setAlterDate(date);
		sms.setStatus((String)result.get(Constant.result_content));
		commonDao.update(sms);
		return ReturnUtil.returnMap(1,sms.getStatus());
	}
}
