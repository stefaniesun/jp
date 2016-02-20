package xyz.svc.base.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.User;
import xyz.svc.base.BaseUserSvc;

@Service
public class BaseUserSvcImp implements BaseUserSvc {

	@Autowired
	CommonDao commonDao;

	@Override
	public Map<String, Object> queryUserList() {
		
		String hql="from User where isRobot=0 and disabled=0 ";
		
		List<User> users=commonDao.queryByHql(hql);
		
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", users.size());
		mapContent.put("rows",users);
		return ReturnUtil.returnMap(1, mapContent);
	}
}
