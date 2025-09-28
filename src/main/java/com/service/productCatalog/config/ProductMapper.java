package com.service.productCatalog.config;

import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);
}
