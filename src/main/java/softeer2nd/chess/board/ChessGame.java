package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Piece;
import softeer2nd.exceptions.OutOfBoardException;

import static softeer2nd.chess.board.Board.SIZE;

public class ChessGame {
    private final Board board;

    public ChessGame(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * 체스판의 점수를 계산한다
     *
     * @param color 점수를 계산할 팀의 색상
     * @return 점수
     */
    public double calculatePoint(Piece.Color color) {
        double point = 0.0;
        for (int x = 0; x < SIZE; x++) {
            point += calculateFilePoint(color, x);
        }
        return point;
    }

    private double calculateFilePoint(Piece.Color color, int x) {
        double pawnPoint = 0.0;
        double point = 0.0;

        for (int y = 0; y < SIZE; y++) {
            Piece piece = board.findPiece(Position.of(x, y));

            if (!piece.isColor(color)) {
                continue;
            }

            if (piece.isType(Piece.Type.PAWN)) {
                pawnPoint += piece.getPoint();
            } else {
                point += piece.getPoint();
            }
        }

        if (pawnPoint > 1) {
            pawnPoint /= 2;
        }
        return point + pawnPoint;
    }

    /**
     * 기물을 이동시킨다
     *
     * @param sourcePosition 이동할 기물의 위치
     * @param targetPosition 목적지
     */
    public void move(String sourcePosition, String targetPosition) {
        if (Position.isNotValid(sourcePosition) || Position.isNotValid(targetPosition)) {
            throw new IllegalArgumentException("잘못된 좌표입니다.");
        }
        if (board.findPiece(sourcePosition).getType() == Piece.Type.NONE) {
            throw new IllegalArgumentException("비어있는 칸입니다.");
        }
        Piece piece = board.removePiece(sourcePosition);
        board.addPiece(targetPosition, piece);
    }


}
