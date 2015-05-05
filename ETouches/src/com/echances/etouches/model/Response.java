package com.echances.etouches.model;

public class Response {
	
	int code;
	String message;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isSucces(){
		return getCode() == 1 || getCode() == 0;
	}
}
