package com.sklepwjavie;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Controller
//@RequestMapping("/items")
public class DbAppController {

    List<String> sortModes =
            Arrays.asList("nazwa", "cena", "waga", "data");
    Comparator<Product> nameSort = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            if (o1.getName().compareToIgnoreCase(o2.getName()) < 0) {
                return -1;
            }
            else if (o1.getName().compareToIgnoreCase(o2.getName()) > 0) {
                return 1;
            }
            else {
                return 0;
            }
        }
    };
    Comparator<Product> priceSort = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            var bd = new BigDecimal(o1.getPrice() - o2.getPrice())
                    .setScale(0, RoundingMode.UP);
            return bd.intValueExact();
        }
    };
    Comparator<Product> weightSort = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            var bd = new BigDecimal(o1.getWeight() - o2.getWeight())
                    .setScale(0, RoundingMode.UP);
            return bd.intValueExact();
        }
    };
    Comparator<Product> dateSort = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            return o1.getFullDate().compareTo(o2.getFullDate());
        }
    };
    private final ProductService pService;

    public DbAppController(ProductService p) {
        pService = p;
    }

    @GetMapping("index")
    public String getAllProducts(Model model) {
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", nameSort);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "nazwa");
        return "index";
    }

    @GetMapping("index/nazwa")
    public String getAllProductsSortedByName(Model model) {
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", nameSort);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "nazwa");
        return "index";
    }

    @GetMapping("index/cena")
    public String getAllProductsSortedByPrice(Model model) {
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", priceSort);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "cena");
        return "index";
    }

    @GetMapping("index/waga")
    public String getAllProductsSortedByWeight(Model model) {
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", weightSort);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "waga");
        return "index";
    }

    @GetMapping("index/data")
    public String getAllProductsSortedByDate(Model model) {
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", dateSort);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "data");
        return "index";
    }





//    @GetMapping("css/style.css")
//    public String getStyle(Model model) {
//        return "../css/style.css";
//    }
    @GetMapping("items/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> p = pService.getProductById(id);
        if (p.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(p.get());
    }

    @GetMapping("addProductForm")
    public String addProductForm(Product product) {
        return "addProductForm";
    }

    @PostMapping("addProduct")
    public String addProduct(@Valid Product product,
                             BindingResult result,
                             Model model) {
        product.setFullDate(LocalDateTime.now());
        pService.save(product);
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", nameSort);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "waga");
        return "index";
    }

    @GetMapping("updateProductForm/{id}")
    public String updateProductForm(@PathVariable    Long id, Product product, Model model) {
        model.addAttribute("product", pService.getProductById(id).get());
//        model.addAttribute("id", id.toString());
        return "updateProductForm";
    }

    @GetMapping("updateProduct/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid Product product,
                                Model model) {
        Optional<Product> p = pService.update(id, product);
        if (p.isEmpty()) {
//            return new ResponseEntity<>("Product with id " + id + " not found",
//                    HttpStatus.NOT_FOUND);

        }
        else {
//            return new ResponseEntity<>("Successfully updated product with id " + id,
//                    HttpStatus.OK);
        }
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "waga");
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", nameSort);
        return "index";
    }

    @GetMapping("removeProduct/{id}")
    public String deleteProduct(@PathVariable Long id, Product product, Model model) {
        Optional<Product> optionalProduct = pService.remove(id);
        if (optionalProduct.isEmpty()) {
//            return new ResponseEntity<String>("Product with id " + id + " not found",
//                    HttpStatus.NOT_FOUND);
        }
        else {

        }
        model.addAttribute("products", pService.getAllProducts());
        model.addAttribute("sort", nameSort);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", "waga");
        return "index";
    }
}
