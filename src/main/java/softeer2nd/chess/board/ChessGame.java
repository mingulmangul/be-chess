package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Direction;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.exceptions.InavailablePieceException;
import softeer2nd.exceptions.InvalidCommandException;
import softeer2nd.exceptions.InvalidMovementException;
import softeer2nd.exceptions.NoneTypePieceException;

import java.util.List;

public class ChessGame {
    private final Board board;
    private int remainingTurns;   // 남은 턴 수
    private Piece.Color currentTurnColor;   // 현재 턴을 진행 중인 팀의 색상
    private boolean isKingCaught;

    public ChessGame(Board board) {
        this.board = board;
    }

    // 새로운 체스 게임을 시작하기 위해 체스판과 턴을 초기화한다
    public void initGame() {
        board.initialize();
        remainingTurns = Constant.MAX_TURN;
        currentTurnColor = Piece.Color.BLACK;
        isKingCaught = false;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public Piece.Color getCurrentTurnColor() {
        return currentTurnColor;
    }

    public boolean isKingCaught() {
        return isKingCaught;
    }

    // 현재 턴을 진행 중인 팀을 변경한다
    private void changeTurnColor() {
        if (currentTurnColor == Piece.Color.WHITE) {
            currentTurnColor = Piece.Color.BLACK;
            return;
        }
        currentTurnColor = Piece.Color.WHITE;
    }

    // 하나의 턴을 진행한다
    public void startTurn(String command) {
        String[] commands = separateMoveCommand(command);

        Piece caughtPiece = catchPiece(commands[Constant.SOURCE_INDEX], commands[Constant.TARGET_INDEX]);

        // 상대 팀의 KING을 잡은 경우
        if (caughtPiece.isType(Piece.Type.KING)) {
            isKingCaught = true;
            return;
        }
        // 남은 턴 수 감소 및 턴 변경
        remainingTurns--;
        changeTurnColor();
    }

    // 이동 명령어를 분리해 반환한다
    private String[] separateMoveCommand(String command) {
        String[] commands = command.split(Constant.SEPARATOR);
        // 이동 명령어 검증
        verifyMoveCommand(commands);
        return commands;
    }

    // 이동 명령어를 검증한다
    private void verifyMoveCommand(String[] commands) {
        if (commands.length != Constant.MOVE_COMMAND_LENGTH ||
                !commands[Constant.MOVE_COMMAND_INDEX].equals(Constant.MOVE_COMMAND) ||
                commands[Constant.SOURCE_INDEX].length() != 2 ||
                commands[Constant.TARGET_INDEX].length() != 2) {
            throw new InvalidCommandException();
        }
    }

    // 기물을 목적지로 이동시키고 해당 위치에 있던 기물을 반환한다
    public Piece catchPiece(String sourcePosition, String targetPosition) {
        // 검증
        Position sourcePos = Position.of(sourcePosition);
        Position targetPos = Position.of(targetPosition);
        verifyMovement(sourcePos, targetPos);
        // 이동
        Piece sourcePiece = board.removePiece(sourcePos);
        return board.replacePiece(targetPos, sourcePiece);
    }

    // 기물이 출발지부터 목적지까지 이동할 수 있는지 검증한다
    private void verifyMovement(Position sourcePosition, Position targetPosition) {
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
        if (!piece.isColor(currentTurnColor)) {
            throw new InavailablePieceException();
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
        Position currentPosition = sourcePosition.createNextPositionByDirection(direction);
        while (!currentPosition.equals(targetPosition)) {
            // 이동하는 경로에 다른 기물이 존재하면 예외 발생
            verifyPieceNotInPosition(currentPosition);
            currentPosition = currentPosition.createNextPositionByDirection(direction);
        }
    }

    // 해당 위치가 빈 칸인지 검증한다
    private void verifyPieceNotInPosition(Position position) {
        // 다른 기물이 존재하면 예외 발생
        if (!board.findPiece(position).isBlank()) {
            throw new InvalidMovementException();
        }
    }

    // 게임의 승자를 계산한다
    public Piece.Color calcGameWinner() {
        if (isKingCaught) {
            return currentTurnColor;
        }
        double whitePoint = calculatePoint(Piece.Color.WHITE);
        double blackPoint = calculatePoint(Piece.Color.BLACK);
        if (whitePoint < blackPoint) {
            return Piece.Color.BLACK;
        }
        return Piece.Color.WHITE;
    }

    // 해당 색상의 팀의 점수를 계산한다
    public double calculatePoint(Piece.Color color) {
        double point = 0.0;
        for (int x = 0; x < Board.SIZE; x++) {
            point += calculatePointByFile(color, x);
        }
        return point;
    }

    private double calculatePointByFile(Piece.Color color, int x) {
        double pawnPoint = 0.0;
        double point = 0.0;

        List<Piece> pieces = board.findPiecesByFile(color, x);
        for (Piece piece : pieces) {
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

    // ChessGame 클래스에서 사용하는 상수를 저장하는 정적 내부 클래스
    private static class Constant {

        private static final int MAX_TURN = 100;
        private static final String MOVE_COMMAND = "move";
        private static final String SEPARATOR = " ";
        private static final int MOVE_COMMAND_LENGTH = 3;
        private static final int MOVE_COMMAND_INDEX = 0;
        private static final int SOURCE_INDEX = 1;
        private static final int TARGET_INDEX = 2;

    }
}
