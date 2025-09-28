package com.service.productCatalog.service;

import com.service.productCatalog.config.ProductMapper;
import com.service.productCatalog.constants.CacheNames;
import com.service.productCatalog.dto.ProductDTO;
import com.service.productCatalog.dto.SearchProductDTO;
import com.service.productCatalog.entity.Product;
import com.service.productCatalog.repo.ProductCatalogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductCatalogRepo productCatalogRepo;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Cacheable(cacheNames = CacheNames.PRODUCTS, key = "#productId")
    public ProductDTO getProductByProductId(long productId) throws IllegalArgumentException {
        if (productId < 0)
            throw new IllegalArgumentException("productId should be positive");
        Product product = this.productCatalogRepo.findById(productId).orElse(null);
        if (product != null)
            return productMapper.toDto(product);
        return null;
    }

    @Override
    @Cacheable(cacheNames = CacheNames.PRODUCTS)
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = this.productCatalogRepo.findAll();
        if (!productList.isEmpty()) {
            List<ProductDTO> productDTOS = new ArrayList<ProductDTO>();
            for (Product product : productList) {
                productDTOS.add(productMapper.toDto(product));
            }
            return productDTOS;
        }
        return null;
    }

    @Override
    public ProductDTO getProductByProductName(String name) {
        Product product = this.productCatalogRepo.findByName(name);
        if (product != null)
            return productMapper.toDto(product);
        return null;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = this.productCatalogRepo.save(productMapper.toEntity(productDTO));
        return productMapper.toDto(product);
    }

    @Override
    @CachePut(cacheNames = CacheNames.PRODUCTS, key = "#productId")
    public ProductDTO updateProduct(long productId, ProductDTO productDTO) {
        Product product = this.productCatalogRepo.findById(productId).orElse(null);
        if (product != null) {
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            this.productCatalogRepo.save(product);
            return productMapper.toDto(product);
        }
        return null;
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.PRODUCTS, key = "#productId")
    public void deleteProduct(long productId) {
        this.productCatalogRepo.deleteById(productId);
    }

    @Override
    public Page<Product> searchProduct(SearchProductDTO searchProductDTO) {
        Pageable pageable = PageRequest.of(searchProductDTO.getPageNumber(), searchProductDTO.getPageSize(), Sort.by("price").descending());
        return this.productCatalogRepo.findByCategory(searchProductDTO.getQuery(), pageable);
    }

    @Override
    public int countProductByCategory(String category) {
        long id = 52;
        Product product = this.productCatalogRepo.findProductById(id);
        System.out.println(product);
        return this.productCatalogRepo.countByCategory(category);
    }

}
