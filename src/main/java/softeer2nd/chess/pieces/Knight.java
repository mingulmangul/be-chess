package softeer2nd.chess.pieces;

import java.util.List;

public class Knight extends Piece {

    protected Knight(Color color) {
        super(color, Type.KNIGHT);
    }

    @Override
    protected List<Direction> getPieceDirections() {
        return Direction.knightDirection();
    }
}
