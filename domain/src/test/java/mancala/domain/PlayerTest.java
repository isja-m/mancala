package mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player playerOne;

    @BeforeEach
    public void beforeEachSetup() {
        playerOne = new Player();
    }
    
    @Test
    public void playerOneHasOpponent() {
        assertNotNull(playerOne.getOpponent());
    }

    @Test
    public void playerIsOpponentOfOwnOpponent() {
        assertEquals(playerOne, playerOne.getOpponent().getOpponent());
    }

    @Test
    public void exactlyOnePlayerHasTheTurn() {
        assertTrue(playerOne.getIsMyTurn() ^ playerOne.getOpponent().getIsMyTurn());
    }

    @Test
    public void passTurnSetsOwnTurnToFalse() {
        NormalBowl bowl = new NormalBowl();
        playerOne.passTurn(bowl);
        assertFalse(playerOne.getIsMyTurn());
    }

    @Test
    public void passTurnSetsOtherPlayersTurnToTrue() {
        NormalBowl bowl = new NormalBowl();
        playerOne.passTurn(bowl);
        assertTrue(playerOne.getOpponent().getIsMyTurn());
    }

    @Test
    public void cannotPassTurnIfYouDontHaveTurn() {
        NormalBowl bowl = new NormalBowl();
        playerOne.getOpponent().passTurn(bowl);
        assertTrue(playerOne.getIsMyTurn());
    }

    @Test
    public void ifTurnStartsWithEmptyBoardEndGame() {
        NormalBowl bowlFromState = new NormalBowl("10404040404040000000000000024");
        bowlFromState.doMove();
        assertFalse(bowlFromState.getOwner().getOpponent().getGameIsRunning());
    }

    @Test
    public void otherPlayerAlsoKnowsGameHasEnded() {
        NormalBowl bowlFromState = new NormalBowl("10404040404040000000000000024");
        bowlFromState.doMove();
        assertFalse(bowlFromState.getOwner().getGameIsRunning());
    }

    @Test
    public void setTurnFromGameState() {
        NormalBowl bowlFromState = new NormalBowl("20205050404040004040404040400");
        assertFalse(bowlFromState.getOwner().getIsMyTurn());
    }

    @Test
    public void gameEndsIfCurrentPlayerHasNoMoveInGameState() {
        NormalBowl bowlFromState = new NormalBowl("20205050404042400000000000000");
        assertFalse(bowlFromState.getGameIsRunning());
    }
}
