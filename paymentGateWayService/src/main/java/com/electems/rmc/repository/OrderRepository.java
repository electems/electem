package com.electems.rmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electems.rmc.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
