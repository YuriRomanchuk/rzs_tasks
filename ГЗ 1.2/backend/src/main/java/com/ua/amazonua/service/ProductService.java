package com.ua.amazonua.service;

import com.ua.amazonua.web.dto.ProductCardDto;
import com.ua.amazonua.web.dto.ProductDetailDto;
import com.ua.amazonua.web.dto.ProductRequest;

import java.util.List;

/**
 * Контракт сервісу товарів (бізнес-логіка).
 *
 * ШАР: service (абстракція). Контролер залежить від цього інтерфейсу, а не
 * від реалізації — DIP. Це ж полегшує тести (можна підставити мок/стаб).
 *
 * Заняття 1/2: до читання (findAll/findById з 1/1) додано повний CRUD —
 * створення, оновлення, видалення — та фільтр за категорією.
 */
public interface ProductService {

    /** Список товарів для карток; якщо category != null — лише цієї категорії. */
    List<ProductCardDto> findAll(String category);

    /** Один товар з повними деталями; кидає ProductNotFoundException, якщо нема. */
    ProductDetailDto findById(Long id);

    /** Створити товар; повертає створений із призначеним id. */
    ProductDetailDto create(ProductRequest request);

    /** Оновити товар за id; кидає ProductNotFoundException, якщо нема. */
    ProductDetailDto update(Long id, ProductRequest request);

    /** Видалити товар за id; кидає ProductNotFoundException, якщо нема. */
    void delete(Long id);
}
