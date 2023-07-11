package softeer2nd.chess.pieces;

import java.util.Objects;

/**
 * Value Object로 구현
 */
public class Piece implements Comparable<Piece> {

    private final Color color;  // 색상
    private final Type type;   // 이름 (기물의 종류)

    // 팩토리 메소드를 통해서만 생성 -> private 생성자
    private Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    // 흰색 기물 생성
    private static Piece createWhite(Type type) {
        return new Piece(Color.WHITE, type);
    }

    // 검은색 기물 생성
    private static Piece createBlack(Type type) {
        return new Piece(Color.BLACK, type);
    }

    // 비어있는 기물 생성
    public static Piece createBlank() {
        return new Piece(Color.NOCOLOR, Type.NONE);
    }

    public static Piece createWhitePawn() {
        return createWhite(Type.PAWN);
    }

    public static Piece createBlackPawn() {
        return createBlack(Type.PAWN);
    }

    public static Piece createWhiteKnight() {
        return createWhite(Type.KNIGHT);
    }

    public static Piece createBlackKnight() {
        return createBlack(Type.KNIGHT);
    }

    public static Piece createWhiteRook() {
        return createWhite(Type.ROOK);
    }

    public static Piece createBlackRook() {
        return createBlack(Type.ROOK);
    }

    public static Piece createWhiteBishop() {
        return createWhite(Type.BISHOP);
    }

    public static Piece createBlackBishop() {
        return createBlack(Type.BISHOP);
    }

    public static Piece createWhiteQueen() {
        return createWhite(Type.QUEEN);
    }

    public static Piece createBlackQueen() {
        return createBlack(Type.QUEEN);
    }

    public static Piece createWhiteKing() {
        return createWhite(Type.KING);
    }

    public static Piece createBlackKing() {
        return createBlack(Type.KING);
    }

    public char getRepresentation() {
        if (isColor(Color.WHITE)) {
            return type.getWhiteRepresentation();
        }
        return type.getBlackRepresentation();
    }

    public double getPoint() {
        return type.getPoint();
    }

    public boolean isColor(Color color) {
        return this.color.equals(color);
    }

    public boolean isType(Type type) {
        return this.type.equals(type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Piece other = (Piece) obj;
        return this.isColor(other.color) && this.isType(other.type);
    }

    @Override
    public String toString() {
        return this.color + "." + this.type;
    }

    @Override
    public int compareTo(Piece o) {
        return Double.compare(o.getPoint(), this.getPoint());
    }

    public enum Color {
        WHITE, BLACK, NOCOLOR
    }

    public enum Type {
        PAWN('P', 1.0),
        KNIGHT('N', 2.5),
        ROOK('R', 5.0),
        BISHOP('B', 3.0),
        QUEEN('Q', 9.0),
        KING('K', 0.0),
        NONE('.', 0.0);

        private final char representation;
        private final double point;

        Type(char representation, double point) {
            this.representation = representation;
            this.point = point;
        }

        public char getBlackRepresentation() {
            return representation;
        }

        public char getWhiteRepresentation() {
            return Character.toLowerCase(representation);
        }

        public double getPoint() {
            return point;
        }
    }

}
