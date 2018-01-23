package com.electem.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electem.product.elastic.model.CustomerElasticModel;
import com.electem.product.repo.CustomerElasticRepository;

/**
 * 
 * @author Vinzy
 * Following snippet is for Elastic database.
 */
@Service
public class CustomerElasticServiceImpl implements CustomerElasticService {
	
	@Autowired
	private CustomerElasticRepository customerElasticRepository;

	@Override
	public CustomerElasticModel save(CustomerElasticModel customer) {
		return customerElasticRepository.save(customer);
	}

	@Override
	public CustomerElasticModel findByFirstName(String firstName) {
		return customerElasticRepository.findByFirstName(firstName);
	}

	@Override
	public List<CustomerElasticModel> findByLastName(String lastName) {
		return customerElasticRepository.findByLastName(lastName);
	}

	@Override
	public List<CustomerElasticModel> findAll() {
		
		List<CustomerElasticModel> customers = new ArrayList<CustomerElasticModel>();
		for(CustomerElasticModel cust : customerElasticRepository.findAll()){
			customers.add(cust);
		}
		return customers;
	}

}
