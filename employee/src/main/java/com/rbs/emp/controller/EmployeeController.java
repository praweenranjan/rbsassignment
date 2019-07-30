package com.rbs.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbs.emp.bean.Employee;
import com.rbs.emp.service.EmployeeImpl;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeImpl empimpl;
	
	@GetMapping("/employees")
	public ResponseEntity fetchAllEmployee() {
		List<Employee> fetchAllEmployee = empimpl.fetchAllEmployee();
		
		//ResponseEntity returnResponse = new ResponseEntity(fetchAllEmployee, 200);
		
		return null;
	}
	
	
	

}
