package com.rest.models;

public class PunchModel {

private boolean flag;
private String msg;
public PunchModel(boolean flag, String msg) {
	super();
	this.flag = flag;
	this.msg = msg;
}
public PunchModel() {
	super();
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
