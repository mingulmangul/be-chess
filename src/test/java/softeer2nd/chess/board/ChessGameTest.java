package softeer2nd.chess.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.pieces.Piece;

import static org.assertj.core.api.Assertions.assertThat;

class ChessGameTest {

    Board board;
    ChessGame chessGame;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.initialize();
        chessGame = new ChessGame(board);
    }

    @Test
    @DisplayName("체스판 위 기물을 움직인다")
    void move() {
        // given
        String sourcePosition = "b2";
        String targetPosition = "b3";

        // when
        chessGame.move(sourcePosition, targetPosition);

        // then
        assertThat(board.findPiece(sourcePosition)).isEqualTo(Piece.createBlank());
        assertThat(board.findPiece(targetPosition)).isEqualTo(Piece.createWhitePawn());
    }

    @Test
    @DisplayName("체스판 위 기물들에 대한 점수를 계산한다")
    void calculatePoint() {
        // given
        board.initializeEmpty();
        board.addPiece("b6", Piece.createBlackPawn());  // 1
        board.addPiece("e6", Piece.createBlackQueen()); // 9
        board.addPiece("b8", Piece.createBlackKing());  // 0
        board.addPiece("c8", Piece.createBlackRook());  // 5
        board.addPiece("f2", Piece.createWhitePawn());  // 0.5
        board.addPiece("f3", Piece.createWhitePawn());  // 0.5
        board.addPiece("e1", Piece.createWhiteRook());  // 5
        board.addPiece("f1", Piece.createWhiteKing());  // 0

        // when
        double blackScore = chessGame.calculatePoint(Piece.Color.BLACK);
        double whiteScore = chessGame.calculatePoint(Piece.Color.WHITE);

        // then
        assertThat(blackScore).isEqualTo(15.0d);
        assertThat(whiteScore).isEqualTo(6.0d);
    }
}
