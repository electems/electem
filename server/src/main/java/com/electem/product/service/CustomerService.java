package com.electem.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electem.product.model.Customer;
import com.electem.product.repo.CustomerRepository;

@Service("customerService")
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	public Customer saveCustomer(Customer customer){
		customer = customerRepository.save(customer);
		return customer;
	}
	
	public List<Customer> allList(){
		List<Customer> customers = new ArrayList<Customer>();
		for(Customer cust : customerRepository.findAll()){
			customers.add(cust);
		}
		return customers;
	}
	
	public Customer findById(Long id){		
		Customer customer = customerRepository.findOne(id);		
		return customer;
	}
	
	public  List<Customer> fetchDataByLastName(String lastName){		
		List<Customer> customers = new ArrayList<Customer>();
		for(Customer cust : customerRepository.findByLastName(lastName)){
			customers.add(cust);
		}
		return customers;
	}
}
