package softeer2nd.chess;

import softeer2nd.chess.board.Board;
import softeer2nd.chess.board.Position;
import softeer2nd.chess.pieces.Direction;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.exceptions.InvalidCommandException;
import softeer2nd.exceptions.InvalidMovementException;
import softeer2nd.exceptions.NoneTypePieceException;
import softeer2nd.exceptions.UnavailablePieceException;

// 체스 기물의 이동 규칙을 검증하고, 관련 명령어가 유효한지 확인하는 역할
public class ChessMoveValidator {

    private static final String MOVE_COMMAND = "move";
    private static final int COMMANDS_LENGTH = 3;
    private static final int ARGUMENT_LENGTH = 2;
    private static final int COMMAND_INDEX = 0;
    private static final int FIRST_ARG_INDEX = 1;
    private static final int SECOND_ARG_INDEX = 2;

    private final Board board;
    private final ChessGame chessGame;

    public ChessMoveValidator(Board board, ChessGame chessGame) {
        this.board = board;
        this.chessGame = chessGame;
    }

    // 이동 명령어를 검증한다
    public void verifyMoveCommand(String[] commands) {
        if (commands.length != COMMANDS_LENGTH ||
                !commands[COMMAND_INDEX].equals(MOVE_COMMAND) ||
                commands[FIRST_ARG_INDEX].length() != ARGUMENT_LENGTH ||
                commands[SECOND_ARG_INDEX].length() != ARGUMENT_LENGTH) {
            throw new InvalidCommandException();
        }
    }

    // 기물이 출발지부터 목적지까지 이동할 수 있는지 검증한다
    public void verifyMovement(Position sourcePosition, Position targetPosition) {
        Piece sourcePiece = board.findPiece(sourcePosition);
        verifySourcePiece(sourcePiece);
        verifyTargetPosition(sourcePosition, targetPosition, sourcePiece);
        verifyAvailableRoute(sourcePosition, targetPosition, sourcePiece);
    }

    // 움직이려는 기물이 올바른지 검증한다
    private void verifySourcePiece(Piece piece) {
        // 기물이 존재하지 않으면(None 타입이면) 예외 발생
        if (piece.isType(Piece.Type.NONE)) {
            throw new NoneTypePieceException();
        }
        // 다른 팀의 기물을 움직이려고 하면 예외 발생
        if (!piece.isColor(chessGame.getCurrentTurnColor())) {
            throw new UnavailablePieceException();
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
            directionX = convertToUnitDirection(dx);
            directionY = convertToUnitDirection(dy);
        }

        // (directionX, directionY)에 따른 Direction 을 계산
        // 해당 기물이 이동할 수 없는 방향이라면 예외 발생
        Direction direction = piece.calcCurrentDirection(directionX, directionY);

        // 목적지에 도착할 때까지 방향 벡터만큼 한 단계씩 이동하며 해당 칸 검증
        Position currentPosition = sourcePosition.createNextPositionByDirection(direction);
        while (!currentPosition.equals(targetPosition)) {
            // 이동하는 경로에 다른 기물이 존재하면 예외 발생
            verifyPieceNotInPosition(currentPosition);
            currentPosition = currentPosition.createNextPositionByDirection(direction);
        }
    }

    // △x를 단위 벡터 크기로 변환한다
    private int convertToUnitDirection(int deltaX) {
        if (deltaX == 0) {
            return deltaX;
        }
        return deltaX / Math.abs(deltaX);
    }

    // 해당 위치가 빈 칸인지 검증한다
    private void verifyPieceNotInPosition(Position position) {
        // 다른 기물이 존재하면 예외 발생
        if (!board.findPiece(position).isBlank()) {
            throw new InvalidMovementException();
        }
    }

}
