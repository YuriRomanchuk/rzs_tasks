package com.ua.amazonua.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Кидаємо, коли товар за id не знайдено.
 *
 * ШАР: exception. {@code @ResponseStatus(NOT_FOUND)} — найпростіший спосіб
 * віддати 404 на 1/1. Централізовану обробку (@RestControllerAdvice) додамо на 1/4.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Товар з id=" + id + " не знайдено");
    }
}
