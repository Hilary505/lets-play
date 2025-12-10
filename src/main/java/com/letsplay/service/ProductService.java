package com.letsplay.service;

import com.letsplay.exception.ResourceNotFoundException;
import com.letsplay.model.Product;
import com.letsplay.model.User;
import com.letsplay.repository.ProductRepository;
import com.letsplay.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
    
    public Product createProduct(Product product, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            product.setOwnerId(user.getId());
        }
        return productRepository.save(product);
    }
    
    public Product updateProduct(String id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }
    
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
    
    public boolean canModify(String productId, Authentication auth) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return user.getRole().equals("ADMIN") || product.getOwnerId().equals(user.getId());
    }
}
