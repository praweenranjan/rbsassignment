package com.rbs.emp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rbs.emp.bean.Employee;
import com.rbs.emp.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public List<Employee> findAll(String sortBy) {
		
		Sort sortOrder = Sort.by(sortBy);
		return (List<Employee>) employeeRepository.findAll(sortOrder);
	}
	
	public Employee save(Employee emp) {
		
		return employeeRepository.save(emp);
		
	}
	
	public Employee findOne(Long id) {
		
		Optional<Employee> empopt = employeeRepository.findById(id);
		
		if(empopt.isPresent()) {
			return empopt.get();
		}
		
		return null;
	}
	
	public void deleteById(int empId) {
		
	  employeeRepository.deleteById((long) empId);;
	  
	}

}
