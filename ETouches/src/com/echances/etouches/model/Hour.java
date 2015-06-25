package com.echances.etouches.model;

public class Hour {
	
	private int value;
	private String text;
	
	public Hour(int value, String text) {
		super();
		this.value = value;
		this.text = text;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
