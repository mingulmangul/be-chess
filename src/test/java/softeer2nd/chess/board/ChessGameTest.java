package softeer2nd.chess.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.ChessGame;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.chess.pieces.PieceFactory;
import softeer2nd.exceptions.*;

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
        board.initializeEmpty();
        // given
        board.setPiece(Position.of("b6"), PieceFactory.createBlack(Type.PAWN));  // 1
        board.setPiece(Position.of("e6"), PieceFactory.createBlack(Type.QUEEN)); // 9
        board.setPiece(Position.of("b8"), PieceFactory.createBlack(Type.KING));  // 0
        board.setPiece(Position.of("c8"), PieceFactory.createBlack(Type.ROOK));  // 5
        board.setPiece(Position.of("f2"), PieceFactory.createWhite(Type.PAWN));  // 0.5
        board.setPiece(Position.of("f3"), PieceFactory.createWhite(Type.PAWN));  // 0.5
        board.setPiece(Position.of("e1"), PieceFactory.createWhite(Type.ROOK));  // 5
        board.setPiece(Position.of("f1"), PieceFactory.createWhite(Type.KING));  // 0

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
            Throwable throwable = catchThrowable(() -> chessGame.movePiece(srcPos, targetPos));
            assertThat(throwable).isNull();
            assertThat(board.findPiece(Position.of(srcPos))).isEqualTo(PieceFactory.createBlank());
            assertThat(board.findPiece(Position.of(targetPos))).isEqualTo(expected);
        }

        @Test
        @DisplayName("올바른 위치로 기물을 이동시킨다")
        void move() {
            board.initializeEmpty();
            board.setPiece(Position.of("b5"), PieceFactory.createBlack(Type.KING));
            board.setPiece(Position.of("g4"), PieceFactory.createBlack(Type.QUEEN));
            board.setPiece(Position.of("a1"), PieceFactory.createBlack(Type.BISHOP));

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
                        chessGame.movePiece(validPosition, invalidPosition));
                assertThatExceptionOfType(OutOfBoardException.class).isThrownBy(() ->
                        chessGame.movePiece(invalidPosition, validPosition));
            }

            @Test
            @DisplayName("기물이 존재하지 않으면 예외가 발생한다")
            void verifyNoneTypePiece() {
                String sourcePosition = "d4";   // None
                String targetPosition = "d5";
                Piece sourcePiece = board.findPiece(Position.of(sourcePosition));

                assertThat(sourcePiece).isEqualTo(PieceFactory.createBlank());
                assertThatExceptionOfType(NoneTypePieceException.class).isThrownBy(() ->
                        chessGame.movePiece(sourcePosition, targetPosition));
            }

            @Test
            @DisplayName("다른 팀의 기물을 움직이려고 하면 예외가 발생한다")
            void verifyUnavailablePiece() {
                String sourcePosition = "a2";   // White Pawn
                String targetPosition = "a3";
                Piece sourcePiece = board.findPiece(Position.of(sourcePosition));

                assertThat(sourcePiece).isEqualTo(PieceFactory.createWhite(Type.PAWN));
                assertThatExceptionOfType(UnavailablePieceException.class).isThrownBy(() ->
                        chessGame.movePiece(sourcePosition, targetPosition));
            }

            @Test
            @DisplayName("현재와 동일한 위치로 이동하려 하면 예외가 발생한다")
            void verifySamePosition() {
                String sourcePosition = "a7";   // Black Pawn

                assertThatExceptionOfType(InvalidMovementException.class).isThrownBy(() ->
                        chessGame.movePiece(sourcePosition, sourcePosition));
            }

            @Test
            @DisplayName("목적지에 같은 편 기물이 있으면 예외가 발생한다")
            void verifySameColor() {
                String sourcePosition = "a8";   // Black Rook
                String targetPosition = "a7";   // Black Pawn
                Piece sourcePiece = board.findPiece(Position.of(sourcePosition));
                Piece targetPiece = board.findPiece(Position.of(targetPosition));

                assertThat(sourcePiece).isEqualTo(PieceFactory.createBlack(Type.ROOK));
                assertThat(targetPiece).isEqualTo(PieceFactory.createBlack(Type.PAWN));
                assertThatExceptionOfType(InvalidMovementException.class).isThrownBy(() ->
                        chessGame.movePiece(sourcePosition, targetPosition));
            }

            @Test
            @DisplayName("선택한 기물이 이동할 수 없는 방향으로 움직이면 예외가 발생한다")
            void verifyDirection() {
                String sourcePosition = "d7";   // Black Pawn
                String targetPosition = "e5";
                Piece sourcePiece = board.findPiece(Position.of(sourcePosition));

                assertThat(sourcePiece).isEqualTo(PieceFactory.createBlack(Type.PAWN));
                assertThatExceptionOfType(InvalidDirectionException.class)
                        .isThrownBy(() -> chessGame.movePiece(sourcePosition, targetPosition));
            }

            @Test
            @DisplayName("이동하는 경로에 다른 기물이 존재하면 예외가 발생한다")
            void verifyRoute() {
                String sourcePosition = "d8";
                String obstaclePosition = "d7";
                String targetPosition = "d4";
                Piece sourcePiece = board.findPiece(Position.of(sourcePosition));
                Piece obstacle = board.findPiece(Position.of(obstaclePosition));

                assertThat(sourcePiece).isEqualTo(PieceFactory.createBlack(Type.QUEEN));
                assertThat(obstacle).isEqualTo(PieceFactory.createBlack(Type.PAWN));
                assertThatExceptionOfType(InvalidMovementException.class).isThrownBy(() ->
                        chessGame.movePiece(sourcePosition, targetPosition));
            }

        }

    }

}
