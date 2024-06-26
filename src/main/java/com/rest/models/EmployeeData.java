package com.rest.models;

public class EmployeeData {
	public EmployeeData(String name, String pbId, RfId reffId, String designation, String division, String phoneNo,
			String programCode, String time, String date, String punchSlot, String attendance) {
		super();
		this.name = name;
		this.pbId = pbId;
		this.reffId = reffId;
		this.designation = designation;
		this.division = division;
		this.phoneNo = phoneNo;
		this.programCode = programCode;
		this.time = time;
		this.date = date;
		this.punchSlot = punchSlot;
		this.attendance = attendance;
	}

	public EmployeeData() {
		super();
	}

	private String name;
	private String pbId;
	private RfId reffId;
	private String designation;
	private String division;
	private String phoneNo;
	private String programCode;
	private String time;
	private String date;
	private String punchSlot;
	private String attendance;

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getPunchSlot() {
		return punchSlot;
	}

	public void setPunchSlot(String punchSlot) {
		this.punchSlot = punchSlot;
	}

	public EmployeeData(String name, String pbId, RfId reffId, String designation, String division, String phoneNo,
			String programCode, String time, String date, String punchSlot) {
		super();
		this.name = name;
		this.pbId = pbId;
		this.reffId = reffId;
		this.designation = designation;
		this.division = division;
		this.phoneNo = phoneNo;
		this.programCode = programCode;
		this.time = time;
		this.date = date;
		this.punchSlot = punchSlot;
		this.attendance = "";
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

	public RfId getReffId() {
		return reffId;
	}

	public void setReffId(RfId reffId) {
		this.reffId = reffId;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return this.getDate() + this.getPbId();
	}

	@Override
	public boolean equals(Object obj) {
		EmployeeData o = (EmployeeData) obj;
		if (obj == null)
			return false;
		if (this.date.equals(o.getDate()) && this.getPbId().equals(o.getPbId())) {
			return true;
		}
		return false;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
