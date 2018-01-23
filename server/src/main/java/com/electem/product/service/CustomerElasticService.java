package com.electem.product.service;

import java.util.List;

import com.electem.product.elastic.model.CustomerElasticModel;

/**
 * @author Vinzy
 * Interface for the Elastic search model
 *
 */
public interface CustomerElasticService {
	CustomerElasticModel save(CustomerElasticModel customer); 
	
	CustomerElasticModel findByFirstName(String firstName);
	
	List<CustomerElasticModel> findByLastName(String lastName);
	
	List<CustomerElasticModel> findAll();
}
