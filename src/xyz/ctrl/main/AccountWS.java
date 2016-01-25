package xyz.ctrl.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.config.AlipayNotify;
import xyz.svc.main.AccountSvc;
import xyz.svc.main.OrderSvc;

@Controller
@RequestMapping(value="/AccountWS")
public class AccountWS {

	@Autowired
	AccountSvc accountSvc;
	
	@Autowired
	OrderSvc orderSvc;
	
	@RequestMapping(value="aliPayConfirmOper")
	@ResponseBody
	public String aliPayConfirmOper(HttpServletRequest request,HttpServletResponse response){
		//获取支付宝GET过来反馈信息
				Map<String,String> params = new HashMap<String,String>();
				@SuppressWarnings("unchecked")
				Map<String,Object> requestParams = request.getParameterMap();
				for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					System.out.println("name="+name);
					String[] values = (String[]) requestParams.get(name);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr + values[i]
								: valueStr + values[i] + ",";
					}
					//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//										valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
					System.out.println("valueStr="+valueStr);
					params.put(name, valueStr);
				}
				
				//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
				//商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no"));
				System.out.println("商户订单号="+out_trade_no);
				//支付宝交易号

				String trade_no = new String(request.getParameter("trade_no"));
				System.out.println("支付宝交易号="+trade_no);
				//交易状态
				String trade_status = new String(request.getParameter("trade_status"));
				System.out.println("交易状态="+trade_status);
				
				//买家（付款）对象
				String buyer_email = new String(request.getParameter("buyer_email"));
				System.out.println("买家（付款）对象="+buyer_email);
				
				String subject = new String(request.getParameter("subject"));
				System.out.println("交易科目="+subject);
				//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
				
				//计算得出通知验证结果
				boolean verify_result = AlipayNotify.verify(params);
				
				if(verify_result){//验证成功
					//////////////////////////////////////////////////////////////////////////////////////////
					//请在这里加上商户的业务逻辑程序代码

					//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
					if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						System.out.println("商品订单号="+out_trade_no);
						orderSvc.orderPayConfirmOper(out_trade_no);
						
						 response.setContentType("text/html;charset=utf-8");  
						    //response.setCharacterEncoding("UTF-8");  
						    PrintWriter out = null;
							try {
								out = response.getWriter();
								out.print("success");  
								out.flush();  
							} catch (IOException e) {
								e.printStackTrace();
							}finally{
							    out.close();
							}   
						return "forward:../xyzpay/200_paySuccess.html";
					}
					//该页面可做页面美工编辑
				
					//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

					//////////////////////////////////////////////////////////////////////////////////////////
				}else{
					//该页面可做页面美工编辑
				
				}
				return "forward:../xyzpay/100_payFail.html";
	}
	
	
	
}
