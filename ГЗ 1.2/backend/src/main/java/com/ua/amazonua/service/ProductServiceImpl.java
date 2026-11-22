package com.ua.amazonua.service;

import com.ua.amazonua.domain.Product;
import com.ua.amazonua.exception.ProductNotFoundException;
import com.ua.amazonua.repository.ProductRepository;
import com.ua.amazonua.service.mapper.ProductMapper;
import com.ua.amazonua.web.dto.ProductCardDto;
import com.ua.amazonua.web.dto.ProductDetailDto;
import com.ua.amazonua.web.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реалізація {@link ProductService}.
 *
 * ШАР: service (реалізація). Залежить від АБСТРАКЦІЙ:
 *  - {@link ProductRepository} (інтерфейс сховища) — DIP,
 *  - {@link ProductMapper} (мапінг у DTO) — SRP.
 * Залежності приходять через конструктор (IoC/DI).
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductCardDto> findAll(String category) {
        List<Product> products = (category == null || category.isBlank())
                ? repository.findAll()
                : repository.findByCategory(category);
        return products.stream().map(mapper::toCard).toList();
    }

    @Override
    public ProductDetailDto findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.toDetail(product);
    }

    @Override
    public ProductDetailDto create(ProductRequest request) {
        Product product = mapper.toDomain(request);
        product.setId(null);                 // id призначить репозиторій
        Product saved = repository.save(product);
        return mapper.toDetail(saved);
    }

    @Override
    public ProductDetailDto update(Long id, ProductRequest request) {
        // переконуємось, що товар існує (інакше 404)
        repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        Product product = mapper.toDomain(request);
        product.setId(id);                   // зберігаємо той самий id
        Product saved = repository.save(product);
        return mapper.toDetail(saved);
    }

    @Override
    public void delete(Long id) {
        boolean removed = repository.deleteById(id);
        if (!removed) {
            throw new ProductNotFoundException(id);
        }
    }
}
