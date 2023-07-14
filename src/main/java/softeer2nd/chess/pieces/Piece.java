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

    public boolean isSlidingPiece() {
        return this.type.slidingPiece;
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

    // 기물의 이동 가능한 방향(Direction) 리스트를 반환한다
    abstract List<Direction> getPieceDirections();

    // 값이 (directionX, directionY)인 Direction 객체를 찾아 반환한다
    public Direction calcCurrentDirection(int directionX, int directionY) {
        List<Direction> directions = getPieceDirections();
        for (Direction direction : directions) {
            if (direction.getDirectionX() == directionX && direction.getDirectionY() == directionY) {
                return direction;
            }
        }
        // (directionX, directionY)가 기물이 이동 가능한 Direction이 아니면 예외 발생
        throw new InvalidDirectionException();
    }

    public enum Color {
        WHITE, BLACK, NOCOLOR
    }

    public enum Type {
        PAWN('P', 1.0, false),
        KNIGHT('N', 2.5, false),
        ROOK('R', 5.0, true),
        BISHOP('B', 3.0, true),
        QUEEN('Q', 9.0, true),
        KING('K', 0.0, false),
        NONE('.', 0.0, true);

        private final char representation;
        private final double point;
        private final boolean slidingPiece;  // 슬라이딩으로 움직이는지 여부 (슬라이딩: 한번에 여러 칸을 움직임)

        Type(char representation, double point, boolean slidingPiece) {
            this.representation = representation;
            this.point = point;
            this.slidingPiece = slidingPiece;
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
