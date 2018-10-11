package com.cb.demo.model;

public class UnEncryptedProfile extends Entity{
	
	private Address address;
	private String firstName;
	private String lastName;
	private UnencryptedCC cc;
	private String[] hobbies;
	
	public UnEncryptedProfile() {
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
	public UnencryptedCC getCc() {
		return cc;
	}
	public void setCc(UnencryptedCC cc) {
		this.cc = cc;
	}
	public String[] getHobbies() {
		return hobbies;
	}
	public void setHobbies(String[] hobbies) {
		this.hobbies = hobbies;
	}
	
	

}
