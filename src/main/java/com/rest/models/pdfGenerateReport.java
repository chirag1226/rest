package com.rest.models;

public class pdfGenerateReport {
	private String pbId;
	private String name;
	private String designation;
	private String division;
	private String date;
	private String inPunch;
	private String outPunch;
	public String getPbId() {
		return pbId;
	}
	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public pdfGenerateReport(String pbId, String name, String designation, String division) {
		super();
		this.pbId = pbId;
		this.name = name;
		this.designation = designation;
		this.division = division;
		this.inPunch="";
		this.outPunch="";
		

	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public pdfGenerateReport() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInPunch() {
		return inPunch;
	}
	public void setInPunch(String inPunch) {
		this.inPunch = inPunch;
	}
	public String getOutPunch() {
		return outPunch;
	}
	public void setOutPunch(String outPunch) {
		this.outPunch = outPunch;
	}
}

