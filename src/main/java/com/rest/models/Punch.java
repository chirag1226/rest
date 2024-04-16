package com.rest.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity(name="Punch_ENTITY")
@Table(name = "Punch_TABLE")
public class Punch {
	public Punch() {
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int uniqueId;
	@ManyToOne
	@JoinColumn(name="PB_ID", nullable=false)
	private Employee emp;
	private Date time;
	public Punch(int uniqueId, Employee emp, Date time) {
		super();
		this.uniqueId = uniqueId;
		this.emp = emp;
		this.time = time;
	}
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Punch [uniqueId=" + uniqueId + ", emp=" + emp + ", time=" + time + "]";
	}
	
	
}
