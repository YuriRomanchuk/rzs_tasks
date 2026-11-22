package com.ua.amazonua.web.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Вхідні дані для СТВОРЕННЯ або ОНОВЛЕННЯ товару (тіло запиту POST/PUT).
 *
 * ШАР: web/dto. Окремий від вихідних DTO контракт: клієнт надсилає поля товару,
 * але БЕЗ id — ідентифікатор призначає сервер (при створенні) або передається
 * у шляху (при оновленні). Так вхідний і вихідний контракти не змішуються.
 */
public record ProductRequest(
        String name,
        BigDecimal price,
        String imageUrl,
        List<String> images,
        String description,
        String category,
        List<CharacteristicDto> characteristics
) {
}
