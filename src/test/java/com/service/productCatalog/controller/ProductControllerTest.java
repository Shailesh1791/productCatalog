package com.service.productCatalog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.productCatalog.config.DatabaseConfig1;
import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.service.IProductService;
import com.service.productCatalog.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ImportAutoConfiguration(exclude = {DatabaseConfig1.class})
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
    public void testProductByProductId_WithSusscess() throws Exception {
        //arrange
        long productId = 200;
        ProductDTO productDTO = ProductDTO.builder()
                .name("IPhone").price(150000)
                .description("Apple Mobile").category("Mobile").productId(productId).build();
        Mockito.when(productService.getProductByProductId(productId)).thenReturn(productDTO);

        //act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/api/v1/products/" + productId)
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
        long productId = -101;
        Mockito.when(productService.getProductByProductId(productId)).thenReturn(null);

        //act
        MvcResult mvcResult = mockMvc.perform(get("http://localhost:8080/api/v1/products/" + productId)
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
        MvcResult mvcResult=mockMvc.perform(get("http://localhost:8080/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        //assertion
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(2, objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class).size());
    }

}
