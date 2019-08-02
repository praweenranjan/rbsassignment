package com.rbs.emp.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rbs.emp.bean.Employee;

@Repository
@Primary
public class EmployeeJdbcDao implements EmployeeDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Employee> findAll() {
		
		return jdbcTemplate.query("select * from Employee", new BeanPropertyRowMapper<Employee>(Employee.class));
		
	}
	
	public Employee findByEmpId(int empId) {
		
		return (Employee) jdbcTemplate.queryForObject("select * from Employee where empid=?", 
				new Object[] {empId}, new BeanPropertyRowMapper<Employee>(Employee.class));
		
	}
	
	public int deleteByEmpId(int empId) {
		
		return jdbcTemplate.update("delete from Employee where empid=?", new Object[] {empId});
		
	}
	
	public int insert(Employee emp) {
		
		return jdbcTemplate.update("insert into employee(empid, name, hiredate, salary) values(?, ?, ?, ?)", 
				emp.getEmpId(), emp.getName(), new Timestamp(emp.getHireDate().getTime()), emp.getSalary());
	}

}
