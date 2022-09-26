package battleship.backend.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Player {
    @NonNull private String id;
    @NonNull private String name;
    private Field field = new Field();
}
