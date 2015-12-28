package xyz.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PathTool {
	private PathTool(){};
	private static Properties p = new Properties();
	private static String path = "config/file_path.properties";
	//静态代码块，初始化配置文件
	static{
		try {
			InputStream  is = PathTool.class.getClassLoader().getResourceAsStream(path);
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
