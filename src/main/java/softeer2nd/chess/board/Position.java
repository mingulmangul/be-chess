package softeer2nd.chess.board;

public class Position {

    private final int rankIndex;
    private final int fileIndex;

    private Position(int rankIndex, int fileIndex) {
        this.rankIndex = rankIndex;
        this.fileIndex = fileIndex;
    }

    // 팩토리 메소드
    public static Position from(String position) {
        int fileIndex = position.charAt(0) - 'a';
        int rankIndex = position.charAt(1) - '1';
        return new Position(rankIndex, fileIndex);
    }

    // 랭크의 인덱스를 반환한다
    public int getRankIndex() {
        return rankIndex;
    }

    // 파일의 인덱스를 반환한다
    public int getFileIndex() {
        return fileIndex;
    }

}
