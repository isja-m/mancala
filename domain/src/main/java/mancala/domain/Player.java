package mancala.domain;

class Player {
    private final Player opponent;
    private Boolean isMyTurn;
    private Boolean haveAnotherTurn;
    private Boolean gameIsRunning;
    private int score;

    Player() {
        opponent = new Player(this);
        isMyTurn = true;
        haveAnotherTurn = false;
        gameIsRunning = true;
        score = 0;
    }

    Player(Player opponent) {
        this.opponent = opponent;
        isMyTurn = false;
        haveAnotherTurn = false;
        gameIsRunning = true;
        score = 0;
    }

    Player getOpponent() {
        return opponent;
    }

    Boolean getIsMyTurn() {
        return isMyTurn;
    }

    void receiveAnotherTurn() {
        haveAnotherTurn = true;
    }

    void receiveTurn(NormalBowl bowl) {
        isMyTurn = true;
        gameIsRunning = !bowl.sideIsEmpty(this);
        opponent.setGameIsRunning(gameIsRunning);
    }

    void passTurn(NormalBowl bowl) {
        if (haveAnotherTurn) {
            haveAnotherTurn = false;
            gameIsRunning = !bowl.sideIsEmpty(this);
            opponent.setGameIsRunning(gameIsRunning);
        } else {
            isMyTurn = false;
            opponent.receiveTurn(bowl);
        }
    }

    Boolean getGameIsRunning() {
        return gameIsRunning;
    }

    void setGameIsRunning(Boolean gameIsRunning) {
        this.gameIsRunning = gameIsRunning;
    }

    void setScore(int score) {
        this.score = score;
        if (opponent.getScore() == 0) {
            opponent.setScore(48-score);
        }
    }

    int getScore() {
        return score;
    }
}
