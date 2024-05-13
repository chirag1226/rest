package com.rest.hr.models;

public class HrEmployeePunchDate {
	
	private String punchInTime;
	private String date;
	private String PunchOutTime;
	private String punchSlot;
	public String getPunchInTime() {
		return punchInTime;
	}
	public void setPunchInTime(String punchInTime) {
		this.punchInTime = punchInTime;
	}
	public String getPunchSlot() {
		return punchSlot;
	}
	public void setPunchSlot(String punchSlot) {
		this.punchSlot = punchSlot;
	}
	public HrEmployeePunchDate() {
		super();
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPunchOutTime() {
		return PunchOutTime;
	}
	public void setPunchOutTime(String punchOutTime) {
		PunchOutTime = punchOutTime;
	}
	public HrEmployeePunchDate(String punchInTime, String date, String punchOutTime, String punchSlot) {
		super();
		this.punchInTime = punchInTime;
		this.date = date;
		PunchOutTime = punchOutTime;
		this.punchSlot = punchSlot;
	}

}
