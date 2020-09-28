package com.amit.connect4.service;

import com.amit.connect4.common.DiscColor;
import com.amit.connect4.common.GameStatus;
import com.amit.connect4.dao.Game;
import com.amit.connect4.dao.Player;
import com.amit.connect4.repository.GameRepo;
import com.amit.connect4.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameManager {


    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private PlayerRepo playerRepo;

    @Autowired
    private GameOperation gameOperation;


    public Game createNewGame(String userId, String color) {
        Player player = new Player();
        player.setPlayerId(userId);
        player.setColor(DiscColor.fromValue(color));
        Game game = new Game();

        game.setPlayer1(playerRepo.save(player));

        gameRepo.save(game);
        return game;
    }

    public Game getGame(String gameString) {
        int gameId = Integer.parseInt(gameString);
        Optional<Game> game = gameRepo.findById(gameId);
        if (!game.isPresent()) throw new RuntimeException("game Not present");
        return game.get();
    }

    public Game joinGame(String gameId, String userId) {
        Game game = null;
        game = gameRepo.findById(Integer.parseInt(gameId)).get();
        if (game.getPlayer2() != null) {
            throw new RuntimeException("Game does not have room for new player");
        }
        if (game.getPlayer1().getPlayerId().equals(userId)) {
            throw new RuntimeException("Player already exists for the game");
        }
        Player player = new Player();
        player.setPlayerId(userId);
        DiscColor disColorForPlayer2 =
                game.getPlayer1().getColor().equals(DiscColor.RED) ? DiscColor.YELLOW : DiscColor.RED;
        player.setColor(disColorForPlayer2);
        game.setPlayer2(playerRepo.save(player));
        game.setStatus(GameStatus.STARTED);
        gameRepo.save(game);

        return game;
    }

    public Game play(String gameId, String userId, int column) {
        Game game = null;

        game = gameRepo.findById(Integer.parseInt(gameId)).get();
        if (GameStatus.CREATED.equals(game.getStatus())) {
            throw new RuntimeException("Game does not have enough players");
        }
        if (!(game.getPlayer1().getPlayerId().equals(userId)
                || game.getPlayer2().getPlayerId().equals(userId))) {
            throw new RuntimeException("No game found for given userId");
        }
        if (GameStatus.COMPLETED.equals(game.getStatus())) {
            throw new RuntimeException("Game is already completed and winner is " + game.getWinner());
        }
        Player player =
                game.getPlayer1().getPlayerId().equals(userId) ? game.getPlayer1() : game.getPlayer2();
        if (player.getColor().equals(gameOperation.getLastPlayedDisc(game))) {
            throw new RuntimeException("Game should be played by other player");
        }
        gameOperation.putDisc(game, player.getColor(), column);
        if (gameOperation.calculateWinner(game)) {
            game.setStatus(GameStatus.COMPLETED);
            game.setWinner(userId);
        }
        gameRepo.save(game);

        return game;
    }
}
