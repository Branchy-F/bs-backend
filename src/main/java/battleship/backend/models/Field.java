package battleship.backend.models;

import battleship.backend.services.Validator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Field {
    @Setter(AccessLevel.NONE)
    private final int[][] field = new int[10][10];

    private boolean isValid = false;
    private boolean allShipHaveRightPosition = true;
    private int battleship;
    private int cruisers;
    private int destroyers;
    private int submarines;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final Validator validator = new Validator();


    public void tryToAddShip(int x, int y) {
        if(field[x][y] == 0) {
            field[x][y] = 1;
            validator.analyze(this);
            if (!allShipHaveRightPosition) { field[x][y] = 0; }
        }
    }

    public void deleteShip(int x, int y) {
        if(field[x][y] == 1) {
            field[x][y] = 0;
            validator.analyze(this);
        }
    }
}
