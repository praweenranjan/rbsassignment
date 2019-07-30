package com.rbs.emp.bean;

import java.util.Date;

public class Employee {

	private String name;
	private Date hireDate;
	private double salary;
	
	
	
	public Employee() {
		super();
	}
	public Employee(String name, Date hireDate, double salary) {
		super();
		this.name = name;
		this.hireDate = hireDate;
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
	
}
