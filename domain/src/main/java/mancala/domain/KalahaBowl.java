package mancala.domain;

class KalahaBowl extends Bowl {

    KalahaBowl(int numberOfBowls, Bowl firstBowl, Player currentPlayer) {
        super(constructNeighbour(numberOfBowls, firstBowl, currentPlayer), currentPlayer);
        seedCount = 0;
    }

    KalahaBowl(int numberOfBowls, Bowl firstBowl, Player currentPlayer, String gameState, int index) {
        super(constructNeighbour(numberOfBowls, firstBowl, currentPlayer, gameState, index), currentPlayer);
        seedCount = Integer.parseInt(gameState.substring(2*index-1,2*index+1));
    }

    private static Bowl constructNeighbour(int numberOfBowls, Bowl firstBowl, Player currentPlayer) {
        if (numberOfBowls == 1) {
            return firstBowl;
        } else {
            Bowl nextBowl = new NormalBowl(numberOfBowls - 1,  firstBowl, currentPlayer.getOpponent());
            return nextBowl;
        }
    }

    private static Bowl constructNeighbour(int numberOfBowls, Bowl firstBowl, Player currentPlayer, String gameState, int index) {
        if (numberOfBowls == 1) {
            return firstBowl;
        } else {
            Bowl nextBowl = new NormalBowl(numberOfBowls - 1,  firstBowl, currentPlayer.getOpponent(), gameState, index + 1);
            return nextBowl;
        }
    }

    @Override
    Bowl getNextKalahaBowl() {
        return neighbour.getNextKalahaBowl();
    }

    @Override
    void keepOneSeedPassRest(int numberOfSeedsGiven) {
        if (numberOfSeedsGiven == 1 && owner.getIsMyTurn()) {
            owner.receiveAnotherTurn();
            seedCount += 1;
        }
        else if (numberOfSeedsGiven > 1 && owner.getIsMyTurn()) {
            seedCount += 1;
            neighbour.keepOneSeedPassRest(numberOfSeedsGiven - 1);
        } else {
            neighbour.keepOneSeedPassRest(numberOfSeedsGiven);
        }
    }

    @Override
    void transferToKalaha(Player player, int numberOfSeedsGiven) {
        if (player == owner) {
            seedCount += numberOfSeedsGiven;
        } else {
            neighbour.transferToKalaha(player, numberOfSeedsGiven);
        }
    }

    @Override
    void orderTransferToKalaha(Player player, int distance) {
        neighbour.orderTransferToKalaha(player, distance - 1);
    }

    @Override
    int findDistanceToOppositeBowl(int distanceSoFar) {
        return 2 * distanceSoFar;
    }

    @Override
    Boolean sideIsEmptySoFar(Boolean isEmptySoFar) {
        if (isEmptySoFar) {
            owner.setScore(seedCount);
        }
        return true;
    }

    @Override
    String saveGameState(String stateSoFar) {
        stateSoFar += String.format("%2d", seedCount).replace(' ', '0');
        if (stateSoFar.length() >= 28) {
            return stateSoFar;
        }
        return neighbour.saveGameState(stateSoFar);
    }
}
