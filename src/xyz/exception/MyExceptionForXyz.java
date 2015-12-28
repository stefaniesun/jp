package xyz.exception;


public class MyExceptionForXyz extends RuntimeException{
	private static final long serialVersionUID = -6621687416265970915L;
	private String  message;
	public MyExceptionForXyz(){
		this.message = "未知异常！";
	}
	public MyExceptionForXyz(String message){
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
