package mancala.persistence;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.QueryConfig;

import mancala.domain.IMancala;
import mancala.domain.Mancala;


public class DBMancalaRepository implements IMancalaRepository {
    private Driver driver;

    public DBMancalaRepository() {
        String dbUri;
        final String dbUser = "neo4j";
        final String dbPassword = "sogyopassword";

        
        try {
            dbUri = "bolt://host.docker.internal:7687";

            driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
            driver.verifyConnectivity();
        } catch (Exception e) {}
        try {
            dbUri = "bolt://localhost:7687";

            driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
            driver.verifyConnectivity();
        } catch (Exception e) {}
        System.out.println("Connection established.");
    }

    public void save(String key, IMancala game) {
        String[] parsedKey = parseKey(key);
        String playerOne = parsedKey[0];
        String playerTwo = parsedKey[1];

        ensureNodeExists(playerOne);
        ensureNodeExists(playerTwo);

        saveGame(playerOne, playerTwo, game);
    };

    public IMancala get(String key) {
        String[] parsedKey = parseKey(key);
        String playerOne = parsedKey[0];
        String playerTwo = parsedKey[1];
        var result = driver.executableQuery("MATCH (p:Person {name: $playerOne})"
            + " - [r:PlayedFirstAgainst] -> (q:Person {name: $playerTwo})"
            + " return r.gameString")
            .withParameters(Map.of("playerOne", playerOne, "playerTwo", playerTwo))
            .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
            .execute();
        return new Mancala(playerOne, playerTwo, result.records().get(0).get("r.gameString").asString()); 
    };

    public boolean containsKey(String key) {
        String[] parsedKey = parseKey(key);
        String playerOne = parsedKey[0];
        String playerTwo = parsedKey[1];
        var result = driver.executableQuery("RETURN EXISTS{MATCH  (p:Person {name: $playerOne}) -[:PlayedFirstAgainst]-> (q:Person {name: $playerTwo})}")
            .withParameters(Map.of("playerOne", playerOne, "playerTwo", playerTwo))
            .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
            .execute();
        return result.records().get(0).get("EXISTS{MATCH  (p:Person {name: $playerOne}) -[:PlayedFirstAgainst]-> (q:Person {name: $playerTwo})}").asBoolean();
    }

    private void ensureNodeExists(String name) {
        var result = driver.executableQuery("MERGE (p:Person {name: $name})")
            .withParameters(Map.of("name", name))
            .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
            .execute();
    }

    private void saveGame(String playerOne, String playerTwo, IMancala mancala) {
        var result = driver.executableQuery("MATCH (p:Person {name: $playerOne})"
            + " MATCH (q:Person {name: $playerTwo})"
            + " MERGE (p) - [r:PlayedFirstAgainst] -> (q)"
            + " ON MATCH SET r.gameString = $gameString"
            + " ON CREATE SET r.gameString = $gameString")
            .withParameters(Map.of("playerOne", playerOne, "playerTwo", playerTwo, "gameString", mancala.saveGameState()))
            .withConfig(QueryConfig.builder().withDatabase("neo4j").build())
            .execute();
    }

    private String[] parseKey(String key) {
        Pattern pattern = Pattern.compile(".*&");
        Matcher matcher = pattern.matcher(key);
        StringBuffer tail = new StringBuffer();
        matcher.find();
        String playerOne = matcher.group().replace("&", "");
        matcher.replaceAll("");
        matcher.appendTail(tail);
        String playerTwo = tail.toString();
        return new String[] {playerOne, playerTwo};
    }
    
}
