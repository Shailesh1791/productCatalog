package com.service.productCatalog.controller;

import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.dto.SearchProductDTO;
import com.service.productCatalog.entity.Product;
import com.service.productCatalog.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private IProductService productService;

    @Mock
    private Environment env;

    @Mock
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testSearchProduct_WithSuccess_ReturnProduct(){
        //Arrange
        SearchProductDTO searchProductDTO = SearchProductDTO.builder().pageSize(5).pageNumber(1).build();
        Page<Product> productDTOS=new PageImpl<>(Collections.singletonList(Product.builder().productId(1000)
                .name("Mobile").price(60000).build()));
        Mockito.when(productService.searchProduct(searchProductDTO)).thenReturn(productDTOS);

        //Act
        ResponseEntity<Page<Product>> responseEntity = productController.searchProduct(searchProductDTO);

        //Assert
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(productDTOS,responseEntity.getBody());
        Mockito.verify(productService,Mockito.times(1)).searchProduct(searchProductDTO);
    }

    @Test
    public void testProductByProductId_WithSuccess() {
        //Arrange
        long productId = 1000;
        ProductDTO productDTO=ProductDTO.builder().productId(1000).name("Mobile").price(50000).description("Mobile").build();
        Mockito.when(productService.getProductByProductId(productId)).thenReturn(productDTO);

        //Act
        ResponseEntity<ProductDTO> response = productController.getProductByProductId(productId);

        //Assertion
        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(productDTO,response.getBody());
        assertEquals(productId,response.getBody().getProductId());
        System.out.println("done");
        Mockito.verify(productService,Mockito.times(1)).getProductByProductId(productId);
    }

    @Test
    public void testProductByProductId_WithSusscess() throws Exception {
        //arrange
        long productId = 1000;
        ProductDTO productDTO=ProductDTO.builder().productId(1000).name("Mobile").price(50000).build();
        Mockito.when(productService.getProductByProductId(productId)).thenReturn(productDTO);

        //act
        MvcResult mvcResult=mockMvc.perform(get("http://localhost:8080/productApi/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId",String.valueOf(productId))).andReturn();

        //assertion
        assertNotNull(mvcResult);
        assertEquals(200,mvcResult.getResponse().getStatus());
        //assertEquals(productDTO.toString(),mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testAllProducts_WithSuccess_ReturnAllProducts() {
        //Arrange
        List<ProductDTO> productDTOS=Arrays.asList(ProductDTO.builder().productId(1000).name("Mobile").price(50000).description("Mobile").build());
        Mockito.when(productService.getAllProducts()).thenReturn(productDTOS);

        //Act
        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(productDTOS,response.getBody());
        Mockito.verify(productService,Mockito.times(1)).getAllProducts();
    }

    @Test
    public void testAllProducts_WithSuccess() throws Exception {
        //arrange
        List<ProductDTO> productDTOS=Arrays.asList(ProductDTO.builder().productId(1000).name("Mobile").price(50000).build());
        Mockito.when(productService.getAllProducts()).thenReturn(productDTOS);

        //act
        MvcResult mvcResult=this.mockMvc.perform(get("http://localhost:8080/productApi/products")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //assertion
        assertNotNull(mvcResult);
        assertEquals(200,mvcResult.getResponse().getStatus());
        Mockito.verify(productService,Mockito.times(1)).getAllProducts();
    }

    @Test
    public void getProductByProductName() {
    }

    @Test
    public void saveProduct() {
    }
}