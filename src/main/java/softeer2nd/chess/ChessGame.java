package softeer2nd.chess;

import softeer2nd.chess.board.Board;
import softeer2nd.chess.board.Position;
import softeer2nd.chess.pieces.Piece;

import java.util.List;

public class ChessGame {

    private static final int MAX_TURN = 100;
    private static final String SEPARATOR = " ";
    private static final int SOURCE_INDEX = 1;
    private static final int TARGET_INDEX = 2;

    private final Board board;
    private final ChessMoveValidator validator;
    private int remainingTurns;   // 남은 턴 수
    private Piece.Color currentTurnColor;   // 현재 턴을 진행 중인 팀의 색상
    private boolean isKingCaught;   // 킹을 잡았는지 여부

    public ChessGame(Board board) {
        this.board = board;
        validator = new ChessMoveValidator(board, this);
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
        // KING이 잡혔다면 즉시 게임 종료
        if (isKingCaught) {
            return;
        }
        // 남은 턴 수 감소 및 턴 변경
        remainingTurns--;
        changeTurnColor();
    }

    // 이동 명령어를 분리해 반환한다
    private String[] separateMoveCommand(String command) {
        String[] commands = command.split(SEPARATOR);
        // 이동 명령어 검증
        validator.verifyMoveCommand(commands);
        return commands;
    }

    // 출발지의 기물을 목적지로 이동시킨다
    public void movePiece(String sourcePosition, String targetPosition) {
        // 검증
        Position sourcePos = Position.of(sourcePosition);
        Position targetPos = Position.of(targetPosition);
        validator.verifyMovement(sourcePos, targetPos);
        // 목적지에 있던 기물이 KING인지 확인
        Piece targetPiece = board.findPiece(targetPos);
        isKingCaught = targetPiece.isType(Piece.Type.KING);
        // 기물 이동
        Piece sourcePiece = board.removePiece(sourcePos);
        board.setPiece(targetPos, sourcePiece);
    }

    // 게임의 승자를 계산한다
    public Piece.Color calcGameWinner() {
        // 킹을 잡은 팀이 있다면 해당 팀이 승리
        if (isKingCaught) {
            return currentTurnColor;
        }
        // 점수가 높은 팀이 승리
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
