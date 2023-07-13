package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Direction;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.exceptions.InvalidMovementException;
import softeer2nd.exceptions.NoneTypePieceException;

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
            point += calculatePointByFile(color, x);
        }
        return point;
    }

    private double calculatePointByFile(Piece.Color color, int x) {
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
        // 검증
        Position sourcePos = Position.of(sourcePosition);
        Position targetPos = Position.of(targetPosition);
        verifyMovement(sourcePos, targetPos);
        // 이동
        Piece piece = board.removePiece(sourcePos);
        board.addPiece(targetPos, piece);
    }

    // 기물이 출발지부터 목적지까지 이동할 수 있는지 검증한다
    private void verifyMovement(Position sourcePosition, Position targetPosition) {
        Piece srcPiece = board.findPiece(sourcePosition);
        verifyPieceType(srcPiece);
        verifyTargetPosition(sourcePosition, targetPosition, srcPiece);
        verifyAvailableRoute(sourcePosition, targetPosition, srcPiece);
    }

    // 기물이 존재하는지(None 타입이 아닌지) 검증한다
    private void verifyPieceType(Piece piece) {
        if (piece.isType(Piece.Type.NONE)) {
            throw new NoneTypePieceException();
        }
    }

    // 목적지가 현재 기물이 이동할 수 있는 위치인지 검증한다
    private void verifyTargetPosition(Position sourcePosition, Position targetPosition, Piece sourcePiece) {
        // 현재와 동일한 위치로 이동하려 하면 예외 발생
        if (sourcePosition.equals(targetPosition)) {
            throw new InvalidMovementException();
        }
        // 목적지에 같은 편 기물이 있으면 예외 발생
        Piece targetPiece = board.findPiece(targetPosition);
        if (targetPiece.equalColor(sourcePiece)) {
            throw new InvalidMovementException();
        }
    }

    // 목적지까지 기물이 이동 가능한 경로가 있는지 검증한다
    private void verifyAvailableRoute(Position sourcePosition, Position targetPosition, Piece piece) {
        // 목적지와 출발지의 좌표 차이 (∆x, ∆y)
        int dx = targetPosition.getX() - sourcePosition.getX();
        int dy = targetPosition.getY() - sourcePosition.getY();

        // 방향 벡터 (directionX, directionY)
        int directionX = dx;
        int directionY = dy;
        // 슬라이딩으로 이동하는 기물(퀸, 룩, 비숍)은 방향 벡터를 단위 벡터로(크기가 최대 1이 되도록) 변경
        if (piece.isSlidingPiece()) {
            directionX = Direction.convertToUnitDirection(dx);
            directionY = Direction.convertToUnitDirection(dy);
        }

        // (directionX, directionY)에 따른 Direction 을 계산
        // 해당 기물이 이동할 수 없는 방향이라면 예외 발생
        Direction direction = piece.calcCurrentDirection(directionX, directionY);

        // 목적지에 도착할 때까지 방향 벡터만큼 한 단계씩 이동하며 해당 칸 검증
        Position currentPosition = sourcePosition.calcNextStepByDirection(direction);
        while (!currentPosition.equals(targetPosition)) {
            // 이동하는 경로에 다른 기물이 존재하면 예외 발생
            verifyPieceNotInPosition(currentPosition);
            currentPosition = currentPosition.calcNextStepByDirection(direction);
        }
    }

    // 해당 위치가 빈 칸인지 검증한다
    private void verifyPieceNotInPosition(Position position) {
        // 다른 기물이 존재하면 예외 발생
        if (!board.findPiece(position).isBlank()) {
            throw new InvalidMovementException();
        }
    }

}
