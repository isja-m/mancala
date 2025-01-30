package mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KalahaBowlTest {

    static NormalBowl bowl;

    @BeforeEach
    public void setupBeforeEach() {
        bowl = new NormalBowl();
    }

    @Test
    public void kalahaCreated() {
        assertTrue(bowl.getNextKalahaBowl() instanceof KalahaBowl);
    }

    @Test
    public void firstBowlAssignedAsNeighbourOfLastBowl() {
        Boolean firstBowlConnected = false;
        Bowl nextBowl = bowl.getNeighbour();
        while (nextBowl != bowl) {
            if (nextBowl.getNeighbour() != null) {
                nextBowl = nextBowl.getNeighbour();
            } else {
                break;
            }
            if (nextBowl == bowl) {
                firstBowlConnected = true;
            }
        }
        assertTrue(firstBowlConnected);
    }

    @Test
    public void playerOneGetsAssignedToFirstKalahaBowl() {
        assertNotNull(bowl.getNextKalahaBowl().getOwner());
    }

    @Test
    public void playerTwoGetsAssignedToSecondKalahaBowl() {
        assertEquals(bowl.getOwner().getOpponent(), bowl.getNextKalahaBowl().getNextKalahaBowl().getOwner());
    }

    @Test
    public void keepOneSeedPassRestSkipsOnOpponentsTurn() {
        bowl.getNextKalahaBowl().getNextKalahaBowl().keepOneSeedPassRest(2);
        assertEquals(0, bowl.getNextKalahaBowl().getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void keepOneSeedPassRestDoesNotSkipOnOwnTurn() {
        bowl.getNextKalahaBowl().keepOneSeedPassRest(2);
        assertEquals(1, bowl.getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void ifLastSeedLandsInKalahaDoNotPassTurn() {
        ((NormalBowl) bowl.getNeighbourAtDistance(2)).doMove();
        assertTrue(bowl.getOwner().getIsMyTurn());
    }

    @Test
    public void kalahaSetsOwnScoreCorrectly() {
        NormalBowl bowlFromState = new NormalBowl("10000000000002004040404040404");
        bowlFromState.sideIsEmpty(bowlFromState.getOwner());
        assertEquals(20, bowlFromState.getOwner().getScore());
    }

    @Test
    public void kalahaSetsOpponentsScoreCorrectly() {
        NormalBowl bowlFromState = new NormalBowl("10000000000002004040404040404");
        bowlFromState.sideIsEmpty(bowlFromState.getOwner());
        assertEquals(28, bowlFromState.getOwner().getOpponent().getScore());
    }

    @Test
    public void sideEmptyAfterEndingInKalaha() {
        NormalBowl bowlFromState = new NormalBowl("10000000000012304040404040400");
        ((NormalBowl) bowlFromState.getNeighbourAtDistance(5)).doMove();
        assertFalse(bowlFromState.getOwner().getGameIsRunning());
    }

    @Test
    public void createFirstKalahaFromGameState() {
        NormalBowl bowlFromState = new NormalBowl("10205050404030104040404040400");
        assertTrue(bowlFromState.getNextKalahaBowl().getSeedCount() == 1);
    }

    @Test
    public void playerTwoGetsAssignedToSecondKalahaBowlWithGamestate() {
        NormalBowl bowlFromState = new NormalBowl("10205050404030104040404040400");
        assertEquals(bowlFromState.getOwner().getOpponent(), bowlFromState.getNextKalahaBowl().getNextKalahaBowl().getOwner());
    }
}
