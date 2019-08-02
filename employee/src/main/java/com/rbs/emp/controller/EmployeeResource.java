package com.rbs.emp.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rbs.emp.bean.Employee;
import com.rbs.emp.exception.EmployeeNotFoundException;
import com.rbs.emp.service.EmployeeService;

@RestController
public class EmployeeResource {
	
	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/employees")
	public List<Employee> retriveAllEmployee() {
		List<Employee> fetchAllEmployee = empService.findAll();
		
		return fetchAllEmployee;
		//return new ResponseEntity<>(fetchAllEmployee, HttpStatus.OK);
	}
	
	@GetMapping("/employees/{empId}")
	public Resource<Employee> retriveEmployee(@PathVariable int empId) {
		Employee emp = empService.findOne(empId);
		if(emp==null) {
			throw new EmployeeNotFoundException("Employee Id - "+empId);
		}
		
		Resource<Employee> resource = new Resource<Employee>(emp);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllEmployee());
		resource.add(linkTo.withRel("all-employees"));
		
		return resource;
		//return new ResponseEntity<>(resource, HttpStatus.OK);
	}
	
	@PostMapping("/employees")
	public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee emp) {
		Employee savedEmployee = empService.save(emp);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{empId}")
				.buildAndExpand(savedEmployee.getEmpId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/employees/{empId}")
	public Employee deleteEmployee(@PathVariable int empId) {
		Employee emp = empService.deleteById(empId);
		if(emp==null) {
			throw new EmployeeNotFoundException("Employee Id - "+empId);
		}
		
		return emp;
	}

}
