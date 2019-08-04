package com.rbs.emp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import com.rbs.emp.bean.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	private HttpHeaders headers = new HttpHeaders();
	private static String BASE_URI="/employees";

	private ResponseEntity<List> result;

	@Test
	public void testCreateEmployee() throws RestClientException, URISyntaxException {
		headers.add("Content-Type", "application/json");
		Employee employee = new Employee(1L, "Lakshya", new Date(), 1000);
		HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(BASE_URI, entity, String.class);

		assertEquals(HttpStatus.CREATED.value(), result.getStatusCodeValue());
		assertTrue(result.getHeaders().containsKey("Location"));
		assertEquals(this.getURI("/employees/1").toString(), result.getHeaders().get("Location").get(0));

	}
	
	@Test
	public void testCreateEmployee_validateName() throws RestClientException, URISyntaxException {
		headers.add("Content-Type", "application/json");
		Employee employee = new Employee(2L, "L", new Date(), 1000);
		HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(BASE_URI, entity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCodeValue());
		assertTrue(result.getBody().contains("name should have atleast two charecters"));
		
	}
	
	@Test
	public void testCreateEmployee_hireDate() throws RestClientException, URISyntaxException {
		headers.add("Content-Type", "application/json");
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		Employee employee = new Employee(2L, "L", dt, 1000);
		HttpEntity<Employee> entity = new HttpEntity<Employee>(employee, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(BASE_URI, entity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatusCodeValue());
		assertTrue(result.getBody().contains("hireDate must be of past dated"));
		
	}

	@Test
	public void testRetriveAllEmployee() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI);
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		// Verify request succeed
		assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
		assertTrue(result.getBody().contains("10003"));
		assertTrue(result.getBody().contains("Ranjan"));
		
	}
	
	@Test
	public void testRetriveEmployee() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI+"/10003");
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		// Verify request succeed
		assertEquals(HttpStatus.OK.value(), result.getStatusCodeValue());
		assertTrue(result.getBody().contains("10003"));
		assertTrue(result.getBody().contains("Mamta"));
		assertFalse(result.getBody().contains("10002"));
		assertFalse(result.getBody().contains("Ranjan"));
	}
	
	@Test
	public void testRetriveEmployee_orderByEmpid() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI+"?sortBy=empId");
		
		ResponseEntity<List<Employee>> response = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Employee>>(){});
		List<Employee> empList = response.getBody();
		System.out.println(empList);
		// Verify request succeed
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		Long empIdPrev = 0L;
		for(int i=0;i< empList.size();i++) {
			Employee emp = empList.get(i);
			assertTrue(empIdPrev < emp.getEmpId());
		}
	}
	
	@Test
	public void testRetriveEmployee_orderByName() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI+"?sortBy=name");
		
		ResponseEntity<List<Employee>> response = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Employee>>(){});
		List<Employee> empList = response.getBody();
		System.out.println(empList);
		// Verify request succeed
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		String namePrev = "aa";
		for(int i=0;i< empList.size();i++) {
			Employee emp = empList.get(i);
			assertTrue(namePrev.compareTo(emp.getName())>=1);
		}
	}
	
	@Test
	public void testRetriveEmployee_orderByHireDate() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI+"?sortBy=hireDate");
		
		ResponseEntity<List<Employee>> response = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Employee>>(){});
		List<Employee> empList = response.getBody();
		System.out.println(empList);
		// Verify request succeed
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		Date hireDatePrev = new Date(Long.MIN_VALUE);
		for(int i=0;i< empList.size();i++) {
			Employee emp = empList.get(i);
			assertTrue(hireDatePrev.before(emp.getHireDate()));
		}
	}
	
	@Test
	public void testRetriveEmployee_orderBySalary() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI+"?sortBy=salary");
		
		ResponseEntity<List<Employee>> response = restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<List<Employee>>(){});
		List<Employee> empList = response.getBody();
		System.out.println(empList);
		// Verify request succeed
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		Double salaryprev = 0D;
		for(int i=0;i< empList.size();i++) {
			Employee emp = empList.get(i);
			assertTrue(salaryprev < emp.getSalary());
		}
	}
	
	@Test
	public void testRetriveEmployee_UnknownResource() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI+"/102");
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		// Verify resource not found
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCodeValue());
		
	}
	
	@Test
	public void testDeleteEmployee() throws RestClientException, URISyntaxException {
		URI uri = this.getURI(BASE_URI+"/10001");
		restTemplate.delete(uri);
		
		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
		// Verify deleted record
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCodeValue());
	}

	private URI getURI(String uri) throws URISyntaxException {
		return new URI("http://localhost:" + port + uri);
	}

}
