package com.cb.demo.model;

import com.couchbase.client.java.repository.annotation.EncryptedField;
import com.couchbase.client.java.repository.annotation.Field;


public class EncryptedProfile extends Entity{
	
	private Address address;
	
	@EncryptedField(provider = "AES")
	private String firstName;
	private String lastName;
	
//	@Field
//	private EncryptedCC cc;
	private String[] hobbies;
	
	public EncryptedProfile() {
		this.set_id("default_id");
		this.set_version("1.0.0");
	}
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
//	public EncryptedCC getCc() {
//		return cc;
//	}
//	public void setCc(EncryptedCC cc) {
//		this.cc = cc;
//	}
	public String[] getHobbies() {
		return hobbies;
	}
	public void setHobbies(String[] hobbies) {
		this.hobbies = hobbies;
	}
	
	

}
