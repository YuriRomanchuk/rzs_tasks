package com.ua.amazonua.web.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Дані для СТОРІНКИ товару: усі фото, опис і характеристики.
 *
 * ШАР: web/dto. Повертається з {@code GET /api/products/{id}}.
 */
public record ProductDetailDto(
        Long id,
        String name,
        BigDecimal price,
        String imageUrl,
        List<String> images,
        String description,
        String category,
        List<CharacteristicDto> characteristics
) {
}
