package com.rbs.emp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rbs.emp.bean.Employee;
import com.rbs.emp.dao.EmployeeDao;


@Component
public class EmployeeService {
	
	//private static List<Employee> empList = new ArrayList<Employee>();
	private static int empId = 10003;
	/*static {
		empList.add(new Employee(10001, "Praween", new Date(), 80000));
		empList.add(new Employee(10002, "Ranjan", new Date(), 60000));
		empList.add(new Employee(10003, "Mamta", new Date(), 40000));
	}*/

	@Autowired
	EmployeeDao employeeDao;
	
	public List<Employee> findAll() {
		//return empList;
		return employeeDao.findAll();
	}
	
	public Employee save(Employee emp) {
		
		if(emp.getEmpId()==null) {
			emp.setEmpId(++empId);
		}
		//empList.add(emp);
		
		employeeDao.insert(emp);
		return emp;
	}
	
	public Employee findOne(int id) {
		
		Employee emp = employeeDao.findByEmpId(id);
		/*for(Employee employee:empList) {
			if(employee.getEmpId()==id) {
				return employee;
			}
		}*/
		
		if(emp!= null && emp.getEmpId()==id) {
			return emp;
		}
		
		
		return null;
	}
	
	public Employee deleteById(int empId) {
		
		/*Iterator<Employee> iterator = empList.iterator();
		while(iterator.hasNext()) {
			Employee emp = iterator.next();
			if(emp.getEmpId()==empId) {
				iterator.remove();
				return emp;
			}
		}*/
		
	  int deletedCount = employeeDao.deleteByEmpId(empId);
	  if(deletedCount > 0) {
		  Employee employee = new Employee();
		  employee.setEmpId(empId);
		  return employee;
	  }
		
		return null;
	}

}
