package xyz.ctrl.main;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.svc.main.ShoppingCartSvc;

@Controller
@RequestMapping(value="/ShoppingCartWS")
public class ShoppingCartWS {

	@Autowired
	ShoppingCartSvc shoppingCartSvc;
	
	@RequestMapping(value="addShoppingCart")
	@ResponseBody
	public Map<String,Object> addShoppingCart(String product,int count){
		return shoppingCartSvc.addShoppingCart(product,count);
	}
	
	@RequestMapping(value="queryShoppingCartList")
	@ResponseBody
	public Map<String,Object> queryShoppingCartList(){
		return shoppingCartSvc.queryShoppingCartList();
	}
	
	@RequestMapping(value="deleteShoppingCart")
	@ResponseBody
	public Map<String,Object> deleteShoppingCart(String numberCode){
		return shoppingCartSvc.deleteShoppingCart(numberCode);
	}
	
	@RequestMapping(value="editShoppingCart")
	@ResponseBody
	public Map<String,Object> editShoppingCart(String numberCode,int count){
		return shoppingCartSvc.editShoppingCart(numberCode,count);
	}
}
