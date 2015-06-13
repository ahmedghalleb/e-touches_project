package com.echances.etouches.model;

public class GalleryImage extends Response{
	
	//{"ID":5,"img":"92b4daf2.jpg","url":"http://itouches.net/public/services/92b4daf2.jpg","Surl":"http://itouches.net/public/services/S/92b4daf2.jpg","Murl":"http://itouches.net/public/services/M/92b4daf2.jpg"}
	int ID;
	String img;
	String url;
	String Surl;
	String Murl;
	
	public int getID() {
		return ID;
	}

	public String getImg() {
		return img;
	}

	public String getUrl() {
		return url;
	}

	public String getSurl() {
		return Surl;
	}

	public String getMurl() {
		return Murl;
	}

	
}
