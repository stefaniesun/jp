package xyz.util;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.exception.MyExceptionForXyz;



/*
 * 用于 工具 - SQL管理的异库查询 
 */
public class JDBCUtil {
	
	private static String mySqlClassName = "com.mysql.jdbc.Driver"; //默认MySQL
	
	public static List<Object[]> getData(String dataClass ,String connectionStr ,String sql ,int colCount){
		
		if(dataClass == null || "".equals(dataClass) || connectionStr == null || "".equals(connectionStr) || sql == null || "".equals(sql) || colCount<1){
			throw new MyExceptionForXyz("参数错误001!");
		}
		
		String className = "";
		
		if("mysql".equals(dataClass)){
			className = mySqlClassName;
		}else{
			throw new MyExceptionForXyz("目前仅支持MySQL!");
		}
		
		List<Object[]> resultList = new ArrayList<Object[]>();
		Connection connection = null;
		
        try {
            Class.forName(className);
            
            connection = DriverManager.getConnection(connectionStr);
            connection.setReadOnly(true);//设置只读.
            Statement statement = connection.createStatement();
            
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {  
            	Object[] objs = new Object[colCount];
            	for(int i=0;i<colCount;i++){
            		objs[i] = resultSet.getObject(i+1);
            	}
            	resultList.add(objs);
            }
            resultSet.close();
            statement.close();
        }catch(Exception e){
            throw new MyExceptionForXyz(e.getMessage());
        }finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
		
		return resultList;
		
	}
	
	public static Map<String,Object> getDataForPage(String dataClass ,String connectionStr,String sql , int colCount, int offset,int pagesize){
		
		if(dataClass == null || "".equals(dataClass) || connectionStr == null || "".equals(connectionStr) || sql == null || "".equals(sql) || colCount < 1){
			throw new MyExceptionForXyz("参数错误002!");
		}
		
		String className = "";
		
		if("mysql".equals(dataClass)){
			className = mySqlClassName;
		}else{
			throw new MyExceptionForXyz("目前仅支持MySQL!");
		}
		
		Map<String ,Object> resultMap = new HashMap<String, Object>();
		List<Object[]> resultList = new ArrayList<Object[]>();
		Connection connection = null;
		
        try {
            Class.forName(className);
            
            connection = DriverManager.getConnection(connectionStr);
            connection.setReadOnly(true);//设置只读.
            Statement statement = connection.createStatement();
            
            String countHql = "select count(*) as countData from ("+sql+") as `"+UUIDUtil.getUUIDStringFor32()+"`"; //避免别名重复
            
            ResultSet resultSet = statement.executeQuery(countHql);
            Object count = 0;
            if(resultSet.next()){
            	count = resultSet.getLong(1);
            }
            resultSet = statement.executeQuery(sql + " limit " + offset + "," + pagesize);
            
            while(resultSet.next()){
            	Object[] objs = new Object[colCount];
            	for(int i=0;i<colCount;i++){
            		objs[i] = resultSet.getObject(i+1);
            	}
            	resultList.add(objs);
            }
            
            resultMap.put("total", count);
            resultMap.put("rows", resultList);
            
            resultSet.close();
            statement.close();
        }catch(Exception e){
            throw new MyExceptionForXyz(e.getMessage());
        }finally{
			try {
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
        }
		
		return resultMap;
	}
}
