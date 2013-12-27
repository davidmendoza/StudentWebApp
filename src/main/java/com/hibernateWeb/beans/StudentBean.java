package com.hibernateWeb.beans;

public class StudentBean {

	public StudentBean(){
		
	}
	
	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private int level;
	private Long addressId;
	private int gradesId;
	private String newCity;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public int getGradesId() {
		return gradesId;
	}
	public void setGradesId(int gradesId) {
		this.gradesId = gradesId;
	}
	public String getNewCity() {
		return newCity;
	}
	public void setNewCity(String newCity) {
		this.newCity = newCity;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
}
