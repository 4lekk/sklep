package com.sklepwjavie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    ProductService(ProductRepository pRepository) {
        productRepository = pRepository;
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product p) {
        productRepository.save(p);
    }
    public Optional<Product> remove(Long id) {
        var optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        }
        return optionalProduct;
    }

    public Optional<Product> update(Long id, Product p) {
        Optional<Product> o = Optional.empty();
        if (productRepository.existsById(id)) {
            Product pp = productRepository.findById(id).get();
            pp.setName(p.getName());
            pp.setPrice(p.getPrice());
            pp.setWeight(p.getWeight());
            pp.setDescription(p.getDescription());
            productRepository.save(pp);
            o = Optional.of(pp);
        }
        return o;
    }
}
