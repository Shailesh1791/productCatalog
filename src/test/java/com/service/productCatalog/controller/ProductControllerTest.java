package com.service.productCatalog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.dto.SearchProductDTO;
import com.service.productCatalog.entity.Product;
import com.service.productCatalog.service.IProductService;
import com.service.productCatalog.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @MockitoBean
    private IProductService productService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSearchProduct_WithSuccess() throws Exception {
        //arrange
        SearchProductDTO searchProductDTO = SearchProductDTO.builder().pageNumber(1).pageSize(10)
                .query("Search by Page").build();
        Product product = Product.builder().name("Iphone").price(150000).productId(100).description("mobile").build();
        Product product2 = Product.builder().name("Iphone 2").price(150000).productId(200).description("mobile").build();
        List<Product> productList = Arrays.asList(product, product2);
        Page<Product> products = new PageImpl<Product>(productList,
                PageRequest.of(1, 10), productList.size());
        Mockito.when(productService.searchProduct(any(SearchProductDTO.class))).thenReturn(products);

        //act
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchProductDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        //assertion
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testProductByProductId_WithSuccess() throws Exception {
        //arrange
        long productId = 200;
        ProductDTO productDTO = ProductDTO.builder()
                .name("IPhone").price(150000)
                .description("Apple Mobile").category("Mobile").productId(productId).build();
        Mockito.when(productService.getProductByProductId(productId)).thenReturn(productDTO);

        //act
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/products/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        //assertion
        assertEquals(200, mvcResult.getResponse().getStatus());
        ProductDTO productDTO1 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class);
        assertEquals(productId, productDTO1.getProductId());
        assertEquals(productDTO.getName(), productDTO1.getName());
    }

    @Test
    public void testProductByProductId_WithFailure() throws Exception {
        //arrange
        long productId = -100;
        Mockito.when(productService.getProductByProductId(productId)).thenReturn(null);

        //act
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/products/{productId}",productId)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //assertion
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAllProduct_WithSuccess() throws Exception {
        //arrange
        ProductDTO productDTO = ProductDTO.builder()
                .productId(100)
                .name("IPhone")
                .price(150000)
                .description("Apple Mobile").category("Mobile").build();
        ProductDTO productDTO2 = ProductDTO.builder()
                .productId(200)
                .name("Samsung")
                .price(150000)
                .description("Samsung Mobile").category("Mobile").build();
        List<ProductDTO> productDTOList = Arrays.asList(productDTO, productDTO2);
        Mockito.when(productService.getAllProducts()).thenReturn(productDTOList);

        //act
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //assertion
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(2, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class).size());
    }

    @Test
    public void testSaveProduct_WithSuccess() throws Exception {
        //arrange
        ProductDTO productDTO= ProductDTO.builder().productId(100).name("Iphone").price(200000).description("Mobile").build();
        Mockito.when(productService.saveProduct(any(ProductDTO.class))).thenReturn(productDTO);

        //act
        MvcResult mvcResult=mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO))).andReturn();

        //assertion
        assertEquals(201, mvcResult.getResponse().getStatus());
        ProductDTO productDTO1 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class);
        assertEquals(productDTO.getName(), productDTO1.getName());
        assertEquals(productDTO.getPrice(), productDTO1.getPrice());
        assertEquals(productDTO.getDescription(), productDTO1.getDescription());
    }

    @Test
    public void testUpdateProduct_WithSuccess() throws Exception {
        //arrange
        ProductDTO productDTO= ProductDTO.builder().productId(100L).name("Iphone").price(200000).description("Mobile").build();
        Mockito.when(productService.updateProduct(anyLong(),any(ProductDTO.class))).thenReturn(productDTO);

        //act
        MvcResult mvcResult=mockMvc.perform(patch("/api/v1/products/{productId}",100L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO))).andReturn();

        //assertion
        assertEquals(200, mvcResult.getResponse().getStatus());
        ProductDTO productDTO1 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDTO.class);
        assertEquals(productDTO.getName(), productDTO1.getName());
        assertEquals(productDTO.getPrice(), productDTO1.getPrice());
        assertEquals(productDTO.getDescription(), productDTO1.getDescription());
    }

    @Test
    public void testDeleteProduct_WithSuccess() throws Exception {
        //arrange
        //act
        MvcResult mvcResult=mockMvc.perform(delete("/api/v1/products/{productId}",100L))
                .andReturn();
        //assertion
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testProductCount() throws Exception {
        //arrange
        String categoryName="Apple";
        Mockito.when(productService.countProductByCategory(categoryName)).thenReturn(2);

        //act
        MvcResult mvcResult=mockMvc.perform(get("/api/v1/products/productCount/{categoryName}",categoryName)
        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        //assertion
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}