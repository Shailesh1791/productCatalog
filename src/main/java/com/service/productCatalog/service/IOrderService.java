package com.service.productCatalog.service;

import com.service.productCatalog.dto.OrderDTO;
import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.dto.SearchProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {

    public OrderDTO getOrderById(long orderId);

    public List<OrderDTO> getAllOrders();

    public OrderDTO saveOrder(OrderDTO orderDTO);

    public OrderDTO updateOrder(long orderId, OrderDTO orderDTO);

    public OrderDTO deleteOrder(long orderId);
}
