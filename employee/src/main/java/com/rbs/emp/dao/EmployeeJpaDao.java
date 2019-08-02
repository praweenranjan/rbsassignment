package com.rbs.emp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.rbs.emp.bean.Employee;

@Repository

@Transactional
public class EmployeeJpaDao implements EmployeeDao{

	@PersistenceContext
	EntityManager entitymanager;
	
	@Override
	public List<Employee> findAll() {
		
		return null;
	}

	@Override
	public Employee findByEmpId(int empId) {
		
		return entitymanager.find(Employee.class, empId);
	}

	@Override
	public int deleteByEmpId(int empId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Employee emp) {
		
		return 0;
				//entitymanager.merge(emp);
	}
	
	

}
