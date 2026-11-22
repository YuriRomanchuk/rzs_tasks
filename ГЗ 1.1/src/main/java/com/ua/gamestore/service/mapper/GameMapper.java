package com.ua.gamestore.service.mapper;

import com.ua.gamestore.domain.Game;
import com.ua.gamestore.web.dto.GameCardDto;
import com.ua.gamestore.web.dto.GameDetailDto;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameCardDto toCard(Game game) {
        Long id = game.getId();
        String title = game.getTitle();
        String studio = game.getStudio();
        return new GameCardDto(id, title, studio, game.getPrice(), game.getCoverUrl());
    }

    public GameDetailDto toDetail(Game game) {
        Long id = game.getId();
        String title = game.getTitle();
        String studio = game.getStudio();
        return new GameDetailDto(id, title, studio, game.getPrice(),
                game.getReleaseYear(), game.getCoverUrl(), game.getDescription(),
                game.getGenre(), game.getPlatform());
    }
}
