package battleship.backend.services;

import battleship.backend.models.Field;
import battleship.backend.models.Player;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private volatile boolean gameIsRunning = false;
    private final Map<String, Player> players = new ConcurrentHashMap<>();
    private Player first_player = null;
    private Player second_player = null;


    private final Random random = new Random();

    public boolean gameIsRunning() {
        return gameIsRunning;
    }

    public Player addNewPlayer(String name) {
        String playerId;
        do {
            playerId = HexFormat.of().toHexDigits(random.nextInt(100000 - 2000) + 2000);
        } while (players.containsKey(playerId));

        Player player = new Player(playerId, name);
        players.put(playerId, player);
        return player;
    }

    public void startGame(String id) {
        if (first_player == null) { first_player = players.get(id); }
        else {
            second_player = players.get(id);
            gameIsRunning = true;
        }
    }

    public void stopGame() {
        first_player = null;
        second_player = null;
        gameIsRunning = false;
    }

    public Field getPlayerField(String id) {
        return players.get(id).getField();
    }

    public Field getOpponentField(String id) {
        if (first_player.getId().equals(id)) {
            return second_player.getField();
        } else if (second_player.getId().equals(id)) {
            return first_player.getField();
        } else {
            return null;
        }
    }
}
