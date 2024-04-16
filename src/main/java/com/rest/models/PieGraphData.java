package com.rest.models;

public class PieGraphData {
	private int absent;
	@Override
	public String toString() {
		return "PieGraphData [absent=" + absent + ", present=" + present + "]";
	}
	public int getAbsent() {
		return absent;
	}
	public void setAbsent(int absent) {
		this.absent = absent;
	}
	public PieGraphData() {
		super();
	}
	public PieGraphData(int absent, int present) {
		super();
		this.absent = absent;
		this.present = present;
	}
	public int getPresent() {
		return present;
	}
	public void setPresent(int present) {
		this.present = present;
	}
	private int present;
	
	
	
}
