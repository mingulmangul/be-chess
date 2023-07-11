package softeer2nd.chess.board;

import java.util.List;

import static softeer2nd.chess.board.Board.SIZE;

public class ChessView {

    private final Board board;

    public ChessView(Board board) {
        this.board = board;
    }

    /**
     * 체스판을 출력한다
     *
     * @return 체스판 출력문
     */
    public String showBoard() {
        StringBuilder sb = new StringBuilder();
        List<Rank> ranks = board.getRanks();
        for (int y = SIZE - 1; y >= 0; y--) {
            sb.append(ranks.get(y).showRank());
        }
        return sb.toString();
    }
}
