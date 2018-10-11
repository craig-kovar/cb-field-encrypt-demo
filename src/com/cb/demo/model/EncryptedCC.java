package com.cb.demo.model;

import com.couchbase.client.java.repository.annotation.EncryptedField;

public class EncryptedCC {
	
	@EncryptedField(provider = "AES")
	private String ccNumber;
	
	private String ccExpDt;
	
	@EncryptedField(provider = "AES")
	private String cvcCode;
	
	private String ccHolderName;
	
	public String getCcNumber() {
		return ccNumber;
	}
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	public String getCcExpDt() {
		return ccExpDt;
	}
	public void setCcExpDt(String ccExpDt) {
		this.ccExpDt = ccExpDt;
	}
	public String getCvcCode() {
		return cvcCode;
	}
	public void setCvcCode(String cvcCode) {
		this.cvcCode = cvcCode;
	}
	public String getCcHolderName() {
		return ccHolderName;
	}
	public void setCcHolderName(String ccHolderName) {
		this.ccHolderName = ccHolderName;
	}
	
	

}
