package com.rest.hr.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "HR_EMPLOYEE_ENTITY")
@Table(name = "HR_EMPLOYEE_TABLE")
public class HrEmployee {

	@Column(name = "NAME")
	private String name;
	@Id
	@Column(name = "PB_ID")
	private String pbId;
	@Column(name = "DESIGNATION")
	private String designation;
	@Column(name = "DIVISION")
	private String division;
	@Column(name = "PHONE_NO")
	private String phoneNo;
	@Column(name = "IS_VALID")
	private boolean valid;
	@Column(name = "registered_time")
	@Temporal(TemporalType.TIME)
	private Date time;
	@Column(name = "registered_date")
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column(name = "role")
	private String role;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
