package xyz.util;

import java.io.File;

public class FileTool {
	private FileTool(){}
	
	public static void deleteAllFolder(File path) {  
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	    	deleteAllFolder(files[i]);
	    }  
	    path.delete();
	}
	
	public static void deleteChildFolder(File path) {  
	    if (!path.exists())
	        return;  
	    if (path.isDirectory()){  
	    	File[] files = path.listFiles();  
		    for (int i = 0; i < files.length; i++) {  
		    	deleteAllFolder(files[i]);  
		    }  
	    }else{
	    	return;
	    } 
	}
	
	public static void deletePath(String path) {  
		File file = new File(path);
		deleteAllFolder(file);
	}
	
	public static void deleteChildPath(String path) {  
		File file = new File(path);
		deleteChildFolder(file);
	}
	
	public static boolean existsPath(String path) {
		File file = new File(path);
	    return file.exists();
	}
}
