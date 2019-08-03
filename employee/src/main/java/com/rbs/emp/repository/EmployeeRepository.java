package com.rbs.emp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rbs.emp.bean.Employee;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{
	
}
