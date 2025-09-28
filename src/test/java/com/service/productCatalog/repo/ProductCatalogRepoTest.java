package com.service.productCatalog.repo;

import com.service.productCatalog.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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
        entityManager.persist(product);
        entityManager.flush();

        //act
        Product product1 = productCatalogRepo.findByName("Iphone");

        //assertion
        assertNotNull(product1);
        assertEquals("Iphone", product1.getName());
        assertEquals(150000, product1.getPrice());
    }

    @Test
    public void testFindByCategory() {
        Product product = Product.builder().name("Iphone")
                .category("Mobiule Product").price(150000).build();
        entityManager.persist(product);
        entityManager.flush();



    }
}