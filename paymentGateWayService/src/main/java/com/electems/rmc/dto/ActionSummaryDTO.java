package com.electems.rmc.dto;

import java.io.Serializable;

public class ActionSummaryDTO implements Serializable{
	
	private static final long serialVersionUID = -7722544975069356302L;
	
	Integer training;
	Integer departmental;
	Integer management;
	Integer working;
	
	public Integer getTraining() {
		return training;
	}
	public void setTraining(Integer training) {
		this.training = training;
	}
	public Integer getDepartmental() {
		return departmental;
	}
	public void setDepartmental(Integer departmental) {
		this.departmental = departmental;
	}
	public Integer getManagement() {
		return management;
	}
	public void setManagement(Integer management) {
		this.management = management;
	}
	public Integer getWorking() {
		return working;
	}
	public void setWorking(Integer working) {
		this.working = working;
	}
	
	

}
