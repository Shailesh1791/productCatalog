package com.service.productCatalog.mapper;

import com.service.productCatalog.dto.OrderDTO;
import com.service.productCatalog.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toDto(Order order);

    Order toEntity(OrderDTO orderDTO);
}
