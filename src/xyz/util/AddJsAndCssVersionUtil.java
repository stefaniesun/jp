package xyz.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 清理缓存工具类
 * 执行即可清理制定目录下JS和CSS的缓存
 * @author Administrator
 *
 */
public class AddJsAndCssVersionUtil {
	
	public static void main(String[] args) {
			//本机测试
//			String path ="D:\\yaochengcheng\\workspace\\xztrip\\WebRoot\\xyzsecurity";
			//服务器正式
//			String path ="C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\项目名称";
		   
			String[] paths = new String[]{
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\xyzsecurity",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\xyzmail",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\xyzaccessory",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\jsp_ali",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\jsp_base",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\jsp_core",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\jsp_hx",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\jsp_main",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\jsp_stat",
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\xztrip\\jsp_zhifubao"
			};
			
			Date date = new Date();  
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");  
	        String version = df.format(date); 
	        
	        for(String path:paths){
	        	addVersionToJSAndCss(path, version);
	        }
	        System.out.println("执行完成!!!!");
	}

	public static void addVersionToJSAndCss(String path, String version) {  
	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    if (files == null)
	        return;
	    for (int i = 0; i < files.length; i++) {
	        if (files[i].isDirectory()) {
	        	addVersionToJSAndCss(files[i].getAbsolutePath(), version);
	        } else {
	            String strFileName = files[i].getAbsolutePath().toLowerCase();
	            // 如果是符合条件的文件，则添加版本信息  
	            if (strFileName.endsWith(".html")
	                || strFileName.endsWith(".jsp")) {
	                InputStream is = null;  
	                OutputStream os = null;  
	                List<String> contentList = new ArrayList<String>();  
	                // 读文件  
	                try {  
	                    is = new FileInputStream(files[i]);  
	                    Reader r = new InputStreamReader(is);  
	                    BufferedReader br = new BufferedReader(r);  
	                    String line = null;  
	                    while ((line = br.readLine()) != null) {  
	                        String modLine = getModLine(line, version);  
	                        if (modLine != null) {  
	                            line = modLine;  
	                        }  
	                        line = line + "\r\n";  
	                        contentList.add(line);  
	                    }  
	                    // 关闭流  
	                    br.close();  
	                    r.close();  
	                } catch (Exception e) {  
	                    System.out.println("读文件失败");  
	                    e.printStackTrace();  
	                } finally {  
	                    if (null != is) {  
	                        try {  
	                            is.close();  
	                        } catch (Exception e) {  
	                            e.printStackTrace();  
	                        }  
	                    }  
	
	                }  
	
	                // 写文件  
	                try {  
	                    os = new FileOutputStream(files[i]);  
	                    Writer w = new OutputStreamWriter(os,"utf8");
	                    BufferedWriter bw = new BufferedWriter(w);  
	                    for (Iterator<String> it = contentList.iterator(); it  
	                            .hasNext();) {  
	                        String line = it.next();  
	                        bw.write(line);  
	                    }  
	                    // 更新到文件  
	                    bw.flush();  
	                    // 关闭流  
	                    bw.close();  
	                    w.close();  
	                } catch (Exception e) {  
	                    System.out.println("写文件失败");  
	                    e.printStackTrace();  
	                } finally {  
	                    if (null != os) {  
	                        try {  
	                            os.close();  
	                        } catch (Exception e) {  
	                            e.printStackTrace();  
	                        }  
	                    }  
	                }  
	            }  
	        }  
	    }  
	}  

	public static String getModLine(String line, String version) {  
	    // 增加js版本  
	    line.trim();  
	    if (line.startsWith("<script") && line.endsWith("</script>")) {  
	        int pos = line.indexOf(".js");  
	        String modLine = line.substring(0, pos) + ".js?version="  
	                + version + "\"";
	        if(line.contains("defer")){
	        	modLine += " defer=\"defer\"";
	        }
	        modLine += "></script>";  
	        return modLine;  
	    } else if (line.startsWith("<link")  
	    	&& line.endsWith("</link>")) {  
	        int pos = line.indexOf(".css");  
	        String modLine = line.substring(0, pos) + ".css?version="  
	                + version+ "\"></link>";  
	        return modLine;  
	    } else {  
	        return null;  
	    }  
	}  
}