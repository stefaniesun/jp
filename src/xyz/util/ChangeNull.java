package xyz.util;

import java.lang.reflect.Field;


public class ChangeNull {
	private ChangeNull(){}
	public static Object changeFieldToNull(Object object){
		Field[] fields = object.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);  
			if(String.class.equals(field.getGenericType())){
				try {
					if("".equals(field.get(object))){
						field.set(object, null);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return object;
	}
}
