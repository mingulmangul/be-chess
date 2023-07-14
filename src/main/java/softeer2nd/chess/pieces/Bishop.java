package softeer2nd.chess.pieces;

import java.util.List;

public class Bishop extends Piece {

    protected Bishop(Color color) {
        super(color, Type.BISHOP);
    }

    @Override
    protected List<Direction> getPieceDirections() {
        return Direction.diagonalDirection();
    }
}
