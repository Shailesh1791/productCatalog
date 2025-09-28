package com.service.productCatalog.controller;

import com.service.productCatalog.dto.OrderDTO;
import com.service.productCatalog.service.IOrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private Environment env;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") long orderId) {
        System.out.println(env.getProperty("local.server.port"));
        OrderDTO orderDTO = orderService.getOrderById(orderId);
        if (orderDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        log.info("getAllOrders started");
        List<OrderDTO> orderDTOS = orderService.getAllOrders();
        if (orderDTOS == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getAllOrders finished");
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody @Valid OrderDTO orderDTO) {
        log.info("saveOrder started");
        orderDTO = orderService.saveOrder(orderDTO);
        log.info("saveOrder finished");
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
