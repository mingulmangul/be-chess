package softeer2nd.chess.pieces;

import java.util.List;

public class Pawn extends Piece {

    protected Pawn(Color color) {
        super(color, Type.PAWN);
    }

    @Override
    protected List<Direction> getPieceDirections() {
        if (this.isColor(Color.WHITE)) {
            return Direction.whitePawnDirection();
        }
        return Direction.blackPawnDirection();
    }
}
