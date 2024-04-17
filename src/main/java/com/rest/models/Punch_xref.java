package com.rest.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity(name="Punch_XREF_ENTITY")
@Table(name = "Punch_XREF_TABLE")
public class Punch_xref {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int uniqueId;
	public int getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	private RfId reffId;
	@Temporal(TemporalType.TIME)
	private Date time;
	@Temporal(TemporalType.DATE)
	private Date date;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;
	private boolean valid;
	private String punchSlot;
	private String pbId;
	public String getPbId() {
		return pbId;
	}
	public void setPbId(String pbId) {
		this.pbId = pbId;
	}
	public Punch_xref( RfId reffId, Date time, Date date, Date timeStamp, boolean valid,
			String punchSlot,String pbId) {
		super();
		this.reffId = reffId;
		this.time = time;
		this.date = date;
		this.timeStamp = timeStamp;
		this.valid = valid;
		this.punchSlot = punchSlot;
		this.pbId = pbId;
	}
	public String getPunchSlot() {
		return punchSlot;
	}
	public void setPunchSlot(String punchSlot) {
		this.punchSlot = punchSlot;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Punch_xref() {
	}
	public RfId getReffId() {
		return reffId;
	}
	public void setReffId(RfId reffId) {
		this.reffId = reffId;
	}
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
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
