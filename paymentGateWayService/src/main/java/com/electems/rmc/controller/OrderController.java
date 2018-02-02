package com.electems.rmc.controller;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.electems.rmc.model.Order;
import com.electems.rmc.service.OrderService;

@Controller
@RequestMapping("/rest/order")
public class OrderController {
	
	@Inject
	OrderService orderService;
	
	/*
	 * Following code is added to place order
	 */
	@RequestMapping(value = "/orderedItems", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> placeOrder(@RequestBody Order order) throws Exception {
		if (order.getLineItem() != null) {
			order.setOrderNumber(orderService.generateOrderNumber());
			order.setStatus("pending");
			order.setCreatedDate(new Date());
		}
		order = orderService.saveOrder(order);
		orderService.generatePDF(order.getTransactionIds());
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateOrderedItems", method = RequestMethod.GET)
	public ResponseEntity updateOrder(@RequestParam("status") String status, @RequestParam("transactionIds") String transactionIds) throws Exception {
		System.out.println("status"+status);
		System.out.println("transactionIds"+transactionIds);
		Order order = (Order) orderService.fetchByTranstionID(transactionIds);
		
		if (order != null) {
			
			order.setStatus(status);
			order.setMailStatus("Pending");
		}
		order = orderService.saveOrder(order);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	/*
	 * Following code is added to send message if success/fail in transaction.
	 */
	@RequestMapping(value = "/orderUpdateAfterPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Order> placedOrderUpdate(@RequestBody Order order) throws Exception {
		if (order.getTransactionCode() != null && order.getTransactionCode() == 0) {
			// success transaction we have to send mail with positive result.
			order.setStatus("completed");

			order = orderService.saveOrder(order);
		} else {
			// transaction error detail
		}
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

}
