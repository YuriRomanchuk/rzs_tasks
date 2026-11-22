package com.ua.amazonua.domain;

/**
 * Одна характеристика товару: назва + значення (напр. "Колір" → "Чорний").
 *
 * ШАР: domain. Окремий файл (а не вкладений клас) — один тип = один файл,
 * щоб структуру було видно з дерева пакетів.
 */
public class Characteristic {

    private String name;
    private String value;

    public Characteristic() {
    }

    public Characteristic(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
