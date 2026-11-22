package com.ua.amazonua.repository;

import com.ua.amazonua.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory реалізація {@link ProductRepository} (заняття 1/2 — ще без БД).
 *
 * ШАР: repository (реалізація). Відповідає ЛИШЕ за зберігання/пошук
 * (SRP) — наповнення стартовими даними винесено в окремий ProductSeeder.
 *
 * Сховище — {@link ConcurrentHashMap}; id видає {@link AtomicLong}.
 * На кроці 2 цей клас замінить JPA-реалізація за тим самим інтерфейсом.
 */
@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Product> findByCategory(String category) {
        return storage.values().stream()
                .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(sequence.incrementAndGet());
        }
        storage.put(product.getId(), product);
        return product;
    }

    @Override
    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }
}
