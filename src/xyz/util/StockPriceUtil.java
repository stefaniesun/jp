package xyz.util;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xyz.dao.CommonDao;
import xyz.exception.MyExceptionForRole;
import xyz.model.main.LogTsTemp;
import xyz.model.main.ProductObject;
import xyz.model.main.Stock;

@Component
public class StockPriceUtil {

	@Resource
	CommonDao commonDao;
	
	@Autowired
	ProductUtil productUtil;
	
	public Stock getStockByDate(String product, Date dateInfo) {
		String hql = "from Stock t where t.product = '"+product+"' and  t.dateInfo='"+DateUtil.dateToShortString(dateInfo)+"'";
		Stock stock=(Stock) commonDao.queryUniqueByHql(hql);
		return stock;
	}

	public void cutStock(String product, Date dateInfo, int count,
			String clientCode, String systemType, String remark) {
		if(dateInfo==null){
			throw new MyExceptionForRole("缺少日期参数！");
		}
		if(product==null || "".equals(product)){
			throw new MyExceptionForRole("缺少产品参数！");
		}
		
		
		ProductObject productObject=productUtil.getProductByNumberCode(product);
		if(productObject==null){
			throw new MyExceptionForRole("产品不存在！");
		}
		
		Date date = new Date();
		if(count>0){
			Stock stock=getStockByDate(product, dateInfo);
			int countHave =stock.getCount();
			if(countHave<count){
				throw new MyExceptionForRole("库存不足！");
			}
			stock.setCount(countHave-count);
			commonDao.update(stock);
		}
		
		LogTsTemp logTsTemp = new LogTsTemp();
		logTsTemp.setAddDate(date);
		logTsTemp.setCount(count);
		logTsTemp.setDateInfo(dateInfo);
		logTsTemp.setNumberCode(UUIDUtil.getUUIDStringFor32());
		logTsTemp.setRemark("["+clientCode+"]"+remark);
		logTsTemp.setSystemType(systemType);
		logTsTemp.setProduct(productObject.getNumberCode());
		logTsTemp.setProductNameCn(productObject.getNameCn());
		commonDao.save(logTsTemp);
	}

}
