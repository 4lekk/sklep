package com.sklepwjavie;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private List<Product> products;

    @Autowired
    ProductService(ProductRepository pRepository) throws Exception {
        products = new ArrayList<>();
        productRepository = pRepository;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        var product = productRepository.findById(id);
        Product p = null;
        return product.orElse(p);
    }

    public void save(Product p) {
        productRepository.save(p);
    }
    public Product remove(Long id) {
        var product = productRepository.findById(id);
        Product p = null;
        if (product.isEmpty()) {
            return p;
        }
        productRepository.deleteById(id);
        return product.get();
    }

    @Transactional
    public void update(Long id, Product p) {
        if (productRepository.existsById(id)) {
            Product pp = productRepository.findById(id).get();
            pp.setName(p.getName());
            pp.setPrice(p.getPrice());
            pp.setWeight(p.getWeight());
            pp.setDescription(p.getDescription());
        }
        else {
            throw new IllegalStateException("product with id " + id + " does not exist");
        }
    }
}
