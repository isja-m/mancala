package mancala.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mancala.domain.IMancala;
import mancala.domain.Mancala;


public class MancalaRepositoryTest {
    private IMancalaRepository repository;

    @BeforeEach
    private void setUp() {
        repository = new MancalaRepository();
    }

    @Test
    public void canLoadSavedGameToRepository() {
        IMancala mancala = new Mancala("Allice", "Bob");
        repository.save("key", mancala);
        assertEquals(mancala.saveGameState(), repository.get("key").saveGameState());
    }

    @Test
    public void findsExistingKey() {
        IMancala mancala = new Mancala("Allice", "Bob");
        repository.save("key", mancala);
        assertEquals(true, repository.containsKey("key"));
    }

    @Test
    public void doesNotFindsNonexistantKey() {
        assertEquals(false, repository.containsKey("key"));
    }

}
