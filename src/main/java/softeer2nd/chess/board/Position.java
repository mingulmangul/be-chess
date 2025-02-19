package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Direction;
import softeer2nd.exceptions.OutOfBoardException;

public class Position {

    private final int x;    // File
    private final int y;    // Rank

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 팩토리 메소드
    public static Position of(String position) {
        int x = position.charAt(0) - 'a';
        int y = position.charAt(1) - '1';
        verifyRange(x, y);
        return new Position(x, y);
    }

    public static Position of(int x, int y) {
        verifyRange(x, y);
        return new Position(x, y);
    }

    // 좌표가 유효한 범위 내에 있는지 검증한다
    private static void verifyRange(int x, int y) {
        if ((0 > x) || (x >= 8) || (0 > y) || (y >= 8)) {
            throw new OutOfBoardException();
        }
    }

    public Position createNextPositionByDirection(Direction direction) {
        return Position.of(
                x + direction.getDirectionX(),
                y + direction.getDirectionY());
    }

    // x(file)를 반환한다
    public int getX() {
        return x;
    }

    // y(rank)를 반환한다
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }
}
