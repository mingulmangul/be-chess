package softeer2nd.chess.pieces;

import softeer2nd.exceptions.InvalidPieceTypeException;

public class PieceFactory {

    private static Piece create(Piece.Color color, Piece.Type type) {
        switch (type) {
            case ROOK:
                return new Rook(color);
            case KNIGHT:
                return new Knight(color);
            case BISHOP:
                return new Bishop(color);
            case QUEEN:
                return new Queen(color);
            case KING:
                return new King(color);
            case PAWN:
                return new Pawn(color);
            default:
                throw new InvalidPieceTypeException();
        }
    }

    public static Piece createWhite(Piece.Type type) {
        return create(Piece.Color.WHITE, type);
    }

    public static Piece createBlack(Piece.Type type) {
        return create(Piece.Color.BLACK, type);
    }

    public static Piece createBlank() {
        return new Blank();
    }
}
