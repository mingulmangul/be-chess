package softeer2nd.chess.pieces;

public enum PieceType {

    PAWN('P'),
    KNIGHT('N'),
    ROOK('R'),
    BISHOP('B'),
    QUEEN('Q'),
    KING('K'),
    NONE('.');

    private final char representation;

    PieceType(char representation) {
        this.representation = representation;
    }

    public char getRepresentation() {
        return representation;
    }
}
