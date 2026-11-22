package com.ua.amazonua.web;

import com.ua.amazonua.service.ProductService;
import com.ua.amazonua.web.dto.ProductCardDto;
import com.ua.amazonua.web.dto.ProductDetailDto;
import com.ua.amazonua.web.dto.ProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API товарів.
 *
 * ШАР: web. Залежить лише від {@link ProductService} (інтерфейсу) — DIP.
 * Відповідає тільки за HTTP: прийняти запит, повернути DTO. Жодної логіки.
 *
 * Заняття 1/2 — повний CRUD у пам'яті:
 *   GET    /api/products              → список карток (опц. ?category=...)
 *   GET    /api/products/{id}         → один товар з деталями
 *   POST   /api/products              → створити товар        (201 Created)
 *   PUT    /api/products/{id}         → оновити товар         (200 OK)
 *   DELETE /api/products/{id}         → видалити товар        (204 No Content)
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductCardDto> list(@RequestParam(required = false) String category) {
        return productService.findAll(category);
    }

    @GetMapping("/{id}")
    public ProductDetailDto getOne(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDetailDto create(@RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductDetailDto update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
