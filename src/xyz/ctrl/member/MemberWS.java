package xyz.ctrl.member;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.member.XyzSessionLogin;
import xyz.svc.member.MemberSvc;

@Controller
@RequestMapping(value="/MemberWS")
public class MemberWS {

	@Autowired
	private MemberSvc memberSvc;
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value="login")
	@ResponseBody
	public Map<String, Object> login(
			String username,
			String password){
		return memberSvc.loginOper(username, password);
	}
	
	/**
	 * 验证登录
	 * @return
	 */
	@RequestMapping(value="decideLogin")
	@ResponseBody
	public Map<String, Object> decideLogin(){
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		if(xyzSessionLogin==null){
			return ReturnUtil.returnMap(0, "无有效登录信息");
		}else{
			return ReturnUtil.returnMap(1,xyzSessionLogin);
		}
	}
	
	/**
	 * 用户退出
	 */
	@RequestMapping(value="exit")
	@ResponseBody
	public Map<String, Object> MemberExit(){
		return memberSvc.memberExit();
	}
}
