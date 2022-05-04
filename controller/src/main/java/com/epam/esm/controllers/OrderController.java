package com.epam.esm.controllers;

import com.epam.esm.entity.Order;
import com.epam.esm.service.logic_service.OrderLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderLogicService orderService;

    private static final String CREATED_MESSAGE = "Created!";
    private static final String DELETED_MESSAGE = "Deleted!";

    @Autowired
    public OrderController(OrderLogicService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> insertOrder(@PathVariable Long userId,@RequestBody Order order) {
        orderService.saveOrder(userId,order);
        return ResponseEntity.status(HttpStatus.CREATED).body(CREATED_MESSAGE);
    }

    @GetMapping("/{id}")
    public Order getOrderByID(@PathVariable Long id){
        return orderService.getById(id);
    }

    @GetMapping
    public List<Order> getAllGiftCertificates(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return orderService.getAll(pageSize,pageNumber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificateByID(@PathVariable long id){
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(DELETED_MESSAGE);
    }
}
