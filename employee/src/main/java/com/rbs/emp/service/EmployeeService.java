package com.rbs.emp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rbs.emp.bean.Employee;

@Component
public class EmployeeService {
	
	private static List<Employee> empList = new ArrayList<Employee>();
	private static int empId = 3;
	static {
		empList.add(new Employee(1, "Praween", new Date(), 80000));
		empList.add(new Employee(2, "Ranjan", new Date(), 60000));
		empList.add(new Employee(3, "Mamta", new Date(), 40000));
	}

	public List<Employee> findAll() {
		return empList;
	}
	
	public Employee save(Employee emp) {
		if(emp.getEmpId()==null) {
			emp.setEmpId(++empId);
		}
		empList.add(emp);
		return emp;
	}
	
	public Employee findOne(int id) {
		for(Employee employee:empList) {
			if(employee.getEmpId()==id) {
				return employee;
			}
		}
		return null;
	}
	
	public Employee deleteById(int empId) {
		
		Iterator<Employee> iterator = empList.iterator();
		while(iterator.hasNext()) {
			Employee emp = iterator.next();
			if(emp.getEmpId()==empId) {
				iterator.remove();
				return emp;
			}
		}
		return null;
	}

}
