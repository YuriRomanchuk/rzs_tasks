package com.ua.gamestore.repository;

import com.ua.gamestore.domain.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryGameRepository implements GameRepository {

    private final Map<Long, Game> games = new LinkedHashMap<>();
    private Long nextId = 1L;

    @Override
    public List<Game> findAll() {
        return new ArrayList<>(games.values());
    }

    @Override
    public Optional<Game> findById(Long id) {
        Game game = games.get(id);
        return Optional.ofNullable(game);
    }

    @Override
    public Game save(Game game) {
        if (game.getId() == null) {
            game.setId(nextId);
            nextId = nextId + 1;
        }
        games.put(game.getId(), game);
        return game;
    }
}
