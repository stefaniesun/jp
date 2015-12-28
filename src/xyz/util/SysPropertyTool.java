package xyz.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysPropertyTool {
	private SysPropertyTool(){};
	private static Properties p = new Properties();
	private static String path = "config/sys.properties";
	//静态代码块，初始化配置文件
	static{
		try {
			InputStream  is = SysPropertyTool.class.getClassLoader().getResourceAsStream(path);
			p.load(is);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//此方法通过在配置文件里靠键取值
	public static String getValue(String key){
		return p.getProperty(key);
	}
}
