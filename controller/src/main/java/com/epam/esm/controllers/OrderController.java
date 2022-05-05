package com.epam.esm.controllers;

import com.epam.esm.dto.impl.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService<OrderDto> orderService;

    private static final String CREATED_MESSAGE = "Created!";
    private static final String DELETED_MESSAGE = "Deleted!";

    @Autowired
    @Qualifier("orderBusinessService")
    public void setOrderService(OrderService<OrderDto> orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Object> insertOrder(@RequestBody OrderDto order) {
        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(CREATED_MESSAGE);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderByID(@PathVariable Long id){
        return orderService.getById(id);
    }

    @GetMapping
    public List<OrderDto> getAllGiftCertificates(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return orderService.getAll(pageSize,pageNumber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificateByID(@PathVariable long id){
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(DELETED_MESSAGE);
    }

    @GetMapping("/users/{userId}")
    public List<OrderDto> ordersByUserId(@PathVariable long userId,@RequestParam int pageSize, @RequestParam int pageNumber) {
        return orderService.ordersByUserId(userId, pageSize, pageNumber);
    }
}
