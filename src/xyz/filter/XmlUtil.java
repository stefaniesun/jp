package xyz.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xyz.model.security.SecurityApi;
import xyz.model.security.SecurityFunction;
import xyz.model.security.SecurityUser;
import xyz.util.PathTool;

/**
 * 
 * 解析方法名和类名的工具
 *
 */
public class XmlUtil{
		
	public static  SecurityUser XmlUtilAdmin(String filePath){
		DocumentBuilderFactory dbFactory = null;
		DocumentBuilder dbBuilder = null;
		Document doc = null;
		InputStream  is = null;
		SecurityUser securityUser = new SecurityUser();
		  //得到DOM解析器的工厂实例
        dbFactory = DocumentBuilderFactory.newInstance();
       
		try {
			//从DOM工厂中获取DOM解析器
			dbBuilder = dbFactory.newDocumentBuilder();
			//把要解析的xml文档读入DOM解析器
			is = PathTool.class.getClassLoader().getResourceAsStream(filePath);
            doc = dbBuilder.parse(is);	        
	        //下面开始读取
	        NodeList nList = doc.getElementsByTagName("adminInfo");	       
                Element node = (Element)nList.item(0);
                String username = node.getAttributeNode("username")==null?null:node.getAttribute("username");
                String password = node.getAttributeNode("password")==null?null:node.getAttribute("password");
                String tokenNum = node.getAttributeNode("tokenNum")==null?null:node.getAttribute("tokenNum");
                securityUser.setUsername(username);
                securityUser.setPassword(password);
                securityUser.setTokenNum(tokenNum);
		} catch (Exception e) {
			e.printStackTrace();
		}  finally{
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}    
		return securityUser;
	}
	
	public static Map<String, Object> XmlUtilFunction(String filePath){
		DocumentBuilderFactory dbFactory = null;
		DocumentBuilder dbBuilder = null;
		Document doc = null;
		InputStream  is = null;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		List<Object[]> functionList = new ArrayList<Object[]>();
		List<SecurityApi> noList = new ArrayList<SecurityApi>();
		try {
	        //得到DOM解析器的工厂实例
            dbFactory = DocumentBuilderFactory.newInstance();
            //从DOM工厂中获得DOM解析器
            dbBuilder = dbFactory.newDocumentBuilder();
            //把要解析的xml文档读入DOM解析器
            //"src/config/sysFunction.xml"
            is = PathTool.class.getClassLoader().getResourceAsStream(filePath);
            doc = dbBuilder.parse(is);
	        //下面开始读取
            NodeList nList = doc.getElementsByTagName("function");
            for(int i = 0; i< nList.getLength() ; i ++){
                Element node = (Element)nList.item(i);
                String numberCode = node.getAttributeNode("numberCode")==null?null:node.getAttribute("numberCode");
                String nameCn = node.getAttributeNode("nameCn")==null?null:node.getAttribute("nameCn");
                String url = node.getAttributeNode("url")==null?null:node.getAttribute("url");
                String groupCn = node.getAttributeNode("groupCn")==null?null:node.getAttribute("groupCn");
                String isApi = node.getAttributeNode("isApi")==null?null:node.getAttribute("isApi");
        		SecurityFunction securityFunction = new SecurityFunction();
        		securityFunction.setNumberCode(numberCode);
        		securityFunction.setNameCn(nameCn);
        		securityFunction.setGroupCn(groupCn);
        		securityFunction.setUrl(url);
        		securityFunction.setIsApi(Integer.parseInt(isApi));
                List<SecurityApi> securityApiList = new ArrayList<SecurityApi>();
                NodeList nList1 = node.getElementsByTagName("api");               
                for(int j = 0; j< nList1.getLength() ; j ++){
                    Element node2 = (Element)nList1.item(j);
                    String nameCn2 = node2.getAttributeNode("nameCn")==null?null:node2.getAttribute("nameCn");
                    String url2 = node2.getAttributeNode("url")==null?null:node2.getAttribute("url");
                    String flagServer = node2.getAttributeNode("flagServer")==null?null:node2.getAttribute("flagServer");
                    String isWork = node2.getAttributeNode("isWork")==null?null:node2.getAttribute("isWork");
                    String buttonCode2 = node2.getAttributeNode("buttonCode")==null?null:node2.getAttribute("buttonCode");
                    int flagServer2 = flagServer==null||"".equals(flagServer)?0:Integer.parseInt(flagServer);
                    int isWork2 = isWork==null||"".equals(isWork)?0:Integer.parseInt(isWork);
                    
                    SecurityApi securityApi = new SecurityApi();
			        securityApi.setButtonCode(buttonCode2);
			        securityApi.setFlagServer(flagServer2);
			        securityApi.setIsWork(isWork2);
			        securityApi.setIsDecide(1);
			        securityApi.setNameCn(nameCn2);
			        securityApi.setUrl(url2);
			        securityApi.setFunction(numberCode);
			        securityApiList.add(securityApi);
                }
                Object[] t = new Object[]{securityFunction,securityApiList};
                functionList.add(t);
            }   
            
            NodeList pList = doc.getElementsByTagName("list");
            for(int i = 0; i< pList.getLength() ; i ++){
                Element node = (Element)pList.item(i);
                NodeList pList1 = node.getElementsByTagName("api");               
                for(int j = 0; j< pList1.getLength() ; j ++){
                	Element node2 = (Element)pList1.item(j);
                    String nameCn2 = node2.getAttributeNode("nameCn")==null?null:node2.getAttribute("nameCn");
                    String url2 = node2.getAttributeNode("url")==null?null:node2.getAttribute("url");
                    String flagServer = node2.getAttributeNode("flagServer")==null?null:node2.getAttribute("flagServer");
                    String isWork = node2.getAttributeNode("isWork")==null?null:node2.getAttribute("isWork");
                    String buttonCode2 = node2.getAttributeNode("buttonCode")==null?null:node2.getAttribute("buttonCode");
                    int flagServer2 = flagServer==null||"".equals(flagServer)?0:Integer.parseInt(flagServer);
                    int isWork2 = isWork==null||"".equals(isWork)?0:Integer.parseInt(isWork);

                    SecurityApi securityApi = new SecurityApi();
			        securityApi.setButtonCode(buttonCode2);
			        securityApi.setFlagServer(flagServer2);
			        securityApi.setIsWork(isWork2);
			        securityApi.setIsDecide(0);
			        securityApi.setNameCn(nameCn2);
			        securityApi.setUrl(url2);
			        securityApi.setFunction(null);
			        noList.add(securityApi);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{			
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mapResult.put("functionList", functionList);
		mapResult.put("noList", noList);
        return mapResult;
	}
}

