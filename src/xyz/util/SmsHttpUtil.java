package xyz.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 登陆平台的方法 
 * 1、http://web.c123.cn 
 * 2、企业编号:50091575 
 * 3、员工账户:1001 
 * 4、密码:1314520yy
 * 5、登陆后点击专业接口管理
 * 6、左边选择接口用户列表 7、选择 南京上好假期国际旅行社有限公司 点击后面的查看明细 OK 进入短信平台后，就可以查看收件发件了.
 * 7、客服电话 : 4001102818
 * 8、企业QQ : 4008885262	
 * 9、专属客服小张 ：15205510853
 */
// 平台接口登陆地址 http://211.144.76.29:90/main
// 这个地址可以直接登录接口平台 账号:500915750001 密码1314520yy
// 请勿删除
public class SmsHttpUtil {
	// 接口URL
	private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
	//状态URL
	private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";	
	// 接口帐号
	private static final String account = "1001@500915750001";
	// 接口密钥
	private static final String authkey = "94A01CEAEFE4152A18AA6984D95CE096";
	// 通道组编号
	private static final int cgid = 1428;
	// 默认使用的签名编号(未指定签名编号时传此值到服务器)
	private static final int csid = 2831;

	/**
	 * 发送短信 content : 短信内容 mobile : 电话号码 如发送多个，请用逗号隔开
	 * (不建议群发，建议循环调用此方法来发送，可以得到每条短信的发送状态) 请判断返回值的状态: >0表示发送成功
	 * 0:帐户格式不正确(正确的格式为:员工编号@企业编号) 
	 * -1:服务器拒绝(速度过快、限时或绑定IP不对等)如遇速度过快可延时再发 
	 * -2:密钥不正确
	 * -3:密钥已锁定 
	 * -4:参数不正确(内容和号码不能为空，手机号码数过多，发送时间错误等) 
	 * -5:无此帐户
	 * -6:帐户已锁定或已过期
	 * -7:帐户未开启接口发送
	 * -8:不可使用该通道组 
	 * -9:帐户余额不足 
	 * -10:内部错误
	 * -11:扣费失败
	 */
	public static Map<String, Object> sendSms(String content, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = ""; // 短信发送状态
		String msgid = ""; // 短信的唯一标示
		String cid = ""; // 发送短信的企业编号
		String sid = ""; // 发送短信的员工编号
		String total = ""; // 任务需要的短信条数
		String price = ""; // 任务中每条短信的价格
		String remain = ""; // 本次发送后企业的余额账户
		String cgidTemp = String.valueOf(cgid);
		String csidTemp = String.valueOf(csid);
		String contentTemp;
		StringBuilder sb = null;
		HttpURLConnection http = null;
		try {
			contentTemp = URLEncoder.encode(content, "UTF-8");
			String param1 = "?action=sendOnce&ac=" + account + "&authkey="
					+ authkey + "&cgid=" + cgidTemp + "&csid=" + csidTemp
					+ "&c=" + contentTemp + "&m=" + mobile;
			URL url;
			url = new URL(sOpenUrl + param1);
			http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setUseCaches(false);
			http.setConnectTimeout(10000);
			http.setReadTimeout(10000);
			http.setInstanceFollowRedirects(true);
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.connect();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					http.getOutputStream(), "UTF-8"));
			out.write(param1);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (http != null) {
				http.disconnect();
			}
		}
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(
					http.getInputStream(), "UTF-8"));
			String line = null;
			sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Document dom = DocumentHelper.parseText(sb.toString());
			Element root = dom.getRootElement();
			result = root.attributeValue("result");
			if ("1".equals(result)) {
				@SuppressWarnings("rawtypes")
				Iterator iter = root.elementIterator("Item");
				while (iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					msgid = recordEle.attributeValue("msgid");
					cid = recordEle.attributeValue("cid");
					sid = recordEle.attributeValue("sid");
					total = recordEle.attributeValue("total");
					price = recordEle.attributeValue("price");
					remain = recordEle.attributeValue("remain");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("result", result);
		map.put("cid", cid);
		map.put("sid", sid);
		map.put("msgid", msgid);
		map.put("total", total);
		map.put("price", price);
		map.put("remain", remain);
		return map;
	}

	/**
	 * 运营商返回状态
	 * DELIVRD:短消息转发成功
	 * EXPIRED:短消息超过有效期
	 * DELETED：短消息已经被删除
	 * UNDELIV：短消息是不可达的
	 * ACCEPTD：短消息在等待发送中
	 * UNKNOWN：未知短消息状态
	 * REJECTD：短消息被短信中心拒绝
	 * DTBLACK：目的号码是黑名单号码
	 * DTWORDS：发送内容被过滤
	 * DTFAILD：发送失败原因未明
	 * ERRBUSY:运营商系统忙状态未知
	 */
	public static List<Map<String, Object>> getSmsStatus() {		
		List<Map<String, Object>> smsStrutsList = new ArrayList<Map<String, Object>>();
		//String id = ""; // 序号，无实际意义
		//String msgid = ""; // 短信的唯一标示
		//String mobile = ""; // 接受号码
		//String result = ""; // 发送状态 0 未知状态 1 发送失败 2 发送成功
		//String returnDetail = ""; // 运营商返回  DELIVRD,EXPIRED,DELETED,UNDELIV,ACCEPTD,UNKNOWN,REJECTD, DTBLACK,DTWORDS,DTFAILD,ERRBUSY
		StringBuilder sb = null;
		HttpURLConnection http = null;
		try {
			String param1 = "?action=getSendState&ac="+account+"&authkey="+authkey;
			URL url;
			url = new URL(sDataUrl + param1);
			http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setUseCaches(false);
			http.setConnectTimeout(10000);
			http.setReadTimeout(10000);
			http.setInstanceFollowRedirects(true);
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.connect();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					http.getOutputStream(), "UTF-8"));
			out.write(param1);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (http != null) {
				http.disconnect();
			}
		}
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(
					http.getInputStream(), "UTF-8"));
			String line = null;
			sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			http.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Document dom = DocumentHelper.parseText(sb.toString());
			Element root = dom.getRootElement();
			String resultBig = root.attributeValue("result");
			if ("1".equals(resultBig)) {
				@SuppressWarnings("rawtypes")
				Iterator iter = root.elementIterator("Item");
				while (iter.hasNext()) {
					Element recordEle = (Element) iter.next();
					String id = recordEle.attributeValue("id");
					String msgid = recordEle.attributeValue("msgid");
					String mobile = recordEle.attributeValue("mobile");
					String result = recordEle.attributeValue("result");
					String returnDetail = recordEle.attributeValue("return");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", id);
					map.put("msgid", msgid);
					map.put("mobile", mobile);
					map.put("result", result);
					map.put("returnDetail", returnDetail);
					smsStrutsList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smsStrutsList;
	}
}
