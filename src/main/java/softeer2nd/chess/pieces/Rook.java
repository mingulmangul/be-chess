package softeer2nd.chess.pieces;

import java.util.List;

public class Rook extends Piece {

    protected Rook(Color color) {
        super(color, Type.ROOK);
    }

    @Override
    protected List<Direction> getPieceDirections() {
        return Direction.linearDirection();
    }
}
