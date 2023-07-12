package softeer2nd.chess.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.chess.pieces.PieceFactory;

import static org.assertj.core.api.Assertions.*;
import static softeer2nd.chess.pieces.Piece.Type;

class ChessGameTest {

    Board board;
    ChessGame chessGame;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.initializeEmpty();
        chessGame = new ChessGame(board);
    }

    @Test
    @DisplayName("체스판 위 기물들에 대한 점수를 계산한다")
    void calculatePoint() {
        // given
        board.addPiece(Position.of("b6"), PieceFactory.createBlack(Type.PAWN));  // 1
        board.addPiece(Position.of("e6"), PieceFactory.createBlack(Type.QUEEN)); // 9
        board.addPiece(Position.of("b8"), PieceFactory.createBlack(Type.KING));  // 0
        board.addPiece(Position.of("c8"), PieceFactory.createBlack(Type.ROOK));  // 5
        board.addPiece(Position.of("f2"), PieceFactory.createWhite(Type.PAWN));  // 0.5
        board.addPiece(Position.of("f3"), PieceFactory.createWhite(Type.PAWN));  // 0.5
        board.addPiece(Position.of("e1"), PieceFactory.createWhite(Type.ROOK));  // 5
        board.addPiece(Position.of("f1"), PieceFactory.createWhite(Type.KING));  // 0

        // when
        double blackScore = chessGame.calculatePoint(Piece.Color.BLACK);
        double whiteScore = chessGame.calculatePoint(Piece.Color.WHITE);

        // then
        assertThat(blackScore).isEqualTo(15.0d);
        assertThat(whiteScore).isEqualTo(6.0d);
    }
}
