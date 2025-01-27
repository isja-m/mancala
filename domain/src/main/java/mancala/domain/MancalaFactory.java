package mancala.domain;

public class MancalaFactory implements IMancalaFactory {
    /**
     * Method to create a new Mancala game
     * 
     * @param namePlayer1 The name of the first player
     * @param namePlayer2 The name of the second player
     * @return A new Mancala game
     */
    public IMancala createNewGame(String namePlayer1, String namePlayer2) {
        return new Mancala(namePlayer1, namePlayer2);
    };
}
