package com.electem.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.electem.product.model.Car;

@RepositoryRestResource
public interface CarRepository extends JpaRepository<Car, Long> {
}
