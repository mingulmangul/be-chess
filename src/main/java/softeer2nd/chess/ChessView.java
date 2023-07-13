package softeer2nd.chess;

import softeer2nd.chess.board.Board;
import softeer2nd.chess.board.Rank;
import softeer2nd.chess.pieces.Piece;

import java.util.List;

import static softeer2nd.utils.StringUtils.appendNewLine;

public class ChessView {

    private static final String COMMAND_SIGN = "> ";
    private static final String PROGRAM_START_MESSAGE = appendNewLine("=======Chess=======")
            + appendNewLine("< 명령어 >")
            + appendNewLine("start : 게임 시작")
            + appendNewLine("end : 체스 프로그램 종료")
            + COMMAND_SIGN;
    private static final String GAME_START_MESSAGE = appendNewLine("~~ 새로운 체스 게임을 시작합니다 ~~")
            + appendNewLine("< 게임 방법 >")
            + appendNewLine("1. 자신의 차례가 되면 명령어를 입력합니다.")
            + appendNewLine("\t- move [from] [to] : [from]에서 [to]로 기물 이동")
            + appendNewLine("2. 상대방의 KING을 잡으면 승리합니다.")
            + appendNewLine("3. 100 턴이 지나도 승부가 나지 않으면 남은 기물에 대한 점수로 승부를 판별합니다.")
            + "----------------------";
    private static final String TURN_START_MESSAGE = appendNewLine(" 의 차례입니다. 명령어를 입력헤주세요") + COMMAND_SIGN;
    private static final String ERROR_MESSAGE = "[[ERROR]] ";


    private final Board board;

    public ChessView(Board board) {
        this.board = board;
    }

    // 체스판을 출력한다
    public String printBoard() {
        // TODO: 보드 출력 Board로.. (rank 접근 x)
        StringBuilder sb = new StringBuilder();
        List<Rank> ranks = board.getRanks();
        for (int y = Board.SIZE - 1; y >= 0; y--) {
            sb.append(ranks.get(y).showRank());
        }
        return appendNewLine(sb.toString());
    }

    // 프로그램 시작 안내 메세지를 출력한다
    public void printProgramStartMessage() {
        System.out.print(PROGRAM_START_MESSAGE);
    }

    // 게임 시작 메세지를 출력한다
    public void printGameStartMessage() {
        System.out.print(appendNewLine(GAME_START_MESSAGE));
    }

    // 각 턴의 시작 메세지를 출력한다
    public void printTurnStartMessage(Piece.Color turn) {
        System.out.print(printBoard());
        System.out.print(turn + TURN_START_MESSAGE);
    }

    // 명령어 오류 메세지를 출력한다
    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_MESSAGE + errorMessage);
    }
}
