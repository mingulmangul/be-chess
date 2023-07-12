package softeer2nd.chess.pieces;

import java.util.List;

public class Queen extends Piece {

    protected Queen(Color color) {
        super(color, Type.QUEEN);
    }

    @Override
    protected List<Direction> getPieceDirections() {
        return Direction.everyDirection();
    }
}
