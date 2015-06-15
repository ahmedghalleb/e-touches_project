package com.echances.etouches.model;

public class Profile{
	
	// {"id":33,"cid":0,"lng":"9.818604","lat":"31.603126","tid":2,"un":"Med ALi","ta":"","ia":"","mb":"0021653176180","acc":0,"ServiceFromWorkingPlace":0,"ServiceOutsideWorkingPlace":1,"ServiceForMale":0,"ServiceFromWomen":1,"CashonDelivery":0}
	
	int id;
	int cid;
	int tid;
	String un;
	String ta;
	String ia;
	String mb;
	String lng;
	String lat;
	int acc;
	int ServiceFromWorkingPlace;
	int ServiceOutsideWorkingPlace;
	int ServiceForMale;
	int ServiceFromWomen;
	int CashonDelivery;
	
	public int getId() {
		return id;
	}
	public int getCid() {
		return cid;
	}
	public int getTid() {
		return tid;
	}
	public String getUn() {
		return un;
	}
	public String getTa() {
		return ta;
	}
	public String getIa() {
		return ia;
	}
	public String getMb() {
		return mb;
	}
	public int getAcc() {
		return acc;
	}
	public int getServiceFromWorkingPlace() {
		return ServiceFromWorkingPlace;
	}
	public int getServiceOutsideWorkingPlace() {
		return ServiceOutsideWorkingPlace;
	}
	public int getServiceForMale() {
		return ServiceForMale;
	}
	public int getServiceFromWomen() {
		return ServiceFromWomen;
	}
	public int getCashonDelivery() {
		return CashonDelivery;
	}
	public String getLng() {
		return lng;
	}
	public String getLat() {
		return lat;
	}

}