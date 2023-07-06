package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

import static softeer2nd.chess.pieces.Piece.Color;
import static softeer2nd.chess.pieces.Piece.Type;

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

    /**
     * 특정 색상과 종류를 가진 기물의 개수를 계산한다
     *
     * @param color 기물의 색상
     * @param type  기물의 종류
     * @return 기물의 개수
     */
    public int countPiecesWithColorAndType(Color color, Type type) {
        int count = 0;
        for (Rank rank : chessBoard) {
            count += rank.countPiecesWithColorAndType(color, type);
        }
        return count;
    }

    /**
     * 특정 위치의 기물을 조회한다
     *
     * @param position 위치
     * @return 찾아 낸 기물
     */
    public Piece findPiece(String position) {
        Position pos = Position.from(position);
        return chessBoard.get(pos.getRankIndex()).getPieceAt(pos.getFileIndex());
    }

    /**
     * 특정 위치에 기물을 추가한다
     *
     * @param position 위치
     * @param piece    추가할 기물
     */
    public void setPiece(String position, Piece piece) {
        Position pos = Position.from(position);
        chessBoard.get(pos.getRankIndex())
                .setPiece(pos.getFileIndex(), piece);
    }

    /**
     * 체스판의 점수를 계산한다
     *
     * @param color 점수를 계산할 팀의 색상
     * @return 점수
     */
    public double calculatePoint(Color color) {
        double point = 0.0d;

        for (int fileIndex = 0; fileIndex < SIZE; fileIndex++) {
            double pawnPoint = 0.0d;

            for (int rankIndex = 0; rankIndex < SIZE; rankIndex++) {
                Piece piece = chessBoard.get(rankIndex).getPieceAt(fileIndex);

                if (!piece.getColor().equals(color)) {
                    continue;
                }

                if (piece.getType().equals(Type.PAWN)) {
                    pawnPoint += piece.getPoint();
                } else {
                    point += piece.getPoint();
                }
            }

            if (pawnPoint > 1) {
                pawnPoint /= 2;
            }
            point += pawnPoint;
        }

        return point;
    }
}
