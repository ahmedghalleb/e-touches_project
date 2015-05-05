package com.echances.etouches.model;

public class LoginResponse extends Response{
	
	// {"code":1,"message":"Login success","result":{"id":1,"cid":0,"tid":2,"un":"","ta":"","ia":"","mb":"00966569014457","acc":0}}

	User result;
	
	public User getResult() {
		return result;
	}
	
	
}
