package com.electem.product.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.electem.product.model.Customer;

@RepositoryRestResource
public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

	public Customer findByFirstName(String firstName);

	public List<Customer> findByLastName(String lastName);

}
