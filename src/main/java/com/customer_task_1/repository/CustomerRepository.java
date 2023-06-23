package com.customer_task_1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer_task_1.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	 List<Customer> findByCustomerId(String customerId);
	 
}
