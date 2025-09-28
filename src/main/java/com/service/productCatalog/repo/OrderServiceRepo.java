package com.service.productCatalog.repo;

import com.service.productCatalog.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderServiceRepo extends JpaRepository<Order,Long> {
}
