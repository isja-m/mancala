# Mancala

This project is a selfcontained app to play the game 'Mancala' in your browser. It was created as an exercise as part of the Sogyo traineeship.

## Build instructions
You can build this project in a docker container by running
```console
> docker compose up -docker
```
in root folder of the repository.

Alternatively you can run the frontend and backend servers manually by executing the commands in their respective docker files. If you do this, make sure to have a Neo4j database, with username 'neo4j' and password 'sogyopassword', running on port 7687 before you start the backend server. You will also have to change the following code in 'DBMancalaRepository.java' by commenting the first line and uncommenting the second.
```java
final String dbUri = "bolt://host.docker.internal:7687";
// final String dbUri = "bolt://localhost:7687";
```