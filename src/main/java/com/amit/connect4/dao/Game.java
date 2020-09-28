package com.amit.connect4.dao;

import com.amit.connect4.common.DiscColor;
import com.amit.connect4.common.GameStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    @OneToOne
    private Player player1;

    @OneToOne
    private Player player2;

    @JsonIgnore
    private String disc = "000000000000000000000000000000000000000000";

    private GameStatus status = GameStatus.CREATED;

    @JsonIgnore
    private int lastPlayedX = -1;

    @JsonIgnore
    private int lastPlayedY = -1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String winner;

    @Transient
    private DiscColor[][] board;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getLastPlayedX() {
        return lastPlayedX;
    }

    public void setLastPlayedX(int lastPlayedX) {
        this.lastPlayedX = lastPlayedX;
    }

    public int getLastPlayedY() {
        return lastPlayedY;
    }

    public void setLastPlayedY(int lastPlayedY) {
        this.lastPlayedY = lastPlayedY;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public DiscColor[][] getBoard() {
        if (board == null) {
            board = new DiscColor[6][7];
            char[] discChar = disc.toCharArray();
            if (discChar.length != 42) {
                throw new RuntimeException("Unexcpected error incounter in board");
            }
            int index = 0;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    DiscColor color = DiscColor.fromValue(discChar[index++]);
                    board[i][j] = color;
                }
            }
        }
        return board;
    }
}
