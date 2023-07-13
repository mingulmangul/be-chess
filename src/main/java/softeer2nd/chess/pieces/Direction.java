package softeer2nd.chess.pieces;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH(1, 0),
    NORTHEAST(1, 1),
    EAST(0, 1),
    SOUTHEAST(-1, 1),
    SOUTH(-1, 0),
    SOUTHWEST(-1, -1),
    WEST(0, -1),
    NORTHWEST(1, -1),

    NNE(2, 1),
    NNW(2, -1),
    SSE(-2, 1),
    SSW(-2, -1),
    EEN(1, 2),
    EES(-1, 2),
    WWN(1, -2),
    WWS(-1, -2);

    private final int directionX;
    private final int directionY;

    Direction(int directionX, int directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public static List<Direction> linearDirection() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST);
    }

    public static List<Direction> diagonalDirection() {
        return Arrays.asList(NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST);
    }

    public static List<Direction> everyDirection() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST);
    }

    public static List<Direction> knightDirection() {
        return Arrays.asList(NNE, NNW, SSE, SSW, EEN, EES, WWN, WWS);
    }

    public static List<Direction> whitePawnDirection() {
        return Arrays.asList(NORTH, NORTHWEST, NORTHEAST);
    }

    public static List<Direction> blackPawnDirection() {
        return Arrays.asList(SOUTH, SOUTHWEST, SOUTHEAST);
    }

    // △x를 단위 벡터 크기로 변환한다
    public static int convertToUnitDirection(int deltaX) {
        if (deltaX == 0) {
            return deltaX;
        }
        return deltaX / Math.abs(deltaX);
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }
}
