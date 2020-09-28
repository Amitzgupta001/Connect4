package com.amit.connect4.Model;

import com.amit.connect4.dao.Game;

public class Response {
    private String msg;
    private Game game;


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
