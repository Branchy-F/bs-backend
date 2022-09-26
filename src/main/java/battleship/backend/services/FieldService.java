package battleship.backend.services;

import battleship.backend.jsons.Coordinates;
import battleship.backend.jsons.ServerResponse;
import battleship.backend.models.Field;
import org.springframework.stereotype.Service;

@Service
public class FieldService {

    private SessionService sessionService;

    public FieldService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public ServerResponse addShipAndCheck(Coordinates coordinates) {
        Field field = sessionService.getPlayerField(coordinates.getId());
        field.tryToAddShip(coordinates.getX(), coordinates.getY());
        return createResponse(field);
    }

    public ServerResponse deleteShipAndCheck(Coordinates coordinates) {
        Field field = sessionService.getPlayerField(coordinates.getId());
        field.deleteShip(coordinates.getX(), coordinates.getY());
        return createResponse(field);
    }

    private ServerResponse createResponse(Field field) {
        ServerResponse response = new ServerResponse(field.isValid(),
                                                     field.isAllShipHaveRightPosition(),
                                                     field.getBattleship(),
                                                     field.getCruisers(),
                                                     field.getDestroyers(),
                                                     field.getSubmarines());

        if (field.isAllShipHaveRightPosition()) { response.setField(field.getField()); }
        return response;
    }
}
