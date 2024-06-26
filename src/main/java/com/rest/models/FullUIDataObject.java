package com.rest.models;

import java.util.List;

public class FullUIDataObject {
	private List<EmployeeData> employeesDataFirst;
	private List<EmployeeData> employeesDataSecond;
	private List<EmployeeData> employeesDataThird;
	private List<EmployeeData> employeesData1stHalfAttendance;
	private List<EmployeeData> employeesDataFullAttendance;
	private List<EmployeeData> employeesData2ndHalfAttendance;
	private List<EmployeeData> employeesPunchGridData;
	private List<BarGraphData> barGraphData;
	private PieGraphData pieGraphData;
	private List<EmployeeData> employeesAttendanceGridData;
	private List<EmployeeData> employeesDataAbsentAttendance;
	private List<EmployeeData> employeesDataSinglePunchAttendance;
	private List<pdfGenerateReport> pdfReport;

	public List<pdfGenerateReport> getPdfReport() {
		return pdfReport;
	}

	public void setPdfReport(List<pdfGenerateReport> pdfReport) {
		this.pdfReport = pdfReport;
	}

	public List<EmployeeData> getEmployeesDataAbsentAttendance() {
		return employeesDataAbsentAttendance;
	}

	public void setEmployeesDataAbsentAttendance(List<EmployeeData> employeesDataAbsentAttendance) {
		this.employeesDataAbsentAttendance = employeesDataAbsentAttendance;
	}

	public List<EmployeeData> getEmployeesDataSinglePunchAttendance() {
		return employeesDataSinglePunchAttendance;
	}

	public void setEmployeesDataSinglePunchAttendance(List<EmployeeData> employeesDataSinglePunchAttendance) {
		this.employeesDataSinglePunchAttendance = employeesDataSinglePunchAttendance;
	}

	public List<EmployeeData> getEmployeesAttendanceGridData() {
		return employeesAttendanceGridData;
	}

	public void setEmployeesAttendanceGridData(List<EmployeeData> employeesAttendanceGridData) {
		this.employeesAttendanceGridData = employeesAttendanceGridData;
	}

	public PieGraphData getPieGraphData() {
		return pieGraphData;
	}

	public void setPieGraphData(PieGraphData pieGraphData) {
		this.pieGraphData = pieGraphData;
	}

	public List<BarGraphData> getBarGraphData() {
		return barGraphData;
	}

	public void setBarGraphData(List<BarGraphData> barGraphData) {
		this.barGraphData = barGraphData;
	}

	public List<EmployeeData> getAllemployeesData() {
		return allemployeesData;
	}

	public void setAllemployeesData(List<EmployeeData> allemployeesData) {
		this.allemployeesData = allemployeesData;
	}

	private List<EmployeeData> allemployeesData;

	public List<EmployeeData> getEmployeesPunchGridData() {
		return employeesPunchGridData;
	}

	public void setEmployeesPunchGridData(List<EmployeeData> employeesPunchGridData) {
		this.employeesPunchGridData = employeesPunchGridData;
	}

	public List<EmployeeData> getEmployeesDataFirst() {
		return employeesDataFirst;
	}

	public void setEmployeesDataFirst(List<EmployeeData> employeesDataFirst) {
		this.employeesDataFirst = employeesDataFirst;
	}

	public List<EmployeeData> getEmployeesDataSecond() {
		return employeesDataSecond;
	}

	public void setEmployeesDataSecond(List<EmployeeData> employeesDataSecond) {
		this.employeesDataSecond = employeesDataSecond;
	}

	public List<EmployeeData> getEmployeesDataThird() {
		return employeesDataThird;
	}

	public void setEmployeesDataThird(List<EmployeeData> employeesDataThird) {
		this.employeesDataThird = employeesDataThird;
	}

	public List<EmployeeData> getEmployeesData1stHalfAttendance() {
		return employeesData1stHalfAttendance;
	}

	public void setEmployeesData1stHalfAttendance(List<EmployeeData> employeesData1stHalfAttendance) {
		this.employeesData1stHalfAttendance = employeesData1stHalfAttendance;
	}

	public List<EmployeeData> getEmployeesDataFullAttendance() {
		return employeesDataFullAttendance;
	}

	public void setEmployeesDataFullAttendance(List<EmployeeData> employeesDataFullAttendance) {
		this.employeesDataFullAttendance = employeesDataFullAttendance;
	}

	public List<EmployeeData> getEmployeesData2ndHalfAttendance() {
		return employeesData2ndHalfAttendance;
	}

	public void setEmployeesData2ndHalfAttendance(List<EmployeeData> employeesData2ndHalfAttendance) {
		this.employeesData2ndHalfAttendance = employeesData2ndHalfAttendance;
	}
}
