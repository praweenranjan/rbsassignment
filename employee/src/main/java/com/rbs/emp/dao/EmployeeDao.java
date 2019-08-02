package com.rbs.emp.dao;

import java.util.List;

import com.rbs.emp.bean.Employee;

public interface EmployeeDao {
	
	public List<Employee> findAll();
	public Employee findByEmpId(int empId);
	public int deleteByEmpId(int empId);
	public int insert(Employee emp);

}
