package mancala.persistence;

import java.util.HashMap;
import mancala.domain.IMancala;

public class MancalaRepository implements IMancalaRepository {
    private HashMap<String, IMancala> games;

    public MancalaRepository() {
        this.games = new HashMap<>();
    }
    
    public void save(String key, IMancala game) {
        games.put(key, game);
    };

    public IMancala get(String key) {
        return games.get(key);
    };

    public boolean containsKey(String key) {
        return games.containsKey(key);
    }

}
