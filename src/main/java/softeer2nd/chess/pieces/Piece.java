package softeer2nd.chess.pieces;

public class Piece {

    public static final String WHITE_COLOR = "white";
    public static final String BLACK_COLOR = "black";
    public static final String NONE_COLOR = "none";

    private final String color;         // 색상
    private final PieceType type;   // 이름 (기물의 종류)

//    기본 말을 생성하는 생성자 제거
//    public Piece() {
//        this.color = WHITE_COLOR;
//        this.representation = WHITE_REPRESENTATION;
//    }

    private Piece(String color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    public static Piece createEmptyPiece() {
        return new Piece(NONE_COLOR, PieceType.NONE);
    }

    public static Piece createPawn(String color) {
        return new Piece(color, PieceType.PAWN);
    }

    public static Piece createRook(String color) {
        return new Piece(color, PieceType.ROOK);
    }

    public static Piece createKnight(String color) {
        return new Piece(color, PieceType.KNIGHT);
    }

    public static Piece createBishop(String color) {
        return new Piece(color, PieceType.BISHOP);
    }

    public static Piece createQueen(String color) {
        return new Piece(color, PieceType.QUEEN);
    }

    public static Piece createKing(String color) {
        return new Piece(color, PieceType.KING);
    }

    public String getColor() {
        return this.color;
    }

    public PieceType getType() {
        return type;
    }

    @Override
    public String toString() {
        char representation;
        if (isWhite()) {
            representation = Character.toLowerCase(type.getRepresentation());
        } else {
            representation = type.getRepresentation();
        }
        return String.valueOf(representation);
    }

    public boolean isBlack() {
        return color.equals(BLACK_COLOR);
    }

    public boolean isWhite() {
        return color.equals(WHITE_COLOR);
    }
}
