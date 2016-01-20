package xyz.svc.main.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.MyRequestUtil;
import xyz.filter.ReturnUtil;
import xyz.model.main.Product;
import xyz.model.main.ShoppingCart;
import xyz.model.member.XyzSessionLogin;
import xyz.svc.main.ShoppingCartSvc;
import xyz.util.UUIDUtil;

@Service
public class ShoppingCartSvcImp implements ShoppingCartSvc {

	@Autowired
	CommonDao commonDao;
	
	@Override
	public Map<String, Object> addShoppingCart(String product, int count) {
		Product p=(Product) commonDao.getObjectByUniqueCode("Product", "numberCode", product);
		if(p==null){
			return ReturnUtil.returnMap(0, "产品不存在");
		}
		
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		ShoppingCart cart=new ShoppingCart();
		cart.setNumberCode(UUIDUtil.getUUIDStringFor32());
		cart.setCount(count);
		cart.setAddDate(new Date());
		cart.setPrice(p.getPrice());
		cart.setUsername(xyzSessionLogin.getUsername());
		cart.setProduct(p.getNumberCode());
		cart.setProductName(p.getName());
		cart.setImage(p.getImage());
		commonDao.save(cart);
		return ReturnUtil.returnMap(1,null);
	}

	@Override
	public Map<String, Object> queryShoppingCartList() {
		XyzSessionLogin xyzSessionLogin = MyRequestUtil.getXyzSessionLogin();
		
		String hql="from ShoppingCart where username='"+xyzSessionLogin.getUsername()+"'";
		List<ShoppingCart> carts=commonDao.queryByHql(hql);
		Map<String,Object> mapContent=new HashMap<String, Object>();
		mapContent.put("total", carts.size());
		mapContent.put("rows",carts);
		return ReturnUtil.returnMap(1, mapContent);
	}

	@Override
	public Map<String, Object> deleteShoppingCart(String numberCode) {
		ShoppingCart cart=(ShoppingCart) commonDao.getObjectByUniqueCode("ShoppingCart", "numberCode", numberCode);
		if(cart==null){
			return ReturnUtil.returnMap(0, "商品不存在");
		}
		commonDao.delete(cart);
		return ReturnUtil.returnMap(1, null);
	}

}
