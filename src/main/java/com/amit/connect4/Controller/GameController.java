package com.amit.connect4.Controller;

import com.amit.connect4.Model.Response;
import com.amit.connect4.common.Desision;
import com.amit.connect4.dao.Game;
import com.amit.connect4.service.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    private GameManager gameManager;

    @PostMapping("/new")
    public Response startNewGame(@RequestParam String userId) {
        Response response = new Response();
        try {
            String color = "Y";
            Desision.validateColor(color);
            Game game = gameManager.createNewGame(userId, color);
            response.setGame(game);
            response.setMsg("Create the new game with id :" + game.getGameId());
            return response;
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @GetMapping("/{gameId}")
    public Response getGame(@PathVariable String gameId) {
        Response response = new Response();
        try {
            Game game = gameManager.getGame(gameId);
            response.setGame(game);
            response.setMsg("fetch the  game with id :" + game.getGameId());
            return response;
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            return response;
        }

    }

    @PostMapping(path = "/{gameId}/join/{userId}")
    public Response joinGame(@PathVariable String gameId,
                             @PathVariable String userId) {
        Response response = new Response();
        try {
            Game game = gameManager.joinGame(gameId, userId);
            response.setGame(game);
            response.setMsg("join the  game with id :" + game.getGameId());
            return response;
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            return response;
        }
    }

    @PostMapping(path = "/{gameId}/play/{userId}/{column}")
    public Response playGame(@PathVariable String gameId,
                             @PathVariable String userId, @PathVariable int column) {
        Response response = new Response();
        try {
            if (column < 0 || column > 6) {
                throw new IllegalArgumentException("Column value should be between 0 and 6");
            }
            Game game = gameManager.play(gameId, userId, column);
            response.setGame(game);
            if (game.getWinner() != null) {
                response.setMsg("game winner is " + userId);
            } else {
                response.setMsg("game played by userID" + userId);
            }
            return response;
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            return response;
        }
    }
}
