package com.rest.models;

public class PunchModel {

	private boolean flag;
	private String msg;
	private String pbId;

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public PunchModel(boolean flag, String msg, String pbId) {
		super();
		this.flag = flag;
		this.msg = msg;
		this.pbId = pbId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "PunchModel [flag=" + flag + ", msg=" + msg + "]";
	}
}
