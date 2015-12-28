package xyz.filter;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;



public class JSON {
	
private static MvcJson objMapper;
	
	static {
		objMapper = new MvcJson();
		objMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
	}
	
	/**
	 *  解析JAVA对象为json对象字符串
	 * @param obj				对象
	 * @param attrs				属性数组
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String toJosn(Object obj){
		if(obj instanceof String){
			return obj.toString();
		}
		try {
			return objMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	// 将Json字符串转换为相应的对象
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String pstrJson, Class<T> cla){
		if(cla.getSimpleName().equals("String")){
			return (T)pstrJson;
		}
		try {
			return objMapper.readValue(pstrJson, cla);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
