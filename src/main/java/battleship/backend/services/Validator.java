package battleship.backend.services;

import battleship.backend.models.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {
    private final int laengeBattleship = 4;

    public void analyze(Field field) {
        int[][] fieldCopy = Arrays.stream(field.getField()).map(int[]::clone).toArray(int[][]::new);

        boolean allShipHaveRightPosition = true;

        List<List<int[]>> allShip = new ArrayList<>();
        List<int[]> found_ship;
        for (int i = 0; i < fieldCopy.length; i++){
            for (int j = 0; j < fieldCopy[i].length; j++){
                if(fieldCopy[i][j] == 1){
                    found_ship = findAllParts(i, j, fieldCopy);
                    for(int[] ship: found_ship) {
                        if (Arrays.equals(ship, new int[]{-1, -1})) {allShipHaveRightPosition = false;}
                    }
                    if (!found_ship.isEmpty() && found_ship.size() <= laengeBattleship) allShip.add(found_ship);
                    else if (found_ship.size() > laengeBattleship) {allShipHaveRightPosition = false;}
                }
            }
        }

        int battleship = 0, cruisers = 0, destroyers = 0, submarines = 0;
        for (List<int[]> ship: allShip){
            switch (ship.size()) {
                case 1:
                    submarines++; break;
                case 2:
                    destroyers++; break;
                case 3:
                    cruisers++; break;
                case 4:
                    battleship++; break;
            }
        }

        field.setAllShipHaveRightPosition(allShipHaveRightPosition);

        if (allShipHaveRightPosition){
            field.setValid(submarines == 4 && destroyers == 3 && cruisers == 2 && battleship == 1);
            field.setBattleship(battleship);
            field.setCruisers(cruisers);
            field.setDestroyers(destroyers);
            field.setSubmarines(submarines);
        } else { field.setValid(false); }
    }

    private List<int[]> findAllParts(int i, int j, int[][] field) {
        List<int[]> ship = new ArrayList<>();
        int found_part;
        field[i][j] = 8;
        ship.add(new int[]{i,j});                                         // add first part  to the ship
        found_part = findNextPart(field, i, j, 0);            // find second part

        while (true){
            if (found_part == 0) { break; }
            else if (found_part == -1) { return List.of(new int[] {-1,-1}); } // '-1' part has forbidden coordinates
            else {                                                            // find next part
                switch (found_part){
                    case 1: // has a part on the right side
                        j++; break;
                    case 2: // has a bottom part
                        i++; break;
                    case 3: // has a part on the left side
                        j--; break;
                    case 4: // has a part on top
                        i--; break;
                }

                field[i][j] = 8;
                ship.add(new int[]{i,j});                       // add found part to the ship
                found_part = findNextPart(field, i, j, found_part);
            }
        }

        return ship;
    }
    //letzter Teil: '0'-keine Teile gefunden, '1'-links, '2'-oben, '3'-rechts, '4'-unten
    //return: '0'- das Schiff hat keine weitere Teile, '1'-rechts, '2'-unten, '3'-links, '4'-oben
    private int findNextPart(int[][] field, int i, int j, int previous_part) {
        int[][] search_coordinates = new int[][]{{i + 1, j}, {i - 1, j}, {i, j + 1}, {i, j - 1}};
        int[][] forbidden_coordinates;

        switch (previous_part){
            case 1: // Teil links
            case 3: // Teil rechts
                forbidden_coordinates = new int[][]{{i + 1, j + 1}, {i - 1, j - 1}, {i - 1, j + 1}, {i + 1, j - 1}, {i + 1, j}, {i - 1, j}};
                break;
            case 2: // Teil oben
            case 4: // Teil unten
                forbidden_coordinates = new int[][]{{i + 1, j + 1}, {i - 1, j - 1}, {i - 1, j + 1}, {i + 1, j - 1}, {i, j + 1}, {i, j - 1}};
                break;
            default: // keine Teile gefunden
                forbidden_coordinates = new int[][]{{i + 1, j + 1}, {i - 1, j - 1}, {i - 1, j + 1}, {i + 1, j - 1}};
                break;
        }

        for (int[] c: forbidden_coordinates) {
            try {
                if (field[c[0]][c[1]] == 1) { return -1; }
            } catch (IndexOutOfBoundsException ignored){ }
        }

        for (int[] c: search_coordinates) {
            try {
                if (field[c[0]][c[1]] == 1) {
                    if (c[1] > j) { return 1; }     // Teil rechts
                    else if (c[0] > i) { return 2; } // Teil unten
                    else if (c[1] < j) { return 3; } // Teil links
                    else if (c[0] < i) { return 4; } // Teil oben
                }
            } catch (IndexOutOfBoundsException ignored){ }
        }
        return 0;
    }
}
