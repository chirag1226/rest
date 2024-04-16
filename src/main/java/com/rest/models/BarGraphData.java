package com.rest.models;

public class BarGraphData {
	private String session;
	private int value;
	public BarGraphData(String session, int value) {
		super();
		this.session = session;
		this.value = value;
	}
	public BarGraphData() {
		super();
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "BarGraphData [session=" + session + ", value=" + value + "]";
	}
	


	
	
}
