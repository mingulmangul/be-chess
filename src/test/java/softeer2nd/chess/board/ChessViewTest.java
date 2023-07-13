package softeer2nd.chess.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.ChessView;

import static softeer2nd.utils.StringUtils.appendNewLine;

class ChessViewTest {

    ChessView chessView;

    @BeforeEach
    void setUp() {
        Board board = new Board();
        board.initialize();
        chessView = new ChessView(board);
    }

    @Test
    @DisplayName("체스판을 출력한다")
    void show() {
        String blankRank = appendNewLine("........");
        String initializedBoard = appendNewLine("RNBQKBNR") +
                appendNewLine("PPPPPPPP") +
                blankRank + blankRank + blankRank + blankRank +
                appendNewLine("pppppppp") +
                appendNewLine("rnbqkbnr");

        Assertions.assertThat(chessView.printBoard()).isEqualTo(initializedBoard);
    }
}
