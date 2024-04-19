package com.rest.models;

public class EmployeeAttendanceData {
	public EmployeeAttendanceData() {
		super();
	}
	private String name;
	private String pbId;
	public EmployeeAttendanceData(String name, String pbId, String designation, String division, String phoneNo,
			String programCode, String date, String attendance) {
		super();
		this.name = name;
		this.pbId = pbId;
		this.designation = designation;
		this.division = division;
		this.phoneNo = phoneNo;
		this.programCode = programCode;
		this.date = date;
		this.attendance = attendance;
	}
	@Override
	public String toString() {
		return "EmployeeAttendanceData [name=" + name + ", pbId=" + pbId + ", designation=" + designation
				+ ", division=" + division + ", phoneNo=" + phoneNo + ", programCode=" + programCode + ", date=" + date
				+ ", attendance=" + attendance + "]";
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	private String designation;
	private String division;
	private String phoneNo;
	private String programCode;
	private String date;
	private String attendance;
	
}
