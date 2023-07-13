package softeer2nd.chess.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.chess.pieces.PieceFactory;
import softeer2nd.exceptions.InvalidDirectionException;
import softeer2nd.exceptions.InvalidMovementException;
import softeer2nd.exceptions.OutOfBoardException;

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

    @Nested
    @DisplayName("기물을 이동시킨다")
    class MovePiece {

        private void verifySuccessMove(String srcPos, String targetPos, Piece expected) {
            Throwable throwable = catchThrowable(() -> chessGame.move(srcPos, targetPos));
            assertThat(throwable).isNull();
            assertThat(board.findPiece(Position.of(srcPos))).isEqualTo(PieceFactory.createBlank());
            assertThat(board.findPiece(Position.of(targetPos))).isEqualTo(expected);
        }

        @Test
        @DisplayName("올바른 위치로 기물을 이동시킨다")
        void move() {
            board.addPiece(Position.of("b5"), PieceFactory.createBlack(Type.KING));
            board.addPiece(Position.of("g4"), PieceFactory.createBlack(Type.QUEEN));
            board.addPiece(Position.of("a1"), PieceFactory.createBlack(Type.BISHOP));

            verifySuccessMove("b5", "b6", PieceFactory.createBlack(Type.KING));
            verifySuccessMove("g4", "c4", PieceFactory.createBlack(Type.QUEEN));
            verifySuccessMove("a1", "c3", PieceFactory.createBlack(Type.BISHOP));
        }

        @Nested
        @DisplayName("잘못된 위치로 이동시키려 한다면")
        class invalidMovement {

            @Test
            @DisplayName("입력 위치가 체스판의 범위를 벗어나면 예외가 발생한다")
            void verifyOutOfBoard() {
                String validPosition = "b2";
                String invalidPosition = "i9";

                assertThatExceptionOfType(OutOfBoardException.class).isThrownBy(() ->
                        chessGame.move(validPosition, invalidPosition));
                assertThatExceptionOfType(OutOfBoardException.class).isThrownBy(() ->
                        chessGame.move(invalidPosition, validPosition));
            }

            @Test
            @DisplayName("선택한 기물이 이동할 수 없는 방향으로 움직이면 예외가 발생한다")
            void verifyDirection() {
                String sourcePosition = "d4";
                String targetPosition = "d6";
                Piece blackKing = PieceFactory.createBlack(Type.KING);
                board.addPiece(Position.of(sourcePosition), blackKing);

                assertThatExceptionOfType(InvalidDirectionException.class)
                        .isThrownBy(() -> chessGame.move(sourcePosition, targetPosition));
            }

            @Test
            @DisplayName("같은 편 기물이 위치한 곳으로 움직이면 예외가 발생한다")
            void verifySameColor() {
                String sourcePosition = "d4";
                String targetPosition = "d5";
                Piece blackKing = PieceFactory.createBlack(Type.KING);
                Piece blackPawn = PieceFactory.createBlack(Type.PAWN);
                board.addPiece(Position.of(sourcePosition), blackKing);
                board.addPiece(Position.of(targetPosition), blackPawn);

                assertThatExceptionOfType(InvalidMovementException.class)
                        .isThrownBy(() -> chessGame.move(sourcePosition, targetPosition));
            }
        }

    }

}
