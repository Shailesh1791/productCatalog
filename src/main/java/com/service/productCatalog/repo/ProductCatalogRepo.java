package com.service.productCatalog.repo;

import com.service.productCatalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductCatalogRepo extends JpaRepository<Product, Long> {

    public Product findByName(String name);

    public Page<Product> findByCategory(String category, Pageable pageable);

    @Transactional(readOnly = false)
    @Procedure(name = "Product.countByCategory")
    public int countByCategory(@Param("categoryName") String category);

    @Query(value =  "SELECT productId,name,description,price,category from " +
            "Product_Table where productId=:id",nativeQuery = false)
    public Product findProductById(@Param("id") long id);

}
