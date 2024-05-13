package com.rest.hr.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name = "HR_EMPLOYEE_ATTENDANCE_ENTITY")
@Table(name = "HR_EMPLOYEE_ATTENDANCE_TABLE")
public class HrEmployeeAttendanceData {
	@Transient
	private int id;
	@Transient
	private String pbId;
	@Transient
	private String datesLeaves;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int uniqueId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESIGNATION")
	private String designation;
	@Column(name = "DIVISION")
	private String division;
	@Column(name = "START_DATE")
	private String startDate;
	@Column(name = "END_DATE")
	private String endDate;
	@Column(name = "NO_OF_PRESENT_DAYS")
	private String noDaysPresent;
	@Column(name = "NO_OF_DAYS_LEAVE")
	private String noDaysLeave;
	@Column(name = "TYPE_OF_LEAVE")
	private String typeOfLeaves;
	@Column(name = "REMARKS")
	private String remarks;
	@EmbeddedId
	private HrEmployeeAttendanceKey myId;
	@Column(name = "CANTEEN_DAYS")
	private String canteenDays;
	@Column(name = "HMA_DAYS")
	private String hmaDays;
	
	public HrEmployeeAttendanceKey getMyId() {
		return myId;
	}
	public void setMyId(HrEmployeeAttendanceKey myId) {
		this.myId = myId;
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getName() {
		return name;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setName(String name) {
		this.name = name;
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

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getNoDaysPresent() {
		return noDaysPresent;
	}
	public void setNoDaysPresent(String noDaysPresent) {
		this.noDaysPresent = noDaysPresent;
	}
	public String getNoDaysLeave() {
		return noDaysLeave;
	}
	public void setNoDaysLeave(String noDaysLeave) {
		this.noDaysLeave = noDaysLeave;
	}
	public String getTypeOfLeaves() {
		return typeOfLeaves;
	}
	public void setTypeOfLeaves(String typeOfLeaves) {
		this.typeOfLeaves = typeOfLeaves;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getPbId() {
		return pbId;
	}
	public void setPbId(String pbId) {
		this.pbId = pbId;
	}
	public String getDatesLeaves() {
		return datesLeaves;
	}
	public void setDatesLeaves(String datesLeaves) {
		this.datesLeaves = datesLeaves;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
