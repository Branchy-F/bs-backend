package battleship.backend.controllers;

import battleship.backend.jsons.Coordinates;
import battleship.backend.jsons.ServerResponse;
import battleship.backend.services.FieldService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FieldCreatorController {

    private FieldService fieldService;
    ObjectMapper objectMapper = new ObjectMapper();

    public FieldCreatorController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping("/addShip")
    public ServerResponse addShip(@RequestBody Coordinates coordinates) {
        return fieldService.addShipAndCheck(coordinates);
    }

    @PostMapping("/deleteShip")
    public ServerResponse deleteShip(@RequestBody Coordinates coordinates) {
        return fieldService.deleteShipAndCheck(coordinates);
    }
}
