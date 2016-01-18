package xyz.svc.main;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Service;

import xyz.model.main.ProductImage;

@Service
public interface ProductImageSvc {

	ProductImage addProductImage(File targetFile);

}
