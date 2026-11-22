package com.ua.amazonua.config;

import com.ua.amazonua.domain.Characteristic;
import com.ua.amazonua.domain.Product;
import com.ua.amazonua.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Наповнює сховище стартовими товарами при запуску застосунку,
 * щоб головна магазину не була порожня.
 *
 * ШАР: config. Винесено ОКРЕМО від репозиторію (SRP): репозиторій зберігає,
 * а сидер — наповнює. Залежить від {@link ProductRepository} (інтерфейсу, не impl),
 * тож на кроці 2 ті самі сид-дані ляжуть у БД без змін тут.
 */
@Component
public class ProductSeeder implements ApplicationRunner {

    private final ProductRepository repository;

    public ProductSeeder(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        repository.save(new Product(null, "Навушники AudioPro X1", new BigDecimal("1299.00"),
                "/images/headphones.svg",
                List.of("/images/headphones.svg", "/images/headphones-2.svg"),
                "Бездротові навушники з активним шумозаглушенням і автономністю до 30 годин.",
                "Аудіо",
                List.of(
                        new Characteristic("Тип", "Накладні"),
                        new Characteristic("Підключення", "Bluetooth 5.3"),
                        new Characteristic("Автономність", "до 30 год"),
                        new Characteristic("Колір", "Чорний")
                )));

        repository.save(new Product(null, "Клавіатура MechKey TKL", new BigDecimal("1890.50"),
                "/images/keyboard.svg",
                List.of("/images/keyboard.svg"),
                "Механічна клавіатура з гарячою заміною перемикачів і RGB-підсвіткою.",
                "Периферія",
                List.of(
                        new Characteristic("Форм-фактор", "TKL (87 клавіш)"),
                        new Characteristic("Перемикачі", "Red, hot-swap"),
                        new Characteristic("Підсвітка", "RGB")
                )));

        repository.save(new Product(null, "Миша GlideMouse Pro", new BigDecimal("749.00"),
                "/images/mouse.svg",
                List.of("/images/mouse.svg"),
                "Легка ігрова миша з сенсором 26000 DPI і вагою 59 г.",
                "Периферія",
                List.of(
                        new Characteristic("Сенсор", "26000 DPI"),
                        new Characteristic("Вага", "59 г"),
                        new Characteristic("Підключення", "USB-C / 2.4 GHz")
                )));

        repository.save(new Product(null, "Монітор ViewMax 27\"", new BigDecimal("8499.00"),
                "/images/monitor.svg",
                List.of("/images/monitor.svg"),
                "27-дюймовий IPS-монітор 2K з частотою 165 Гц для роботи та ігор.",
                "Монітори",
                List.of(
                        new Characteristic("Діагональ", "27\""),
                        new Characteristic("Роздільність", "2560×1440"),
                        new Characteristic("Частота", "165 Гц"),
                        new Characteristic("Матриця", "IPS")
                )));
    }
}
