package com.rest.hr.models;

public class HrLogin {

	private String email;

	public HrLogin(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public HrLogin() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String password;


	
}
