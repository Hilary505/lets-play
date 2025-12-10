package com.letsplay.controller;

import com.letsplay.model.Product;
import com.letsplay.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }
    
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.findById(id);
    }
    
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Product createProduct(@Valid @RequestBody Product product, Authentication auth) {
        return productService.createProduct(product, auth.getName());
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("@productService.canModify(#id, authentication)")
    public Product updateProduct(@PathVariable String id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("@productService.canModify(#id, authentication)")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
