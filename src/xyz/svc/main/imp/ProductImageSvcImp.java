package xyz.svc.main.imp;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.dao.CommonDao;
import xyz.filter.ReturnUtil;
import xyz.model.main.ProductImage;
import xyz.svc.main.ProductImageSvc;
import xyz.util.UUIDUtil;

@Service
public class ProductImageSvcImp implements ProductImageSvc {

	
	@Autowired
	CommonDao commonDao;
	
	@Override
	public ProductImage addProductImage(File targetFile) {
		ProductImage image=new ProductImage();
		
		image.setNumberCode(UUIDUtil.getUUIDStringFor32());
		image.setAddDate(new Date());
		image.setUrl(targetFile.getAbsolutePath());
		commonDao.save(image);
		return image;
	}

}
