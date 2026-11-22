package com.ua.gamestore.service;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.exception.GameNotFoundException;
import com.ua.gamestore.repository.GameRepository;
import com.ua.gamestore.service.mapper.GameMapper;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository repository;
    private final GameMapper mapper;

    public GameServiceImpl(GameRepository repository, GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<GameCardDto> findAll() {
        List<Game> games = repository.findAll();
        List<GameCardDto> result = new ArrayList<>();
        for (Game game : games) {
            result.add(mapper.toCard(game));
        }
        return result;
    }

    @Override
    public GameDetailDto findById(Long id) {
        Optional<Game> found = repository.findById(id);
        if (found.isEmpty()) {
            throw new GameNotFoundException(id);
        }
        return mapper.toDetail(found.get());
    }
}
