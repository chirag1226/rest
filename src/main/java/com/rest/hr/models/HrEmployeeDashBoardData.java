package com.rest.hr.models;

import java.util.List;

public class HrEmployeeDashBoardData {
	
	private String presentDays;
	private String lWP;
	private String cL;
	private List<HrEmployeeAttendanceData> data;
	private String canteenDays;
	private String hmaDays;
	public String getPresentDays() {
		return presentDays;
	}
	public void setPresentDays(String presentDays) {
		this.presentDays = presentDays;
	}
	public String getlWP() {
		return lWP;
	}
	public void setlWP(String lWP) {
		this.lWP = lWP;
	}
	public String getcL() {
		return cL;
	}
	public void setcL(String cL) {
		this.cL = cL;
	}
	public List<HrEmployeeAttendanceData> getData() {
		return data;
	}
	public void setData(List<HrEmployeeAttendanceData> data) {
		this.data = data;
	}
	public String getCanteenDays() {
		return canteenDays;
	}
	public void setCanteenDays(String canteenDays) {
		this.canteenDays = canteenDays;
	}
	public String getHmaDays() {
		return hmaDays;
	}
	public void setHmaDays(String hmaDays) {
		this.hmaDays = hmaDays;
	}

}
