package com.tech26.robotfactory.controller;

import com.tech26.robotfactory.entities.OrderDetails;
import com.tech26.robotfactory.entities.OrderRequest;
import com.tech26.robotfactory.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
class OrdersController {
    @Autowired
    OrderService orderService;

    @PostMapping(path="/orders", consumes="application/json", produces="application/json")
    public ResponseEntity<OrderDetails> createOrder(@RequestBody OrderRequest orderRequest) throws HttpStatusCodeException {
        Set<String> uniqueRobotParts = orderRequest.getComponents().stream().collect(Collectors.toSet());
        if(orderService.isValidOrder(uniqueRobotParts)) {
            return new ResponseEntity<>(orderService.placeOrder(uniqueRobotParts), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>((OrderDetails) null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="orders/search/{orderId}", produces="application/json")
    public ResponseEntity<OrderDetails> findOrder(@PathVariable String orderId) throws HttpStatusCodeException {
        OrderDetails orderDetails = orderService.findOrder(orderId);
        if(orderDetails != null) {
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>((OrderDetails) null, HttpStatus.NOT_FOUND);
        }
    }
}
