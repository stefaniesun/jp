package xyz.ctrl.base;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import xyz.filter.ReturnUtil;
import xyz.filter.RmiUtil;

@Controller
@RequestMapping(value="/UploadWS")
public class UploadWS {

	@RequestMapping(value="uploadImage")
	  public Map<String, Object> upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {  

        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  

        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    	return ReturnUtil.returnMap(1,null);
    }  
}
