package com.service.productCatalog.repo;

import com.service.productCatalog.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductCatalogRepoTest {

    @Autowired
    private ProductCatalogRepo productCatalogRepo;

    @Mock
    private TestEntityManager entityManager;


    @Test
    public void testFindByName() {
        //arrange
        Product product = Product.builder().name("Iphone")
                .category("Mobiule Product").price(150000).build();
        productCatalogRepo.save(product);

        //act
        Product product1 = productCatalogRepo.findByName("Iphone");

        //assertion
        assert product1 != null;
        assertNotNull(product1);
        assertEquals("Iphone", product1.getName());
        assertEquals(150000, product1.getPrice());
    }

    @Test
    public void testFindByCategory() {
        //arrange
        Product product = Product.builder().name("Iphone")
                .category("Mobiule Product").price(150000).build();
        productCatalogRepo.save(product);

        //act
        Pageable pageable= PageRequest.of(0, 10, Sort.by("price").descending());
        Page<Product> product1=productCatalogRepo.findByCategory(product.getCategory(),pageable);

        //assertion
        assert product1 != null;
        assertNotNull(product1);
        assertEquals(1, product1.getTotalElements());
    }
}