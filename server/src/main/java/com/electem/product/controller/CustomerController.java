package com.electem.product.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electem.product.model.Customer;
import com.electem.product.repository.CustomerRepository;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class CustomerController {

	@Autowired
	private CustomerRepository repository;
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@PostMapping("/save")
	private void saveCustomers(@RequestBody Customer customer) {
		this.repository.save(customer);
	}

	/**
	 * 
	 * @return All the customer record.
	 */
	@GetMapping("/fetchAllCust")
	private Collection<Customer> fetchAllCustomers() {
		return ((Collection<Customer>) this.repository.findAll()).stream()
                .collect(Collectors.toList());
	}

	/** 
	 * @param name
	 * @return the list of customer record based on name searched.
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/fetch")
	private Collection<Customer>  fetchIndividualCustomers(String name) {		
		return ((Collection<Customer>) this.repository.findByFirstName(name)).stream()
                .collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param customer 
	 * Saves the values to JMS
	 */
	@PostMapping("/send")
	  public void send(@RequestBody Customer customer) {
		//Following Function saves the record to JMS
	    jmsTemplate.convertAndSend(
	        "OrderTransactionQueue", customer);
	    
	    this.repository.save(customer);
	  }
}
