package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	//READ OERATIONS
	
	//Read all data
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		System.out.println("test");
		try {
			return new ResponseEntity<List<Employee>>(employeeRepository.findAll(), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Read based on conditions
	
	//Read based on conditions
		@GetMapping("/{employeeid}")
		public ResponseEntity<Employee> getAllEmployeeById(@PathVariable String employeeid) {
			try {
				return new ResponseEntity<Employee>(employeeRepository.findById(employeeid).get(), HttpStatus.OK);
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(null, HttpStatus.OK);
			}
		}
	
	@GetMapping("/by-name")
	public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam String name) {
		try {
			return new ResponseEntity<List<Employee>>(employeeRepository.findByName(name), HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.OK);
		}
	}
	
	//CREATE OPERATIONS
	
	//Insert one document
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		try {
			return new ResponseEntity<Employee>(employeeRepository.save(employee), HttpStatus.CREATED);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Insert multiple documents
	@PostMapping("bulk-insert")
	public ResponseEntity<List<Employee>> createEmployees(@RequestBody List<Employee> employees) {
		try {
			return new ResponseEntity<List<Employee>>(employeeRepository.saveAll(employees), HttpStatus.CREATED);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//UPDATE OPERATION
	
	@PutMapping("{employeeid}")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		Optional<Employee> employeeData = employeeRepository.findById(employee.getEmployeeId());
		if(employeeData.isPresent()) {
			Employee updatedEmployee = new Employee();
			updatedEmployee.setName(employee.getName());
			updatedEmployee.setAddress(employee.getAddress());
			updatedEmployee.setDesignation(employee.getDesignation());
			updatedEmployee.setBloodGroup(employee.getBloodGroup());
			updatedEmployee.setDateOfJoining(employee.getDateOfJoining());
			updatedEmployee.setMobileNo(employee.getMobileNo());
			return new ResponseEntity<Employee>(employeeRepository.save(updatedEmployee), HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.NOT_FOUND);
	}
	
	
	//DELETE OPERATION
	@DeleteMapping("{employeeid}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable String employeeid) {
		Optional<Employee> employeeData = employeeRepository.findById(employeeid);
		if(employeeData.isPresent()) {
			employeeRepository.deleteById(employeeid);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
