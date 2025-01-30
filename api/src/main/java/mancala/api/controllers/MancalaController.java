package mancala.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mancala.api.models.MancalaDTO;
import mancala.api.models.PlayInputDTO;
import mancala.api.models.StartInputDTO;
import mancala.domain.IMancala;
import mancala.domain.IMancalaFactory;
import mancala.persistence.IMancalaRepository;

@Path("/mancala/api")
public class MancalaController {

    private IMancalaFactory factory;
    private IMancalaRepository repository;

    public MancalaController(IMancalaFactory factory, IMancalaRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    @Path("/start")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response start(@Context HttpServletRequest request, StartInputDTO body) {
        // Create HTTP session.
        HttpSession session = request.getSession(true);

        // Create gameId, based on player names
        String gameId = body.getPlayer1() + "&" + body.getPlayer2();

        // Initialize game, from previous game if it exists.
        IMancala mancala;
        if (repository.containsKey(gameId)) {
            mancala = repository.get(gameId);
        } else {
            mancala = factory.createNewGame(body.getPlayer1(), body.getPlayer2());
        }

        if (mancala.isEndOfGame()) {
            mancala = factory.createNewGame(body.getPlayer1(), body.getPlayer2());
        }

        // Save the ID in the HTTP session.
        session.setAttribute("gameId", gameId);

        // Save the game in the database.
        repository.save(gameId, mancala);

        // Use the game to create a DTO.
        var output = new MancalaDTO(mancala);

        // Send DTO back in response.
        return Response.status(200).entity(output).build();
    }

    @Path("/play")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(@Context HttpServletRequest request, PlayInputDTO body) {
        // Retrieve HTTP session.
        HttpSession session = request.getSession(false);

        // Retrieve game ID.
        String gameId = (String) session.getAttribute("gameId");

        // Retrieve the game from the database
        IMancala mancala = repository.get(gameId);

        // Play a pit.
        mancala.playPit(body.getIndexToPlay());

        // Save the updated game in the database
        repository.save(gameId, mancala);

        // Use the game to create a DTO.
        MancalaDTO output = new MancalaDTO(mancala);

        // Send DTO back in response.
        return Response.status(200).entity(output).build();
    }
}
