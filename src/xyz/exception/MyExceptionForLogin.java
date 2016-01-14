package xyz.exception;


public class MyExceptionForLogin extends RuntimeException{
	private static final long serialVersionUID = -4606084306118628275L;
	private String  message;
	private int value=0;
	public MyExceptionForLogin(){
		this.message = "请重新登录！";
	}
	public MyExceptionForLogin(String message){
		this.message = message;
	}
	public  String getMessage() {
		return  message;
	}
	public   void  setMessage(String message) {
		this.message = message;
	}
	@Override
	public  Throwable fillInStackTrace() {
		return this ;
	}
}
