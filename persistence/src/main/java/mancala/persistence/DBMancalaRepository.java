package mancala.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.QueryConfig;
import org.neo4j.driver.Driver;

import mancala.domain.IMancala;


public class DBMancalaRepository implements IMancalaRepository {
    private HashMap<String, IMancala> games; // TEMPORARY, REMOVE WHEN DB IS IMPLEMENTED
    private Driver driver;

    public DBMancalaRepository() {
        this.games = new HashMap<>(); // TEMPORARY, REMOVE WHEN DB IS IMPLEMENTED

        final String dbUri = "bolt://localhost:7687";
        final String dbUser = "neo4j";
        final String dbPassword = "sogyopassword";

        driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
        driver.verifyConnectivity();
        System.out.println("Connection established.");
    }

    public void save(String key, IMancala game) {
        // Parse keystring
        Pattern pattern = Pattern.compile(".*&");
        Matcher matcher = pattern.matcher(key);
        StringBuffer tail = new StringBuffer();
        matcher.find();
        String playerOne = matcher.group().replace("&", "");
        matcher.replaceAll("");
        matcher.appendTail(tail);
        String playerTwo = tail.toString();

        ensureNodeExists(playerOne);
        ensureNodeExists(playerTwo);
        
        games.put(key, game); // TEMPORARY, REMOVE WHEN DB IS IMPLEMENTED
    };

    public IMancala get(String key) {
        return games.get(key); // TEMPORARY, REMOVE WHEN DB IS IMPLEMENTED
    };

    public boolean containsKey(String key) {
        return games.containsKey(key); // TEMPORARY, REMOVE WHEN DB IS IMPLEMENTED
    }

    private void ensureNodeExists(String name) {
        var result = driver.executableQuery("MERGE (p:Person {name: $name})")
            .withParameters(Map.of("name", name))
            .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
            .execute();
    }

    private void saveGame() {
        
    }
    
}
