package com.rbs.emp.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Details of Employee")
@Entity
@Table(name = "employeejpa")
public class Employee {

	@Id
	@GeneratedValue
	@Column(name="empId")
	private Integer empId;
	
	@Size(min=2, message="name should have atleast two charecters")
	@ApiModelProperty(notes="name should have atleast two charecters")
	private String name;
	
	@Past(message="hireDate must be of past dated")
	@ApiModelProperty(notes="Hire Date should be in the past")
	private Date hireDate;
	private double salary;
	
	
	
	public Employee() {
		
	}
	public Employee(Integer empId, String name, Date hireDate, double salary) {
		super();
		this.empId=empId;
		this.name = name;
		this.hireDate = hireDate;
		this.salary = salary;
	}
	
	public Employee(String name, Date hireDate, double salary) {
		super();
		this.name = name;
		this.hireDate = hireDate;
		this.salary = salary;
	}
	
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
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
	
	@Override
	public String toString() {
		return "\nEmployee [empId=" + empId + ", name=" + name + ", hireDate=" + hireDate + ", salary=" + salary + "]";
	}
	
	
	
	
}
