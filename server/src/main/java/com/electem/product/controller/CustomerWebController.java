package com.electem.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electem.product.elastic.model.CustomerElasticModel;
import com.electem.product.model.Customer;
import com.electem.product.service.CustomerElasticService;
import com.electem.product.service.CustomerService;

/**
 * 
 * @author Vinzy
 * Following class makes HTTP rest call. Used in client side.
 */
@RestController
public class CustomerWebController {
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerElasticService custService;
	
	@RequestMapping(value = "/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> process(@RequestBody Customer customer) throws Exception {	
		//Save the entity to Postgres Db
		customer = customerService.saveCustomer(customer);
		
		//Following Function saves the record to JMS
	    jmsTemplate.convertAndSend("OrderTransactionQueue", customer);
		
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/findall",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> findAll() throws Exception{
		
		List<Customer> customers = new ArrayList<Customer>();
		
		//Fetching the list from Elastic database. 
		for(CustomerElasticModel cust :  custService.findAll()){
			//The list of record fetched from the Elastic database is added to Customer Entity.
			Customer tempCust = new Customer();
			tempCust.setFirstName(cust.getFirstName());
			tempCust.setLastName(cust.getLastName());
			tempCust.setId(Long.valueOf(cust.getId()));
			customers.add(tempCust);
		}					
		return  new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findbylastname",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)  
	public ResponseEntity<List<Customer>> fetchDataByLastName(@RequestParam("lastname") String lastName) throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		
		//Fetching the list from Elastic database. 
		for(CustomerElasticModel cust :  custService.findByLastName(lastName)){
			Customer tempCust = new Customer();
			tempCust.setFirstName(cust.getFirstName());
			tempCust.setLastName(cust.getLastName());
			tempCust.setId(Long.valueOf(cust.getId()));
			customers.add(tempCust);
		}	
		
		return  new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
}
