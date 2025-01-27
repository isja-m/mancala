package mancala.domain;

abstract class Bowl {
    protected final Bowl neighbour;
    protected int seedCount;
    protected final Player owner;

    Bowl(int numberOfBowls, Player owner) { // Constructor for the fist bowl that gets created.
        this.neighbour = new NormalBowl(numberOfBowls - 1, this, owner);
        this.owner = owner;
    }

    Bowl(int numberOfBowls, Player owner, String gameState) { // Constructor for the fist bowl that gets created from a given gamestate.
        this.neighbour = new NormalBowl(numberOfBowls - 1, this, owner, gameState, 2);
        this.owner = owner;
    }

    Bowl(Bowl neighbour, Player owner) {
        this.neighbour = neighbour;
        this.owner = owner;
    }

    abstract Bowl getNextKalahaBowl();

    abstract void keepOneSeedPassRest(int numberOfSeedsGiven);

    abstract void transferToKalaha(Player player, int numberOfSeedsGiven);

    abstract void orderTransferToKalaha(Player player, int distance);

    abstract int findDistanceToOppositeBowl(int distanceSoFar);

    abstract Boolean sideIsEmptySoFar(Boolean isEmptySoFar);
    
    abstract String saveGameState(String stateSoFar);

    int getDistanceToNextKalaha() {
        return neighbour instanceof KalahaBowl ? 1 : neighbour.getDistanceToNextKalaha() + 1;
    }

    public Bowl getNeighbourAtDistance(int distance) {
        if (distance == 1) {
            return neighbour;
        } else {
            return neighbour.getNeighbourAtDistance(distance - 1);
        }
    }

    public Bowl getNeighbour() {
        return neighbour;
    }

    public int getSeedCount() {
        return seedCount;
    }

    public Player getOwner() {
        return owner;
    }

    public Boolean getGameIsRunning() {
        return owner.getGameIsRunning();
    }

    public int getScore() {
        return owner.getScore();
    }

    public Boolean getIsMyTurn() {
        return owner.getIsMyTurn();
    }
}
