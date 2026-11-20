package com.ua.gamestore.config;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.repository.GameRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GameSeeder implements ApplicationRunner {

    private final GameRepository repository;

    public GameSeeder(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        repository.save(new Game(null, "Легенди Козацької Січі", "Volyn Games",
                new BigDecimal("649.00"), 2023, "/covers/kozaky.svg",
                "Історична RPG про пригоди козацького старшини на Дикому Полі.",
                "RPG", "PC, PlayStation 5"));
        repository.save(new Game(null, "Космічний Шлях", "Nova Studio",
                new BigDecimal("899.00"), 2024, "/covers/space.svg",
                "Космічний симулятор дослідження далеких галактик і торгівлі між зорями.",
                "Симулятор", "PC, Xbox Series X"));
        repository.save(new Game(null, "Місто Тіней", "Kyiv Pixel",
                new BigDecimal("499.00"), 2022, "/covers/shadows.svg",
                "Детективна пригода у неоновому місті з нелінійним сюжетом.",
                "Пригоди", "PC, Nintendo Switch"));
        repository.save(new Game(null, "Ферма Діда Панаса", "Sich Soft",
                new BigDecimal("329.00"), 2021, "/covers/farm.svg",
                "Затишний симулятор ферми з українським колоритом і кооперативом.",
                "Симулятор", "PC, Android, iOS"));
        repository.save(new Game(null, "Битва за Небо", "Aero Lab",
                new BigDecimal("749.00"), 2025, "/covers/sky.svg",
                "Аркадний авіасимулятор з динамічними повітряними боями.",
                "Екшн", "PC, PlayStation 5, Xbox Series X"));
    }
}
