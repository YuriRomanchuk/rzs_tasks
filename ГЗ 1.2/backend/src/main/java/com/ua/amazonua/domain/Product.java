package com.ua.amazonua.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Доменна модель товару.
 *
 * ШАР: domain. Навмисно БЕЗ Spring-анотацій і без залежностей на фреймворк —
 * це серце застосунку, яке не має знати ні про web, ні про спосіб зберігання.
 * Такий поділ дає DIP: верхні шари залежать від домену, а не навпаки.
 *
 * Заняття 1/2: усе ще без БД. Порівняно з 1/1 додано поле category (для фільтра
 * за категорією). На кроці 2 цей клас стане JPA-сутністю (@Entity), але контракт
 * домену лишиться тим самим.
 */
public class Product {

    private Long id;
    private String name;
    private BigDecimal price;

    /** Основне фото (на картці у списку). */
    private String imageUrl;

    /** Усі фото (галерея на сторінці товару). */
    private List<String> images;

    private String description;

    /** Категорія товару (для фільтра у переліку). */
    private String category;

    private List<Characteristic> characteristics;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal price, String imageUrl,
                   List<String> images, String description, String category,
                   List<Characteristic> characteristics) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.images = images;
        this.description = description;
        this.category = category;
        this.characteristics = characteristics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
