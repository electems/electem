package com.electems.rmc.dto;

import java.io.Serializable;

public class UserDataViewDTO implements Serializable {
	private static final long serialVersionUID = -7722544975069356302L;
	
	String custList;
	String locList;
	public String getCustList() {
		return custList;
	}
	public void setCustList(String custList) {
		this.custList = custList;
	}
	public String getLocList() {
		return locList;
	}
	public void setLocList(String locList) {
		this.locList = locList;
	}
}
