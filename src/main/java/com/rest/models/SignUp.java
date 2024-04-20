package com.rest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "SIGNUP_ENTITY")
@Table(name = "SIGNUP_TABLE")
public class SignUp {
	@Id
	@Column(name = "PB_ID")
	private String pbId;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "ROLE")
	private String role;

	public SignUp(String pbId, String password, String role) {
		super();
		this.pbId = pbId;
		this.password = password;
		this.role = role;
	}

	public SignUp() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	@Override
	public String toString() {
		return "UserDto [pbId=" + pbId + ", password=" + password + ", role=" + role + "]";
	}
}
