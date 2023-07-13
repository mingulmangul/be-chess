package softeer2nd.chess;

import softeer2nd.chess.board.Board;
import softeer2nd.chess.board.ChessGame;
import softeer2nd.exceptions.ChessGameException;
import softeer2nd.exceptions.InvalidCommandException;

import java.util.Scanner;

public class Chess {

    private static final int MAX_TURN = 100;
    private static final String START_COMMAND = "start";
    private static final String END_COMMAND = "end";
    private static final String MOVE_COMMAND = "move";
    private static final int MOVE_COMMAND_LENGTH = 3;
    private static final int MOVE_COMMAND_INDEX = 0;
    private static final int SOURCE_INDEX = 1;
    private static final int TARGET_INDEX = 2;

    private final ChessGame chessGame;
    private final ChessView chessView;
    private Scanner sc;

    // 체스 프로그램을 시작하기 위한 초기화를 진행한다
    public Chess() {
        Board board = new Board();
        chessGame = new ChessGame(board);
        chessView = new ChessView(board);
    }

    // 체스 프로그램을 실행한다
    public void run() {
        sc = new Scanner(System.in);
        // 프로그램 종료 명령어를 입력 받을 때까지 반복
        while (true) {
            chessView.printProgramStartMessage();
            String command = sc.nextLine().toLowerCase();
            try {
                // 명령어 검증
                verifyProgramCommand(command);
                // 프로그램 종료
                if (command.equals(END_COMMAND)) {
                    break;
                }
                // 새로운 체스 게임 시작
                chessGame.initGame();
                chessView.printGameStartMessage();
                playGame();
            } catch (InvalidCommandException e) {
                chessView.printErrorMessage(e.getMessage());
            }
        }
        sc.close();
    }

    // 체스 게임 플레이를 시작한다
    // 게임은 한 팀의 킹이 잡히거나 MAX_TURN(100) 만큼 턴을 반복할 때까지 진행한다
    private void playGame() {
        int turn = 0;
        while (turn < MAX_TURN) {
            chessView.printTurnStartMessage(chessGame.getCurrentTurn());

            String command = sc.nextLine().toLowerCase();
            try {
                String[] commands = separateMoveCommand(command);
                chessGame.move(commands[SOURCE_INDEX], commands[TARGET_INDEX]);
                turn++;
            } catch (ChessGameException e) {
                chessView.printErrorMessage(e.getMessage());
            }
        }
    }

    // 게임 시작과 프로그램 종료 명령어를 검증한다
    private void verifyProgramCommand(String command) {
        if (!command.equals(START_COMMAND) && !command.equals(END_COMMAND)) {
            throw new InvalidCommandException();
        }
    }

    // 이동 명령어를 검증한다
    private String[] separateMoveCommand(String command) {
        String[] commands = command.split(" ");
        if (commands.length != MOVE_COMMAND_LENGTH ||
                !commands[MOVE_COMMAND_INDEX].equals(MOVE_COMMAND)) {
            throw new InvalidCommandException();
        }
        return commands;
    }
}
