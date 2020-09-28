package com.amit.connect4.service;

import com.amit.connect4.common.DiscColor;
import com.amit.connect4.dao.Game;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

@Service
public class GameOperation {


    public DiscColor getLastPlayedDisc(Game game) {
        if (game.getLastPlayedX() == -1 && game.getLastPlayedY() == -1) {
            return game.getPlayer2().getColor();
        }

        return game.getBoard()[game.getLastPlayedX()][game.getLastPlayedY()];
    }

    public boolean calculateWinner(Game game) {
        game.getBoard();
        return validateInRow(game) || validateInColumn(game) || validateInForwardDiagonal(game)
                || validateInReverseDiagonal(game);
    }

    private boolean validateInRow(Game game) {
        int start = (game.getLastPlayedY() - 3) > 0 ? game.getLastPlayedY() - 3 : 0;
        int end = (game.getLastPlayedY() + 3) < 6 ? game.getLastPlayedY() + 3 : 6;
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (getLastPlayedDisc(game).equals(game.getBoard()[game.getLastPlayedX()][i])) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    @Transient
    private boolean validateInColumn(Game game) {
        int start = (game.getLastPlayedX() - 3) > 0 ? game.getLastPlayedX() - 3 : 0;
        int end = (game.getLastPlayedX() + 3) < 6 ? game.getLastPlayedX() + 3 : 5;
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (getLastPlayedDisc(game).equals(game.getBoard()[i][game.getLastPlayedY()])) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    @Transient
    private boolean validateInForwardDiagonal(Game game) {
        int startX = game.getLastPlayedX();
        int startY = game.getLastPlayedY();
        for (int i = 0; i < 3; i++) {
            if (startX == 0 || startY == 0) {
                break;
            }
            startX--;
            startY--;
        }

        int endX = game.getLastPlayedX();
        int endY = game.getLastPlayedY();
        for (int i = 0; i < 3; i++) {
            if (endX == 5 || endY == 6) {
                break;
            }
            endX++;
            endY++;
        }

        int count = 0;
        for (int i = startX, j = startY; i <= endX || j <= endY; i++, j++) {
            if (getLastPlayedDisc(game).equals(game.getBoard()[i][j])) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    @Transient
    private boolean validateInReverseDiagonal(Game game) {
        int startX = game.getLastPlayedX();
        int startY = game.getLastPlayedY();
        for (int i = 0; i < 3; i++) {
            if (startX == 0 || startY == 6) {
                break;
            }
            startX--;
            startY++;
        }

        int endX = game.getLastPlayedX();
        int endY = game.getLastPlayedY();
        for (int i = 0; i < 3; i++) {
            if (endX == 5 || endY == 0) {
                break;
            }
            endX++;
            endY--;
        }

        int count = 0;
        for (int i = startX, j = startY; i <= endX || j >= endY; i++, j--) {
            if (getLastPlayedDisc(game).equals(game.getBoard()[i][j])) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public void putDisc(Game game, DiscColor discColor, int column) {
        if (!DiscColor.NONE.equals(game.getBoard()[0][column])) {
            throw new RuntimeException("No space for given column");
        }
        for (int i = 5; i >= 0; i--) {
            if (DiscColor.NONE.equals(game.getBoard()[i][column])) {
                game.getBoard()[i][column] = discColor;
                game.setLastPlayedX(i);
                game.setLastPlayedY(column);
                break;
            }
        }
        String str = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                str = str + game.getBoard()[i][j];
            }
        }
        game.setDisc(str);

    }
}
