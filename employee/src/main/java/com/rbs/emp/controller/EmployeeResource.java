package com.rbs.emp.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rbs.emp.bean.Employee;
import com.rbs.emp.exception.EmployeeNotFoundException;
import com.rbs.emp.service.EmployeeService;

@RestController
public class EmployeeResource {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping(value="/employees", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<Employee> retriveAllEmployee(@RequestParam(defaultValue = "empId") String sortBy) {
		List<Employee> fetchAllEmployee = employeeService.findAll(sortBy);
		
		return fetchAllEmployee;
	}
	
	@GetMapping(value="/employees/{empId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Resource<Employee> retriveEmployee(@PathVariable long empId) {
		Employee emp = employeeService.findOne(empId);
		if(emp==null) {
			throw new EmployeeNotFoundException("Employee Id - "+empId);
		}
		
		//To Generate Link of all employee to develop hateoas. 
		Resource<Employee> resource = new Resource<Employee>(emp);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllEmployee("empId"));
		resource.add(linkTo.withRel("all-employees"));
		
		return resource;
	}
	
	@PostMapping("/employees")
	public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee emp) {
		Employee savedEmployee = employeeService.save(emp);
		
		//To generate location of created resource.
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{empId}")
				.buildAndExpand(savedEmployee.getEmpId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(value="/employees/{empId}",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void deleteEmployee(@PathVariable int empId) {
		employeeService.deleteById(empId);
	}

}
