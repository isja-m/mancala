package mancala.domain;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NormalBowlTest {

    static NormalBowl bowl;

    @BeforeEach
    public void setupBeforeEach() {
        bowl = new NormalBowl();
    }

    @Test
    public void neighbourGetsCreated() {
        assertNotNull(bowl.getNeighbour());
    }

    @Test
    public void bowlCreationDoesNotLoopForever() {
        assertDoesNotThrow(() -> new NormalBowl());
    }

    @Test
    public void kalahaAtCorrectDistance() {
        assertEquals(6, bowl.getDistanceToNextKalaha());
    }

    @Test
    public void playerOneGetsAssignedToFirstBowl() {
        assertNotNull(bowl.getOwner());
    }

    @Test
    public void playerOneGetsAssignedToSecondBowl() {
        assertNotNull(bowl.getNeighbour().getOwner());
    }

    @Test
    public void playerTwoGetsAssignedToEigthBowl() {
        assertEquals(bowl.getOwner().getOpponent(), bowl.getNextKalahaBowl().getNeighbour().getOwner());
    }

    @Test
    public void moveEmptiesBowl() {
        bowl.doMove();
        assertEquals(0, bowl.getSeedCount());
    }

    @Test
    public void keepOneSeedPassRestAddsOneSeed() {
        bowl.keepOneSeedPassRest(2);
        assertEquals(5, bowl.getSeedCount());
    }

    @Test
    public void doMoveDoesNotReachFurtherThanNumberOfSeedsInBowl() {
        bowl.doMove();
        assertEquals(4, bowl.getNeighbourAtDistance(5).getSeedCount());
    }

    @Test
    public void doMoveReachesBowlAtDistanceEqualToNumberOfSeedsInBowl() {
        bowl.doMove();
        assertEquals(5, bowl.getNeighbourAtDistance(4).getSeedCount());
    }

    @Test
    public void cannotDoMoveOnOpponentsBowl() {
        ((NormalBowl) bowl.getNeighbourAtDistance(7)).doMove();
        assertEquals(4, bowl.getNeighbourAtDistance(7).getSeedCount());
    }

    @Test
    public void cannotDoMoveOnEmptyBowl() {
        NormalBowl bowlFromState = new NormalBowl("10004040404040404040404040400");
        bowlFromState.doMove();
        assertEquals(true, bowlFromState.getOwner().getIsMyTurn());
    }

    @Test
    public void turnPassesAfterMove() {
        bowl.doMove();
        assertFalse(bowl.getOwner().getIsMyTurn());
    }

    @Test
    public void stealAddsSeedsToOwnKalaha() {
        NormalBowl bowlFromState = new NormalBowl("10404040400040004040404040404");
        bowlFromState.doMove();
        assertEquals(5, bowlFromState.getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void stealAddsSeedsToOpponentsKalaha() {
        NormalBowl bowlFromState = new NormalBowl("20404040404040404040404000400");
        ((NormalBowl) bowlFromState.getNeighbourAtDistance(7)).doMove();
        assertEquals(5, bowlFromState.getNextKalahaBowl().getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void orderTransferToKalahaTriggersCorrectBowl() {
        bowl.orderTransferToKalaha(bowl.getOwner(), 0);
        assertEquals(0, bowl.getNeighbourAtDistance(12).getSeedCount());
    }

    @Test
    public void orderTransferToKalahaTransfersToCorrectBowl() {
        bowl.orderTransferToKalaha(bowl.getOwner(), 0);
        assertEquals(4, bowl.getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void lastSeedInOwnEmptyBowlLeavesBowlEmptyIfOppositeBowlNotEmpty() {
        NormalBowl bowlFromState = new NormalBowl("10404040404000004040404040404");
        ((NormalBowl) bowlFromState.getNeighbour()).doMove();
        assertEquals(0, bowlFromState.getNeighbourAtDistance(5).getSeedCount());
    }

    @Test
    public void lastSeedInOwnEmptyBowlDoesNotLeaveBowlEmptyIfOppositeBowlEmpty() {
        NormalBowl bowlFromState = new NormalBowl("10404040404000000040404040408");
        ((NormalBowl) bowlFromState.getNeighbour()).doMove();
        assertEquals(1, bowlFromState.getNeighbourAtDistance(5).getSeedCount());
    }

    @Test
    public void lastSeedInOwnEmptyBowlStealsOpponentsSeeds() {
        NormalBowl bowlFromState = new NormalBowl("10404040404000004040404040404");
        ((NormalBowl) bowlFromState.getNeighbour()).doMove();
        assertEquals(0, bowlFromState.getNeighbourAtDistance(7).getSeedCount());
    }

    @Test
    public void lastSeedInOwnEmptyBowlAddsStolenSeedsToKalaha() {
        NormalBowl bowlFromState = new NormalBowl("10404040404000004040404040404");
        ((NormalBowl) bowlFromState.getNeighbour()).doMove();
        assertEquals(5, bowlFromState.getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void lastSeedInOpponentsEmptyBowlDoesNotAddToOpponentsKalaha() {
        NormalBowl bowlFromState = new NormalBowl("10404040404040000040404040404");
        ((NormalBowl) bowlFromState.getNeighbourAtDistance(3)).doMove();
        assertEquals(4, bowlFromState.getNextKalahaBowl().getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void lastSeedInOpponentsEmptyBowlDoesNotAddToOwnKalaha() {
        NormalBowl bowlFromState = new NormalBowl("10404040404000004040404040404");
        ((NormalBowl) bowlFromState.getNeighbourAtDistance(3)).doMove();
        assertEquals(1, bowlFromState.getNextKalahaBowl().getSeedCount());
    }

    @Test
    public void emptySideIsDetectedBySideIsEmpty() {
        NormalBowl bowlFromState = new NormalBowl("10000000000002404040404040400");
        assertTrue(bowlFromState.sideIsEmpty(bowlFromState.getOwner()));
    }

    @Test
    public void fullSideIsNotDetectedBySideIsEmpty() {
        assertFalse(bowl.sideIsEmpty(bowl.getOwner()));
    }

    @Test
    public void createFirstBowlFromGameState() {
        NormalBowl bowlFromState = new NormalBowl("10205050404040004040404040400");
        assertTrue(bowlFromState.getSeedCount() == 2);
    }

    @Test
    public void createSecondBowlFromGameState() {
        NormalBowl bowlFromState = new NormalBowl("10205050404040004040404040400");
        assertTrue(bowlFromState.getNeighbour().getSeedCount() == 5);
    }

    @Test
    public void canLoadTwoDigitNumberFromGameState() {
        NormalBowl bowlFromState = new NormalBowl("10215000003040004040404040400");
        assertTrue(bowlFromState.getNeighbour().getSeedCount() == 15);
    }

    @Test
    public void loadedGameGetsSavedAsSameString() {
        NormalBowl bowlFromState = new NormalBowl("20404040404040000000000000420");
        String gameState = bowlFromState.saveGameState();
        assertEquals("20404040404040000000000000420", gameState);
    }
}
