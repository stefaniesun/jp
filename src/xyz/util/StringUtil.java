package xyz.util;

import java.text.SimpleDateFormat;
import java.util.Date;




public class StringUtil {
	private StringUtil(){}
	private static long orderNum = System.currentTimeMillis();//订单编号
	private static long product = System.currentTimeMillis()/1000;//票号
	private static SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String getOrderNum(){
		Date date=new Date();
		return format.format(date);
	}
	
	public static String get_new_product(String type){
		long curt = System.currentTimeMillis()/1000;
		if(curt>product){
			product=curt;
		}else{
			curt = ++product;
		}
		String cClientCode = type+Long.toString(curt, 36).toUpperCase();
		return cClientCode;
	}
	
	public static String getRandomStr(int scale){
		String temp = "";
		while(temp.length()<scale){
			temp = temp+(int)(Math.random()*10);
		}
		return temp;
	}
	
	public static String getScaleStr(int scale,int number){
		String temp = number+"";
		while(temp.length()<scale){
			temp = 0+temp;
		}
		return temp;
	}
}
