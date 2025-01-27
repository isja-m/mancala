package mancala.domain;

public class NormalBowl extends Bowl {
    public NormalBowl() { // First to be called
        super(14, new Player());
        seedCount = 4;
    }

    public NormalBowl(String gameState) { // First to be called
        super(14, new Player(), gameState);
        if (gameState.charAt(0) == '2') {
            owner.passTurn(this);
        }
        seedCount = Integer.parseInt(gameState.substring(1,3));
    }

    NormalBowl(int numberOfBowls, Bowl firstBowl, Player currentPlayer) {
        super(constructNeighbour(numberOfBowls, firstBowl, currentPlayer), currentPlayer);
        seedCount = 4;
    }

    NormalBowl(int numberOfBowls, Bowl firstBowl, Player currentPlayer, String gameState, int index) {
        super(constructNeighbour(numberOfBowls, firstBowl, currentPlayer, gameState, index), currentPlayer);
        seedCount = Integer.parseInt(gameState.substring(2*index-1,2*index+1));
    }

    private static Bowl constructNeighbour(int numberOfBowls, Bowl firstBowl, Player currentPlayer) {
        if (numberOfBowls == 2 || numberOfBowls == 9) {
            Bowl nextBowl = new KalahaBowl(numberOfBowls - 1,  firstBowl, currentPlayer);
            return nextBowl;
        }else {
            Bowl nextBowl = new NormalBowl(numberOfBowls - 1,  firstBowl, currentPlayer);
            return nextBowl;
        }
    }

    private static Bowl constructNeighbour(int numberOfBowls, Bowl firstBowl, Player currentPlayer, String gameState, int index) {
        if (numberOfBowls == 2 || numberOfBowls == 9) {
            Bowl nextBowl = new KalahaBowl(numberOfBowls - 1,  firstBowl, currentPlayer, gameState, index + 1);
            return nextBowl;
        }else {
            Bowl nextBowl = new NormalBowl(numberOfBowls - 1,  firstBowl, currentPlayer, gameState, index + 1);
            return nextBowl;
        }
    }

    @Override
    Bowl getNextKalahaBowl() {
        if (neighbour instanceof KalahaBowl) {
            return neighbour;
        }
        return neighbour.getNextKalahaBowl();
    }

    public void doMove() {
        if (owner.getIsMyTurn() && !(seedCount == 0)) {
            neighbour.keepOneSeedPassRest(seedCount);
            seedCount = 0;
            owner.passTurn(this);
        }
    }

    @Override
    void keepOneSeedPassRest(int numberOfSeedsGiven) {
        if (numberOfSeedsGiven > 0) {
            seedCount += 1;
            neighbour.keepOneSeedPassRest(numberOfSeedsGiven - 1);
        }
        if (numberOfSeedsGiven == 1 && seedCount == 1) {
            attemptSteal();
        }
    }

    private void attemptSteal() {
        Bowl oppositeBowl = getNeighbourAtDistance(findDistanceToOppositeBowl());
        if (oppositeBowl.getSeedCount() != 0 && owner.getIsMyTurn()) {
            transferToKalaha(owner);
            orderTransferToKalaha();
        }
    }

    int findDistanceToOppositeBowl() { // First to be called
        return neighbour.findDistanceToOppositeBowl(1);
    }

    @Override
    int findDistanceToOppositeBowl(int distanceSoFar) {
        return neighbour.findDistanceToOppositeBowl(distanceSoFar + 1);
    }

    private void transferToKalaha(Player player) { // First to be called
        neighbour.transferToKalaha(player, seedCount);
        seedCount = 0;
    }

    @Override
    void transferToKalaha(Player player, int numberOfSeedsGiven) {
        neighbour.transferToKalaha(player, numberOfSeedsGiven);
    }

    private void orderTransferToKalaha() { // First to be called
        orderTransferToKalaha(owner, 0);
    }

    @Override
    void orderTransferToKalaha(Player player, int distance) {
        if (player == owner) {
            neighbour.orderTransferToKalaha(player, distance + 1); // Determine distance to Kalaha by adding 1 at each bowl
        } else if (distance > 0) {
            neighbour.orderTransferToKalaha(player, distance - 1); // Find opponents bowl at that same distance by subtracting
        } else {
            transferToKalaha(player);
        }
    }

    Boolean sideIsEmpty(Player player) {
        if (player == owner) {
            return getNextKalahaBowl().getNextKalahaBowl().getNeighbour().sideIsEmptySoFar(true);
        } else {
            return getNextKalahaBowl().getNeighbour().sideIsEmptySoFar(true);
        }
    }

    @Override
    Boolean sideIsEmptySoFar(Boolean isEmptySoFar) {
        if (isEmptySoFar && seedCount == 0) {
            return neighbour.sideIsEmptySoFar(true);
        }
        return false;
    }

    public String saveGameState() { // First to be called
        return owner.getIsMyTurn() ? "1" + saveGameState("") : "2" + saveGameState("");
    }

    @Override
    String saveGameState(String stateSoFar) {
        stateSoFar += String.format("%2d", seedCount).replace(' ', '0');
        return neighbour.saveGameState(stateSoFar);
    }
}
