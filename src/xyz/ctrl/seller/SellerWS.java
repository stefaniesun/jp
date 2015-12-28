package xyz.ctrl.seller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.core.SmsSvc;
import xyz.svc.seller.SellerSvc;

@Controller
@RequestMapping(value="/SellerWS")
public class SellerWS {

	@Autowired
	private SellerSvc sellerSvc;
	
	/**
	 * 普通登录
	 */
	@RequestMapping(value="loginOper")
	@ResponseBody
	public Map<String, Object> loginOper(
			String username,
			String password){
		return sellerSvc.loginOper(username, password);
	}
}
