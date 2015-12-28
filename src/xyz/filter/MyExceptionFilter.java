package xyz.filter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import xyz.exception.MyExceptionUtil;
import xyz.util.Constant;
import xyz.util.DateUtil;

public class MyExceptionFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		;
	}

	@Override
	public void doFilter(
			ServletRequest request1, 
			ServletResponse response1,
			FilterChain chain) 
					throws IOException, ServletException {
		try{
			chain.doFilter(request1, response1);
		}catch(Exception ex){
			Throwable newEx = MyExceptionUtil.handleException(ex);
			HttpServletRequest request = (HttpServletRequest)request1;
			HttpServletResponse response = (HttpServletResponse)response1;
			String exString = newEx.getClass().getSimpleName();
			Map<String, Object> map = new HashMap<String, Object>();
			String model = "../xyzsecurity/100_exception.html";
			if("MySQLIntegrityConstraintViolationException".equals(exString)){
				map.put(Constant.result_msg, "操作失败：您可能正在创建已经存在的数据!");
			}else if("StaleObjectStateException".equals(exString)){
				map.put(Constant.result_msg, "操作失败：数据可能已被其他人修改，请刷新后重试！");
			}else if("HttpMessageNotWritableException".equals(exString)){
				;
			}else if(exString.contains("MyExceptionFor")){
				map.put(Constant.result_msg, "操作失败："+newEx.getMessage()+"");
			}else if(exString.contains("SizeLimitExceededException")){
				map.put(Constant.result_msg, "系统异常：文件尺寸超过限制！");
			}else{
				map.put(Constant.result_msg, "系统异常：请将整个网页截图给系统管理员！【"+newEx.getMessage()+"】");
			}
			
			StringBuffer otherInfo = new StringBuffer();
			otherInfo.append("ip:"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"\r\n");
			otherInfo.append("servletPath:"+request.getServletPath()+"\r\n");
			otherInfo.append("param:"+JSON.toJosn(request.getParameterMap())+"\r\n");
			otherInfo.append("cookies:"+JSON.toJosn(request.getCookies())+"\r\n");
			outputException(newEx, otherInfo.toString());//输出异常到文件
			
			boolean isAjax = false;
			String requestType =(String)request.getHeader("X-Requested-With");
			if(requestType != null && requestType.equals("XMLHttpRequest")){
				isAjax = true;
			}
			if(ServletFileUpload.isMultipartContent(request)){
				isAjax = true;
			}
			if (isAjax) {
				map.put(Constant.result_status, 0);
				PrintWriter pw = null;
				try {
					if(response!=null){
						response.setCharacterEncoding("utf-8");
						response.setContentType("text/json;charset=utf-8");
						pw = response.getWriter();
						if(pw!=null){
							pw.print(JSON.toJosn(map));
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}finally{
					if(pw!=null){
						pw.close();
					}
				}
			}else{
				request.getRequestDispatcher(model).forward(request, response);
			}
		}
	}
	
	@Override
	public void destroy() {
		;
	}
	
	public void outputException(Throwable ex,String otherInfo) throws IOException{
		String time = DateUtil.dateToShortString(new Date());
		String filePath = "c:/xyzexceptioninfo/";
		String fileName = "ex_"+time+".log";
		StringBuffer info = new StringBuffer("\r\n\r\n");
		info.append(DateUtil.dateToString(new Date()));
		info.append("\r\n<\r\n");
		info.append(otherInfo);
		info.append(">\r\n");
		Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        info.append(writer.toString());
        File dir = new File(filePath);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        File file = new File(dir, fileName);
        if(!file.exists()){
        	file.createNewFile();
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath+fileName, true)));   
        out.write(info.toString());
       	out.close();
	}
}
