package com.rest.models;

public class Customfilter {

	public Customfilter() {
		super();
	}

	public Customfilter(String pbId, String date, String programCode) {
		super();
		this.pbId = pbId;
		this.date = date;
		this.programCode = programCode;

	}

	private String pbId;
	private String date;
	private String endDate;

	public Customfilter(String pbId, String date, String endDate, String programCode, boolean allData) {
		super();
		this.pbId = pbId;
		this.date = date;
		this.endDate = endDate;
		this.programCode = programCode;
		this.allData = allData;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	private String programCode;
	private boolean allData;

	public String getPbId() {
		return pbId;
	}

	@Override
	public String toString() {
		return "Customfilter [pbId=" + pbId + ", date=" + date + ", endDate=" + endDate + ", programCode=" + programCode
				+ ", allData=" + allData + "]";
	}

	public Customfilter(String pbId, String date, String programCode, boolean allData) {
		super();
		this.pbId = pbId;
		this.date = date;
		this.programCode = programCode;
		this.allData = allData;
	}

	public boolean isAllData() {
		return allData;
	}

	public void setAllData(boolean allData) {
		this.allData = allData;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
}
