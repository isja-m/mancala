package mancala.domain;

import java.util.ArrayList;
import java.util.List;

import mancala.domain.IMancala.Winner;

public class Mancala implements IMancala {
    private String playerOneName;
    private String playerTwoName;
    private List<Bowl> bowls;

    public Mancala(String playerOneName, String playerTwoName) {
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
        initializeGame();
    }
    
    public String getNameOfPlayerOne() {
        return playerOneName;
    };

    public String getNameOfPlayerTwo() {
        return playerTwoName;
    };

    public boolean isPlayersTurn(String name) {
        return (name.equals(playerOneName) && bowls.get(0).getIsMyTurn())
            || (name.equals(playerTwoName) && bowls.get(7).getIsMyTurn());
    };

    public void playPit(int index) {
        if (index != 6 && index != 13) {
            ((NormalBowl) bowls.get(index)).doMove();
        }
    };

    public int getStonesForPit(int index) {
        return bowls.get(index).getSeedCount();
    };

    public boolean isEndOfGame() {
        return (isPlayersTurn(playerOneName) && ((NormalBowl) bowls.get(0)).sideIsEmpty())
            || (isPlayersTurn(playerTwoName) && ((NormalBowl) bowls.get(7)).sideIsEmpty());
    };

    public Winner getWinner() {
        if (bowls.get(6).owner.getScore() == 24 && bowls.get(13).owner.getScore() == 24) {
            return Winner.DRAW;
        } else if (bowls.get(6).owner.getScore() > 24) {
            return Winner.PLAYER_1;
        } else if (bowls.get(13).owner.getScore() > 24) {
            return Winner.PLAYER_2;
        } else {
            return Winner.NO_ONE;
        }
    };

    private void initializeGame() {
        this.bowls = new ArrayList<Bowl>();
        NormalBowl bowl = buildBoard();

        bowls.add(bowl);
        for (int i = 1; i < 14; i++) {
            bowls.add(bowl.getNeighbourAtDistance(i));
        }
    }

    private static NormalBowl buildBoard() {
        String gameState = "10404040404040004040404040400";

        NormalBowl bowl = new NormalBowl(gameState);
        return bowl;
    }


}
