
package xyz.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.transaction.interceptor.TransactionAspectSupport;


public class HtmlUtil {
	/**
	 * 将本地模板替换为输出的静态页面
	 * @param oldFilePath 本地模板文件地址
	 * @param newFilePath 替换后的html地址
	 * @param replaceOldStrs 本地模板文件中需替换的字符串组
	 * @param replaceNewStrs 新的字符串组，用来替换replaceOldStrs
	 * @return boo true代表替换成功，false代表替换失败
	 */
	public static boolean toHtmlByLocalFile(String oldFilePath, String newFilePath,
										String [] replaceOldStrs,String [] replaceNewStrs) { 
		
			String oldFileStr = "";//老文件字符串
			String newFileStr = "";//新文件字符串
			boolean boo = false;//返回值
			String tempStr = ""; //临时字符串
			String chartSet = "utf-8";
			try {
				/*
				 * 读取本地模板文件，并转换为字符串
				 */
				FileInputStream is = new FileInputStream(oldFilePath);//读取模块文件 
				BufferedReader br = new BufferedReader(new InputStreamReader(is,chartSet)); 
				while ((tempStr = br.readLine()) != null)
					oldFileStr += tempStr ;
				is.close();
				
				/*
				 * 替换
				 */
				if(replaceOldStrs!=null && replaceOldStrs.length>0){
					for(int i=0;i<replaceOldStrs.length;i++){
						oldFileStr = oldFileStr.replaceAll(replaceOldStrs[i],replaceNewStrs[i]);
					}
				}
				newFileStr = oldFileStr;
				
				/*
				 * 将替换后的字符串写入指定目录中
				 */
				File file = new File(newFilePath); 
				BufferedWriter o = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),chartSet));
				o.write(newFileStr);
				o.flush();
				o.close(); 
				boo = true;
			}catch (IOException e){ 
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return boo;
			}catch (Exception e){
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return boo;
			}
			return boo;
	} //toHtmlByLocal方法结束
	
}//类文件结束
