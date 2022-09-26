package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;


@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>{

	List<Employee> findByName(String name);
	List<Employee> findByDesignation(String designation);
	
}

