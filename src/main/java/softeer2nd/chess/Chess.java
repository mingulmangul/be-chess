package softeer2nd.chess;

import softeer2nd.chess.board.Board;

import java.util.Scanner;

public class Chess {

    private Scanner sc;

    public void startGame() {
        sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().toLowerCase();

            if (input.equals("start")) {
                Board board = new Board();
                board.initialize();
                System.out.println(board.showBoard());
                playGame(board);
            } else if (input.equals("end")) {
                break;
            }
        }
        sc.close();
    }

    private void playGame(Board board) {
        while (true) {
            String input = sc.nextLine().toLowerCase();

            if (input.startsWith("move")) {
                String[] commands = input.split(" ");
                try {
                    board.move(commands[1], commands[2]);
                    System.out.println(board.showBoard());
                } catch (IllegalArgumentException err) {
                    System.out.println(err.getMessage());
                }
            } else {
                System.out.println("finish game");
                break;
            }
        }
    }

}
