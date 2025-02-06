package mancala.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import mancala.domain.IMancala;
import mancala.domain.Mancala;
import mancala.persistence.IMancalaRepository;
import mancala.persistence.MancalaRepository;


public class MancalaRepositoryTest {
    private IMancalaRepository repository;

    @BeforeEach
    private void setUp() {
        repository = new MancalaRepository();
    }

    // @Test
    // public void canLoadSavedGameToRepository() {
    //     IMancala mancala = new Mancala("Allice", "Bob");
    //     repository.save("key", mancala);
    //     assertEquals(mancala.saveGameState(), repository.get("key").saveGameState());
    // }

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
