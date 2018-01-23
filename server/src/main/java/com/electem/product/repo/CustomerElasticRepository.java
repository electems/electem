package com.electem.product.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.electem.product.elastic.model.CustomerElasticModel;

/**
 * 
 * @author Elastic search repository
 *
 */
@RepositoryRestResource
public interface CustomerElasticRepository extends ElasticsearchRepository<CustomerElasticModel, String> {

	public CustomerElasticModel findByFirstName(String firstName);

	public List<CustomerElasticModel> findByLastName(String lastName);

}
