package com.service.productCatalog.controller;

import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.dto.SearchProductDTO;
import com.service.productCatalog.entity.Product;
import com.service.productCatalog.service.IProductService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private Environment env;

    @PostMapping("/search")
    public ResponseEntity<Page<Product>> searchProduct(@RequestBody SearchProductDTO searchProductDTO) {
        log.info("searchProductDTO: {}", searchProductDTO);
        Page<Product> productDTOList = this.productService.searchProduct(searchProductDTO);
        log.info("productDTOList: {}", productDTOList);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    //@CircuitBreaker(name = "productCircuitBrek",fallbackMethod = "productfallbackMethod")
    //@Retry(name = "productRetry",fallbackMethod = "productfallbackMethod")
    @TimeLimiter(name = "productTimeLimiter", fallbackMethod = "productfallbackMethod")
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "productfallbackMethod")
    @Bulkhead(name = "productBulkHead", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "productfallbackMethod")
    public ResponseEntity<ProductDTO> getProductByProductId(@PathVariable("productId") long productId) {
        log.info(env.getProperty("local.server.port"));
        ProductDTO product = productService.getProductByProductId(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<ProductDTO> productfallbackMethod(Exception exception) {
       log.info("productfallbackMethod called.........");
        ProductDTO product = ProductDTO.builder().name("dummy").build();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("getAllProducts started");
        List<ProductDTO> products = productService.getAllProducts();
        if (products == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("getAllProducts finished");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/productByName/{productName}")
    public ResponseEntity<ProductDTO> getProductByProductName(@PathVariable("productName") String productName) {
        System.out.println(env.getProperty("local.server.port"));
        ProductDTO product = productService.getProductByProductName(productName);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO product) {
        log.info("saveProduct started ");
        product = productService.saveProduct(product);
        //System.out.println(product.getName());
        //orderService.addOrder(new OrderDTO());
        log.info("saveProduct finished");
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") long productId, @RequestBody ProductDTO product) {
        product = productService.updateProduct(productId, product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/productCount/{categoryName}")
    public ResponseEntity<Integer> productCount(@PathVariable("categoryName") String categoryName) {
        int count = this.productService.countProductByCategory(categoryName);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
