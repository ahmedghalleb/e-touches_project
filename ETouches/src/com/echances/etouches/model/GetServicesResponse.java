package com.echances.etouches.model;

import java.util.ArrayList;

public class GetServicesResponse extends Response{
	
	// {"code":1,"message":"Login success","result":{"id":1,"cid":0,"tid":2,"un":"","ta":"","ia":"","mb":"00966569014457","acc":0}}

	ArrayList<Service> Servics;

	public ArrayList<Service> getServics() {
		return Servics;
	}
	
}
