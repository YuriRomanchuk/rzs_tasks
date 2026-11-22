package com.ua.amazonua.web.dto;

import java.math.BigDecimal;

/**
 * DTO для переліку товарів: містить лише поля, потрібні для одного елемента списку.
 *
 * ШАР: web/dto. Окремий контракт API — назовні не віддаємо доменний Product.
 * record (Java 21) — незмінний носій даних.
 */
public record ProductCardDto(
        Long id,
        String name,
        BigDecimal price,
        String imageUrl
) {
}
