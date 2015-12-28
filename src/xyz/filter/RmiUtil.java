package xyz.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import xyz.util.Constant;

@Component
public class RmiUtil{
	@SuppressWarnings("unchecked")
	public Object loadData(
			HttpServletRequest request,
			String url,
			Map<String, String> accessoryParam){
		Map<String,Object[]> parameters = request.getParameterMap();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
		for(String p : parameters.keySet()){
			Object[] parameter = parameters.get(p);
			if(parameter!=null){
				for(Object para : parameter){
					parameterList.add(new BasicNameValuePair(p, para.toString()));
				}
			}
		}
		for(String key : accessoryParam.keySet()){
			parameterList.add(new BasicNameValuePair(key,accessoryParam.get(key)));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(parameterList,"utf8");
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Cookie", request.getHeader("Cookie"));
			httpPost.setHeader("X-Requested-With","XMLHttpRequest");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Object result = null;
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity2 = httpResponse.getEntity();
				byte[] bytes = EntityUtils.toByteArray(httpEntity2);
				String resultStr = new String(bytes,"utf-8");
				resultStr = resultStr.trim();
				if("".equals(resultStr)){
					result = "";
				}else if("[".equals(resultStr.substring(0, 1))){
					result = JSON.toObject(resultStr,List.class);
				}else if("{".equals(resultStr.substring(0, 1))){
					result = JSON.toObject(resultStr,Map.class);
				}else{
					result = "";
				}
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Constant.result_status, 0);
				map.put(Constant.result_msg, "http连接错误("+httpResponse.getStatusLine().getStatusCode()+")");
				result = map;
			}
			httpClient.close();
			return result;
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return ReturnUtil.returnMap(0,e.getMessage());
		}catch(IOException e) {
			e.printStackTrace();
			return ReturnUtil.returnMap(0,e.getMessage());
		}
	}
	
	public Object loadData(
			String url,
			Map<String, String> accessoryParam){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
		for(String key : accessoryParam.keySet()){
			parameterList.add(new BasicNameValuePair(key,accessoryParam.get(key)));
		}
		try {
			HttpEntity httpEntity = new UrlEncodedFormEntity(parameterList,"utf8");
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("X-Requested-With","XMLHttpRequest");
			httpPost.setEntity(httpEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Object result = null;
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity httpEntity2 = httpResponse.getEntity();
				byte[] bytes = EntityUtils.toByteArray(httpEntity2);
				String resultStr = new String(bytes,"utf-8");
				resultStr = resultStr.trim();
				if("".equals(resultStr)){
					result = "";
				}else if("[".equals(resultStr.substring(0, 1))){
					result = JSON.toObject(resultStr,List.class);
				}else if("{".equals(resultStr.substring(0, 1))){
					result = JSON.toObject(resultStr,Map.class);
				}else{
					result = "";
				}
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Constant.result_status, 0);
				map.put(Constant.result_msg, "http连接错误("+httpResponse.getStatusLine().getStatusCode()+")");
				result = map;
			}
			httpClient.close();
			return result;
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return ReturnUtil.returnMap(0,e.getMessage());
		}catch(IOException e) {
			e.printStackTrace();
			return ReturnUtil.returnMap(0,e.getMessage());
		}
	}
}
