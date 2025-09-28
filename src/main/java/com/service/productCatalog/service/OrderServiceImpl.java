package com.service.productCatalog.service;

import com.service.productCatalog.config.OrderMapper;
import com.service.productCatalog.constants.CacheNames;
import com.service.productCatalog.dto.OrderDTO;
import com.service.productCatalog.entity.Order;
import com.service.productCatalog.repo.OrderServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService{

    @Autowired
    private OrderServiceRepo orderServiceRepo;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Cacheable(cacheNames = CacheNames.ORDERS,key = "#orderId")
    public OrderDTO getOrderById(long orderId) {
        Order order=orderServiceRepo.findById(orderId).get();
        return orderMapper.toDto(order);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.ORDERS)
    public List<OrderDTO> getAllOrders() {
        List<Order> orderList=orderServiceRepo.findAll();
        if(!orderList.isEmpty()){
            List<OrderDTO> orderDTOList=new ArrayList<>();
            orderList.forEach(order->{orderDTOList.add(orderMapper.toDto(order));});
            return orderDTOList;
        }
        return null;
    }

    @Override
    @CachePut(cacheNames = CacheNames.ORDERS,key = "#orderId")
    public OrderDTO saveOrder(OrderDTO orderDTO) {
        Order order=this.orderServiceRepo.save(orderMapper.toEntity(orderDTO));
        return orderMapper.toDto(order);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.ORDERS,key = "#orderId")
    public OrderDTO updateOrder(long orderId,OrderDTO orderDTO) {
        Order order=this.orderServiceRepo.save(orderMapper.toEntity(orderDTO));
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO deleteOrder(long orderId) {
        return null;
    }


}
