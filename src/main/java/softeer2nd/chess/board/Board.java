package softeer2nd.chess.board;

import java.util.ArrayList;
import java.util.List;

import static softeer2nd.chess.pieces.Piece.Color;

public class Board {

    public static final int SIZE = 8;

    private final List<Rank> chessBoard = new ArrayList<>();

    public Board() {
        initializeEmpty();  // 빈 체스판 생성
    }

    // 체스판을 비어있는 상태로 초기화한다
    public void initializeEmpty() {
        chessBoard.clear();
        for (int rankIndex = 0; rankIndex < SIZE; rankIndex++) {
            chessBoard.add(Rank.createEmptyRank());
        }
    }

    // 체스판을 게임 시작 상태로 초기화한다
    public void initialize() {
        initializeEmpty();
        chessBoard.get(0).initFirstRank(Color.WHITE);
        chessBoard.get(1).initSecondRank(Color.WHITE);
        chessBoard.get(SIZE - 1).initFirstRank(Color.BLACK);
        chessBoard.get(SIZE - 2).initSecondRank(Color.BLACK);
    }

    // 체스판 위 전체 기물의 개수를 계산한다
    public int pieceCount() {
        int count = 0;
        for (Rank rank : chessBoard) {
            count += rank.countPieces();
        }
        return count;
    }

    // 체스판을 출력한다
    public String showBoard() {
        StringBuilder sb = new StringBuilder();
        for (int rankIndex = SIZE - 1; rankIndex >= 0; rankIndex--) {
            sb.append(chessBoard.get(rankIndex).showRank());
        }
        return sb.toString();
    }

}
