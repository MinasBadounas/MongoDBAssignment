package com.assignment.wellics.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "metrics")
public class Metrics {

	@Id
	private Long id;
	private String type;
	private String department;
	@Indexed(direction = IndexDirection.ASCENDING)
	private Date registrationDate;
	private int value;
		
	public Metrics(Long id,String type, String department, Date registrationDate, int value) {
		super();
		this.id=id;
		this.type = type;
		this.department = department;
		this.registrationDate = registrationDate;
		this.value = value;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
