package softeer2nd.chess;

import softeer2nd.chess.board.Board;

import java.util.Scanner;

public class Chess {

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().toLowerCase();

            if (input.equals("start")) {
                Board board = new Board();
                board.initialize();
                System.out.println(board.showBoard());
            } else if (input.equals("end")) {
                break;
            }
        }
        sc.close();
    }
}
