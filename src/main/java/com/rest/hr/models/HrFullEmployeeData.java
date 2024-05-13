package com.rest.hr.models;

import java.util.List;

public class HrFullEmployeeData {

private int id;
	private String name;
	private String pbId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String designation;
	private String division;
	private String phoneNo;
	private String programCode;
	private String punchInTime;
	private String punchOutPunch;
	private String date;
	private String punchSlot;
public String getPunchSlot() {
		return punchSlot;
	}
	public void setPunchSlot(String punchSlot) {
		this.punchSlot = punchSlot;
	}
public String getPunchInTime() {
		return punchInTime;
	}
	public void setPunchInTime(String punchInTime) {
		this.punchInTime = punchInTime;
	}
	public String getPunchOutPunch() {
		return punchOutPunch;
	}
	public void setPunchOutPunch(String punchOutPunch) {
		this.punchOutPunch = punchOutPunch;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
public HrFullEmployeeData() {
	super();
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPbId() {
	return pbId;
}
public void setPbId(String pbId) {
	this.pbId = pbId;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getDivision() {
	return division;
}
public void setDivision(String division) {
	this.division = division;
}
public String getPhoneNo() {
	return phoneNo;
}
public void setPhoneNo(String phoneNo) {
	this.phoneNo = phoneNo;
}
public String getProgramCode() {
	return programCode;
}
public void setProgramCode(String programCode) {
	this.programCode = programCode;
}
public HrFullEmployeeData(String name, String pbId, String designation, String division, String phoneNo,
		String programCode, String punchInTime, String punchOutPunch, String date, String punchSlot) {
	super();
	this.name = name;
	this.pbId = pbId;
	this.designation = designation;
	this.division = division;
	this.phoneNo = phoneNo;
	this.programCode = programCode;
	this.punchInTime = punchInTime;
	this.punchOutPunch = punchOutPunch;
	this.date = date;
	this.punchSlot = punchSlot;
}




	

}
