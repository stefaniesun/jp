function isImage(value){
	if(value.indexOf("png")>0 || value.indexOf("PNG")>0 || value.indexOf("jpg")>0 || value.indexOf("JPG")>0
		|| value.indexOf("jpeg")>0 || value.indexOf("JPEG")>0 || value.indexOf("bmp")>0 || value.indexOf("BMP")>0){
		return true;
	}
	return false;
}
function isDOC(value){
	if(value.indexOf("doc")>0 || value.indexOf("DOC")>0 || value.indexOf("docx")>0 || value.indexOf("DOCX")>0){
		return true;
	}
	return false;
}
function isPDF(value){
	if(value.indexOf("pdf")>0 || value.indexOf("PDF")>0){
		return true;
	}
	return false;
}