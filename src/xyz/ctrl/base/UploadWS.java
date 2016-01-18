package xyz.ctrl.base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;
import xyz.model.main.ProductImage;
import xyz.svc.main.ProductImageSvc;
import xyz.util.StringUtil;
import xyz.util.UUIDUtil;

@Controller
@RequestMapping(value="/UploadWS")
public class UploadWS {

	@Autowired
	private ProductImageSvc productImageSvc;
	
	@RequestMapping(value="uploadImage")
	  public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response) {  

        String path = request.getSession().getServletContext().getRealPath("upload/productImage");  
        String fileName = file.getOriginalFilename();  

        String type=fileName.substring(fileName.indexOf("."));
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmm"); 
        fileName=format.format(new Date())+StringUtil.getRandomStr(4)+type;

        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile);
          
            ProductImage image=productImageSvc.addProductImage(targetFile);
            response.setContentType("text/html;charset=utf-8");  
            response.setStatus(200);
		    PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(image.getNumberCode());  
				out.flush();  
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
			    out.close();
			}  
            return ReturnUtil.returnMap(1,null);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    	return ReturnUtil.returnMap(0,null);
    }  
	
	
	
	@RequestMapping(value="uploadContentImage")
	  public Map<String, Object> uploadContentImage(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response) {  

      String path = request.getSession().getServletContext().getRealPath("upload/productImage");  
      String fileName = file.getOriginalFilename();  

      String type=fileName.substring(fileName.indexOf("."));
      SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmm"); 
      fileName=format.format(new Date())+StringUtil.getRandomStr(4)+type;

      File targetFile = new File(path, fileName);  
      if(!targetFile.exists()){  
          targetFile.mkdirs();  
      }  
      //保存  
      try {  
          file.transferTo(targetFile);
        
          response.setContentType("text/html;charset=utf-8");  
          response.setStatus(200);
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
          return ReturnUtil.returnMap(1,null);
      } catch (Exception e) {  
          e.printStackTrace();  
      }  

  	return ReturnUtil.returnMap(0,null);
  } 
}
