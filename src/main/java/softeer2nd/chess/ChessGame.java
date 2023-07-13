package softeer2nd.chess;

import softeer2nd.chess.board.Board;
import softeer2nd.chess.board.Position;
import softeer2nd.chess.pieces.Direction;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.exceptions.InvalidCommandException;
import softeer2nd.exceptions.InvalidMovementException;
import softeer2nd.exceptions.NoneTypePieceException;
import softeer2nd.exceptions.UnavailablePieceException;

import java.util.List;

public class ChessGame {

    private static final int MAX_TURN = 100;
    private static final String MOVE_COMMAND = "move";
    private static final String SEPARATOR = " ";
    private static final int COMMANDS_LENGTH = 3;
    private static final int ARGUMENT_LENGTH = 2;
    private static final int COMMAND_INDEX = 0;
    private static final int SOURCE_INDEX = 1;
    private static final int TARGET_INDEX = 2;

    private final Board board;
    private int remainingTurns;   // 남은 턴 수
    private Piece.Color currentTurnColor;   // 현재 턴을 진행 중인 팀의 색상
    private boolean isKingCaught;   // 킹을 잡았는지 여부

    public ChessGame(Board board) {
        this.board = board;
        initGame();
    }

    // 새로운 체스 게임을 시작하기 위해 체스판과 턴을 초기화한다
    private void initGame() {
        board.initialize();
        remainingTurns = MAX_TURN;
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
        movePiece(commands[SOURCE_INDEX], commands[TARGET_INDEX]);
        // 남은 턴 수 감소 및 턴 변경
        remainingTurns--;
        changeTurnColor();
    }

    // 이동 명령어를 분리해 반환한다
    private String[] separateMoveCommand(String command) {
        String[] commands = command.split(SEPARATOR);
        // 이동 명령어 검증
        verifyMoveCommand(commands);
        return commands;
    }

    // 이동 명령어를 검증한다
    private void verifyMoveCommand(String[] commands) {
        if (commands.length != COMMANDS_LENGTH ||
                !commands[COMMAND_INDEX].equals(MOVE_COMMAND) ||
                commands[SOURCE_INDEX].length() != ARGUMENT_LENGTH ||
                commands[TARGET_INDEX].length() != ARGUMENT_LENGTH) {
            throw new InvalidCommandException();
        }
    }

    // 출발지의 기물을 목적지로 이동시킨다
    public void movePiece(String sourcePosition, String targetPosition) {
        // 검증
        Position sourcePos = Position.of(sourcePosition);
        Position targetPos = Position.of(targetPosition);
        verifyMovement(sourcePos, targetPos);
        // 목적지에 있던 기물이 KING인지 확인
        Piece targetPiece = board.findPiece(targetPos);
        isKingCaught = targetPiece.isType(Piece.Type.KING);
        // 기물 이동
        Piece sourcePiece = board.removePiece(sourcePos);
        board.setPiece(targetPos, sourcePiece);
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

    // File(x 좌표)을 기준으로 점수를 계산한다
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
}
