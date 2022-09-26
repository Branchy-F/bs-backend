package battleship.backend.jsons;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ServerResponse {
    private int[][] field = null;
    @NonNull private boolean ready;
    @NonNull private boolean allShipHaveRightPosition;
    @NonNull private int battleship;
    @NonNull private int cruisers;
    @NonNull private int destroyers;
    @NonNull private int submarines;
}
