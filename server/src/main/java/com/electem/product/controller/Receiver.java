package com.electem.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.electem.product.elastic.model.CustomerElasticModel;
import com.electem.product.model.Customer;
import com.electem.product.service.CustomerElasticService;

/**
 * 
 * @author Vinzy
 * Following code is for JMS: The Recived entity is saved to Elastic Database.
 *
 */
@Component
public class Receiver {
	
	@Autowired
	CustomerElasticService custService;
	
	@JmsListener(destination = "OrderTransactionQueue", containerFactory = "jmsFactory")
    public void receiveMessage(Customer customer) {
		// The received Entity is saved to Elastic Database
		CustomerElasticModel customerElasticModel = new CustomerElasticModel();
		customerElasticModel.setFirstName(customer.getFirstName());
		customerElasticModel.setLastName(customer.getLastName());
		customerElasticModel.setId(Long.toString(customer.getId()));
		
		//Elastic DB save
		custService.save(customerElasticModel);
    }

}
