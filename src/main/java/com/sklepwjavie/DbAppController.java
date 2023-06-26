package com.sklepwjavie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class DbAppController {
    private final ProductService pService;

    public DbAppController(ProductService p) {
        pService = p;
    }

    @GetMapping("/items")
    public List<Product> getAllProducts() {
        return pService.getAllProducts();
    }
    @GetMapping("/items/{id}")
    public Product getProductById(@PathVariable Long id) {
        Product p = pService.getProductById(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return p;
    }

    @PostMapping("/items")
    public void saveProduct(@RequestBody Product product) {
        product.setFullDate(LocalDateTime.now());
        pService.save(product);
    }

    @PutMapping("/items/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product product) {
        pService.update(id, product);
    }

    @DeleteMapping("/items/{id}")
    public void deleteProduct(@PathVariable Long id) {
        Product p = pService.remove(id);
        if (p == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
