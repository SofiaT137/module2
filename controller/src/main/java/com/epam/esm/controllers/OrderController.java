package com.epam.esm.controllers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * OrderController class presents REST controller for the Order entity
 */
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

    /**
     * Method insertOrder inserts the OrderDto entity
     * @param order OrderDto orderDto
     * @return Response entity with HttpStatus "CREATED"
     */
    @PostMapping
    public ResponseEntity<Object> insertOrder(@RequestBody OrderDto order) {
        orderLogicService.insert(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method getOrderByID returns ResponseEntity with OrderDto entity and HttpStatus "OK"
     * @param id Order id(Long value)
     * @return Response entity with OrderDto entity and HttpStatus "OK"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderByID(@PathVariable Long id){
        OrderDto order = orderLogicService.getById(id);
        orderDtoHateoas.addLinks(order);
        return new ResponseEntity<>(order,HttpStatus.OK);
    }

    /**
     * Method getAllOrders returns all the OrderDto entity
     * @param pageSize Number of OrderDto entities per page(default value is 5)
     * @param pageNumber Number of the page with OrderDto entities(default value is 1)
     * @return Response entity with list of OrderDto entity and HttpStatus "OK"
     */
    @GetMapping
    public ResponseEntity<Object> getAllOrders(@RequestParam(defaultValue = "5",required = false) int pageSize,
                                                         @RequestParam (defaultValue = "1", required = false)
                                                                 int pageNumber){
        Page<OrderDto> orderDtoList = orderLogicService.getAll(pageSize,pageNumber);
        orderDtoList.forEach(orderDtoHateoas::addLinks);
        return new ResponseEntity<>(orderDtoList,HttpStatus.OK);
    }

    /**
     * Method deleteOrderByID deletes OrderDto entity by its id
     * @param id Order id(Long value)
     * @return Response entity with HttpStatus "NO_CONTENT"
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrderByID(@PathVariable long id){
        orderLogicService.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Method findByUserId returns all the Users OrderDto entity
     * @param userId User id(Long value)
     * @param pageSize Number of OrderDto entities per page(default value is 5)
     * @param pageNumber Number of the page with OrderDto entities(default value is 1)
     * @return Response entity with list of OrderDto entity and HttpStatus "OK"
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> ordersByUserId(@PathVariable long userId,
                                                 @RequestParam(defaultValue = "5",required = false) int pageSize,
                                                 @RequestParam (defaultValue = "1", required = false) int pageNumber){
        Page<OrderDto> orderDtoList = orderLogicService.ordersByUserId(userId, pageSize, pageNumber);
        orderDtoList.forEach(orderDtoHateoas::addLinks);
        return new ResponseEntity<>(orderDtoList,HttpStatus.OK);
    }
}
