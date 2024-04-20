package com.rest.models;

import java.util.List;

public class FullDataRegistration {
	private List<EmployeeData> employeeRegisteredToday;
	private boolean employeeRegisteredStatus;

	public List<EmployeeData> getEmployeeRegisteredToday() {
		return employeeRegisteredToday;
	}

	public void setEmployeeRegisteredToday(List<EmployeeData> employeeRegisteredToday) {
		this.employeeRegisteredToday = employeeRegisteredToday;
	}

	public FullDataRegistration() {
		super();
	}

	public boolean isEmployeeRegisteredStatus() {
		return employeeRegisteredStatus;
	}

	public void setEmployeeRegisteredStatus(boolean employeeRegisteredStatus) {
		this.employeeRegisteredStatus = employeeRegisteredStatus;
	}

	public FullDataRegistration(List<EmployeeData> employeeRegisteredToday, boolean employeeRegisteredStatus) {
		super();
		this.employeeRegisteredToday = employeeRegisteredToday;
		this.employeeRegisteredStatus = employeeRegisteredStatus;
	}

}
