package com.rest.hr.models;

public class HrEmployeeDataFilter {
private String pbId;
private String startDate;
private String endDate;
private String canteenDays;
private String hmaDays;
private String role;
private HrEmployee loginUserData;
public String getPbId() {
	return pbId;
}
public HrEmployeeDataFilter() {
	super();
}
public void setPbId(String pbId) {
	this.pbId = pbId;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
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
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public HrEmployee getLoginUserData() {
	return loginUserData;
}
public void setLoginUserData(HrEmployee loginUserData) {
	this.loginUserData = loginUserData;
}

}
