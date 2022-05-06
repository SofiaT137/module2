package com.epam.esm.controllers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.Hateoas;
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

    private OrderService<OrderDto> orderLogicService;
    private final Hateoas<OrderDto> orderDtoHateoas;

    @Autowired
    public OrderController(Hateoas<OrderDto> orderDtoHateoas) {
        this.orderDtoHateoas = orderDtoHateoas;
    }

    @Autowired
    @Qualifier("orderBusinessService")
    public void setUserService(OrderService<OrderDto> orderLogicService) {
        this.orderLogicService = orderLogicService;
    }

    private static final String CREATED_MESSAGE = "Created!";
    private static final String DELETED_MESSAGE = "Deleted!";


    @PostMapping
    public ResponseEntity<Object> insertOrder(@RequestBody OrderDto order) {
        orderLogicService.saveOrder(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderByID(@PathVariable Long id){
        OrderDto order = orderLogicService.getById(id);
        orderDtoHateoas.addLinks(order);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllGiftCertificates(@RequestParam int pageSize, @RequestParam int pageNumber) {
        List<OrderDto> orderDtoList = orderLogicService.getAll(pageSize,pageNumber);
        orderDtoList.forEach(orderDtoHateoas::addLinks);
        return new ResponseEntity<>(orderDtoList,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGiftCertificateByID(@PathVariable long id){
        orderLogicService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> ordersByUserId(@PathVariable long userId,@RequestParam int pageSize, @RequestParam int pageNumber) {
        List<OrderDto> orderDtoList = orderLogicService.ordersByUserId(userId, pageSize, pageNumber);
        orderDtoList.forEach(orderDtoHateoas::addLinks);
        return new ResponseEntity<>(orderDtoList,HttpStatus.OK);
    }
}
