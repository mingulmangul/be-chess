package softeer2nd.chess.pieces;

import java.util.List;

public class King extends Piece {

    protected King(Color color) {
        super(color, Type.KING);
    }

    @Override
    protected List<Direction> getPieceDirections() {
        return Direction.everyDirection();
    }
}
