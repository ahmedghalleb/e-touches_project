package com.echances.etouches.model;

import java.util.ArrayList;

public class OneService {
	
	//{"ID":2,"D":1,"P":1,"SAR":"\u0645\u0643\u064A\u0627\u062C","SEN":"make up","AAR":"\u0633\u064A\u0628\u0633\u064A\u0628\u0633\u064A\u0628 \u0634\u0633\u064A\u0628\u0633\u064A\u0628","AEN":"ddd dfsedff fsef fsfserf","Gallery":[]}
	int ID;
	int D;
	int P;
	String SAR;
	String SEN;
	String AAR;
	String AEN;
	ArrayList<GalleryImage> Gallery;
	
	public int getID() {
		return ID;
	}
	public String getSAR() {
		return SAR;
	}
	public String getSEN() {
		return SEN;
	}
	public int getD() {
		return D;
	}
	public int getP() {
		return P;
	}
	public String getAAR() {
		return AAR;
	}
	public String getAEN() {
		return AEN;
	}
	public ArrayList<GalleryImage> getGallery() {
		return Gallery;
	}
	
}
