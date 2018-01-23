package com.electem.product.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.electem.product.model.Customer;

/**
 * 
 * Customer Repository for CRUD operation to save in Postgres db.
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{
	List<Customer> findByLastName(String lastName);
}
