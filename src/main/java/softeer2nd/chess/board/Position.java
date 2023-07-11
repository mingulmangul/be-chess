package softeer2nd.chess.board;

public class Position {

    private final int x;    // File
    private final int y;    // Rank

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 팩토리 메소드
    public static Position of(String position) {
        if (isNotValid(position)) {
            throw new IllegalArgumentException("잘못된 좌표입니다");
        }
        int x = position.charAt(0) - 'a';
        int y = position.charAt(1) - '1';
        return new Position(x, y);
    }

    // 좌표가 유효한 범위 내에 있는지 검증한다
    public static boolean isNotValid(String position) {
        char file = position.charAt(0);
        char rank = position.charAt(1);
        return 'a' > file || file > 'h' || '1' > rank || rank > '8';
    }

    // x(file)를 반환한다
    public int getX() {
        return x;
    }

    // y(rank)를 반환한다
    public int getY() {
        return y;
    }

}
