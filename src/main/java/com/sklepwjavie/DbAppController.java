package com.sklepwjavie;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/items")
public class DbAppController {

    List<String> sortModes =
            Arrays.asList("nazwa", "cena", "waga", "data");
    String currentSortLabel;
    Comparator<Product> currentSortMode;
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
        currentSortLabel = "nazwa";
        currentSortMode = nameSort;
    }

    private void addFormattedDateToProducts() {
        for (Product p : pService.getAllProducts()) {
            p.setFormattedDate(p.getFullDate()
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy, kk:mm")));
        }
    }

    @GetMapping("index")
    public String getAllProducts(Model model) {
        model.addAttribute("sort", currentSortMode);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }

    @GetMapping("index/nazwa")
    public String getAllProductsSortedByName(Model model) {
        currentSortLabel = "nazwa";
        currentSortMode = nameSort;
        model.addAttribute("sort", currentSortMode);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }

    @GetMapping("index/cena")
    public String getAllProductsSortedByPrice(Model model) {
        currentSortLabel = "cena";
        currentSortMode = priceSort;
        model.addAttribute("sort", currentSortMode);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }

    @GetMapping("index/waga")
    public String getAllProductsSortedByWeight(Model model) {
        currentSortLabel = "waga";
        currentSortMode = weightSort;
        model.addAttribute("sort", currentSortMode);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }

    @GetMapping("index/data")
    public String getAllProductsSortedByDate(Model model) {
        currentSortLabel = "data";
        currentSortMode = dateSort;
        model.addAttribute("sort", currentSortMode);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }

    @GetMapping("addProductForm")
    public String addProductForm(Product product) {
        return "addProductForm";
    }

    @PostMapping("addProduct")
    public String addProduct(@Valid Product product,
                             BindingResult result,
                             Model model) {
        var date = LocalDateTime.now();
        product.setFullDate(date);
        product.setFormattedDate(product.getFullDate()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy, kk:mm")));
        pService.save(product);
        model.addAttribute("sort", currentSortMode);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }

    @GetMapping("updateProductForm/{id}")
    public String updateProductForm(@PathVariable    Long id, Product product, Model model) {
        model.addAttribute("product", pService.getProductById(id).get());
        return "updateProductForm";
    }

    @GetMapping("updateProduct/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid Product product,
                                Model model) {
        Optional<Product> p = pService.update(id, product);
        if (p.isEmpty()) {

        }
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        model.addAttribute("sort", currentSortMode);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }

    @GetMapping("removeProduct/{id}")
    public String deleteProduct(@PathVariable Long id, Product product, Model model) {
        Optional<Product> optionalProduct = pService.remove(id);
        if (optionalProduct.isEmpty()) {
            
        }
        model.addAttribute("sort", currentSortMode);
        model.addAttribute("sortModes", sortModes);
        model.addAttribute("dropDownOptionLabel", currentSortLabel);
        addFormattedDateToProducts();
        model.addAttribute("products", pService.getAllProducts());
        return "index";
    }
}
