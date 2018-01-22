package com.electem.product;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.electem.product.model.Customer;

@Component
public class Receiver {

	@JmsListener(destination = "OrderTransactionQueue", containerFactory = "jmsFactory")
    public void receiveMessage(Customer customer) {
        System.out.println("Received <" + customer + ">");
    }
}
