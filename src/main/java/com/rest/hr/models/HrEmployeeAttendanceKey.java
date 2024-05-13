package com.rest.hr.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class HrEmployeeAttendanceKey implements Serializable {
	

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
		private String pbId;
	    private String datesLeaves;
		public HrEmployeeAttendanceKey(String pbId, String datesLeaves) {
			super();
			this.pbId = pbId;
			this.datesLeaves = datesLeaves;
		}



	
}
