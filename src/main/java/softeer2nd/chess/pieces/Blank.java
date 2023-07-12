package softeer2nd.chess.pieces;

import softeer2nd.exceptions.NoneTypePieceException;

import java.util.List;

public class Blank extends Piece {

    protected Blank() {
        super(Color.NOCOLOR, Type.NONE);
    }

    @Override
    protected List<Direction> getPieceDirections() {
        throw new NoneTypePieceException();
    }
}
