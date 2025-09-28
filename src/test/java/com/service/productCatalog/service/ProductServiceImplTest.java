package com.service.productCatalog.service;

import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.entity.Product;
import com.service.productCatalog.repo.ProductCatalogRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductCatalogRepo productCatalogRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProductByProductId_WithSuccess_ReturnProductDTO() {
        //arrange
        long productId = 1000;
        Product product= Product.builder().name("mobile").price(50000)
                .productId(productId).description("Mobile Description").build();
        Mockito.when(productCatalogRepo.findById(productId)).thenReturn(Optional.ofNullable(product));
        //Act
        ProductDTO productDTO1 = productService.getProductByProductId(productId);
        
        //assertion
        assertNotNull(productDTO1);
        assertEquals(productId, productDTO1.getProductId());
        assertEquals(product.getName(), productDTO1.getName());
        Mockito.verify(productCatalogRepo, Mockito.times(1)).findById(productId);
    }

    @Test
    public void testProductByProductId_WithFailure() {
        //arrange
        long productId = -100;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> {productService.getProductByProductId(productId);});
        assertEquals("productId should be positive", illegalArgumentException.getMessage());
    }

    @Test
    public void testAllProducts_WithSuccess_ReturnAllProductDTOs() {
        //arrange
        Product product=Product.builder().productId(1000).name("Apple Phone").price(50000).description("Sumsung Mobile").build();
        Product product1=Product.builder().productId(2000).name("IPhone").price(80000).description("Sumsung Mobile").build();
        List<Product> productList= Arrays.asList(product,product1);
        Mockito.when(productCatalogRepo.findAll()).thenReturn(productList);

        //act
        List<ProductDTO> productDTOList=productService.getAllProducts();

        //assertion
        assertNotNull(productDTOList);
        assertEquals(productList.size(), productDTOList.size());
        assertEquals(productList.get(0).getProductId(), productDTOList.get(0).getProductId());
    }

    @Test
    public void testProductByProductName_WithSuccess_ReturnProductDTO() {

    }

    @Test
    public void testSaveProduct() {
    }
}