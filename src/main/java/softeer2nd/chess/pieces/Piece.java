package softeer2nd.chess.pieces;

import softeer2nd.exceptions.InvalidDirectionException;

import java.util.List;
import java.util.Objects;

/**
 * Value Object로 구현
 */
public abstract class Piece implements Comparable<Piece> {

    protected final Color color;  // 색상
    protected final Type type;   // 이름 (기물의 종류)

    // 팩토리 메소드를 통해서만 생성
    protected Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
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

    public boolean equalColor(Piece other) {
        return this.isColor(other.color);
    }

    public boolean isType(Type type) {
        return this.type.equals(type);
    }

    public boolean isBlank() {
        return this.type.equals(Type.NONE);
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
    public int compareTo(Piece o) {
        return Double.compare(o.getPoint(), this.getPoint());
    }

    abstract List<Direction> getPieceDirections();

    public Direction calcCurrentDirection(int directionX, int directionY) {
        List<Direction> directions = getPieceDirections();
        for (Direction direction : directions) {
            if (direction.getDirectionX() == directionX && direction.getDirectionY() == directionY) {
                return direction;
            }
        }
        throw new InvalidDirectionException();
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
