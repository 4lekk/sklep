package com.sklepwjavie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class DbAppController {
    private final ProductService pService;

    public DbAppController(ProductService p) {
        pService = p;
    }

    @GetMapping("")
    public List<Product> getAllProducts() {
        return pService.getAllProducts();
    }
    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> p = pService.getProductById(id);
        if (p.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(p.get());
    }

    @PostMapping("")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        product.setFullDate(LocalDateTime.now());
        pService.save(product);
        return new ResponseEntity<String>("Successfully added product", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> p = pService.update(id, product);
        if (p.isEmpty()) {
            return new ResponseEntity<>("Product with id " + id + " not found",
                    HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>("Successfully updated product with id " + id,
                    HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = pService.remove(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<String>("Product with id " + id + " not found",
                    HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<String>("Product with id " + id + " successfully deleted",
                    HttpStatus.OK);
        }
    }
}
