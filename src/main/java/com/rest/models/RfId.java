package com.rest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity(name="RFID_ENTITY")
@Table(name = "RFID_TABLE")
public class RfId {
	public RfId(String reffId) {
		super();
		this.reffId = reffId;
	}

	@Id
	@Column(name="REFF_ID")
	private String reffId;


	public RfId() {
	}

	public String getReffId() {
		return reffId;
	}

	public void setReffId(String reffId) {
		this.reffId = reffId;
	}

	@Override
	public String toString() {
		return "RfId [reffId=" + reffId + "]";
	}

	
	
}
