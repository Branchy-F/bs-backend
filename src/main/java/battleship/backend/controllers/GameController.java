package battleship.backend.controllers;

import battleship.backend.services.FieldService;
import battleship.backend.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.socket.WebSocketHandler;

@RestController
public class GameController {

    @Autowired
    private WebSocketHandler webSocketHandler;
    private SessionService sessionService;
    private FieldService fieldService;

    public GameController(SessionService sessionService, FieldService fieldService) {
        this.sessionService = sessionService;
        this.fieldService = fieldService;
    }

//    @GetMapping("/start-game")
//    public ResponseEntity<String> startGame (@RequestParam String id) {
//        sessionService.playerIsReady(id);
//    }

    @GetMapping("/getMyField")
    public int[][] getMyField(@RequestParam String id) {
        return sessionService.getPlayerField(id).getField();
    }

    @GetMapping("/getOpponentField")
    public int[][] getOpponentField(@RequestParam String id) {
        return sessionService.getOpponentField(id).getField();
    }


}
