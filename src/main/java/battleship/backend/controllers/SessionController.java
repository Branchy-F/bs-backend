package battleship.backend.controllers;

import battleship.backend.models.Player;
import battleship.backend.services.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/new-player")
    public ResponseEntity<String> addNewPlayer(@RequestParam String name) {
        Player newPlayer = sessionService.addNewPlayer(name);
        return new ResponseEntity<>(newPlayer.getId(), HttpStatus.OK);
    }

    @GetMapping("/start")
    public ResponseEntity<String> startGame(@RequestParam String id) {
        if(sessionService.gameIsRunning()) {
            return new ResponseEntity<>("The game is already running", HttpStatus.FORBIDDEN);
        } else {
            sessionService.startGame(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/stop")
    public void stopGame() {
        sessionService.stopGame();
    }
}
