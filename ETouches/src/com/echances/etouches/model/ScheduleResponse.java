package com.echances.etouches.model;

import java.util.ArrayList;

public class ScheduleResponse extends Response{
	
	ArrayList<Integer> SAT;
	ArrayList<Integer> SUN;
	ArrayList<Integer> MON;
	ArrayList<Integer> TUE;
	ArrayList<Integer> WED;
	ArrayList<Integer> THU;
	ArrayList<Integer> FRI;
	
	public ArrayList<Integer> getSAT() {
		return SAT;
	}
	public void setSAT(ArrayList<Integer> sAT) {
		SAT = sAT;
	}
	public ArrayList<Integer> getSUN() {
		return SUN;
	}
	public void setSUN(ArrayList<Integer> sUN) {
		SUN = sUN;
	}
	public ArrayList<Integer> getMON() {
		return MON;
	}
	public void setMON(ArrayList<Integer> mON) {
		MON = mON;
	}
	public ArrayList<Integer> getTUE() {
		return TUE;
	}
	public void setTUE(ArrayList<Integer> tUE) {
		TUE = tUE;
	}
	public ArrayList<Integer> getWED() {
		return WED;
	}
	public void setWED(ArrayList<Integer> wED) {
		WED = wED;
	}
	public ArrayList<Integer> getTHU() {
		return THU;
	}
	public void setTHU(ArrayList<Integer> tHU) {
		THU = tHU;
	}
	public ArrayList<Integer> getFRI() {
		return FRI;
	}
	public void setFRI(ArrayList<Integer> fRI) {
		FRI = fRI;
	}
	
	public void initArrays(){
		SAT = new ArrayList<Integer>();
		SUN = new ArrayList<Integer>();
		MON = new ArrayList<Integer>();
		TUE = new ArrayList<Integer>();
		WED = new ArrayList<Integer>();
		THU = new ArrayList<Integer>();
		FRI = new ArrayList<Integer>();
	}
}
