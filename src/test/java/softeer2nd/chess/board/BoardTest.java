package softeer2nd.chess.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.chess.pieces.PieceFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static softeer2nd.chess.pieces.Piece.Color;
import static softeer2nd.chess.pieces.Piece.Type;
import static softeer2nd.chess.pieces.PieceFactory.createBlack;
import static softeer2nd.chess.pieces.PieceFactory.createWhite;

class BoardTest {

    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board();
        board.initialize();
    }

    @Test
    @DisplayName("기물의 개수를 계산한다")
    void countPiecesWithColorAndType() {
        int countAllPieces = board.countPieces();
        int countBlackPawn = board.countPieces(Color.BLACK, Type.PAWN);
        int countWhiteKnight = board.countPieces(Color.WHITE, Type.KNIGHT);
        int countWhiteKing = board.countPieces(Color.WHITE, Type.KING);

        int numberOfAllPieces = 32;
        int numberOfBlackPawn = 8;
        int numberOfWhiteKnight = 2;
        int numberOfWhiteKing = 1;

        assertThat(countAllPieces).isEqualTo(numberOfAllPieces);
        assertThat(countBlackPawn).isEqualTo(numberOfBlackPawn);
        assertThat(countWhiteKnight).isEqualTo(numberOfWhiteKnight);
        assertThat(countWhiteKing).isEqualTo(numberOfWhiteKing);
    }

    private void verify(String position, Piece expected) {
        Piece piece = board.findPiece(Position.of(position));
        assertThat(piece).isEqualTo(expected);
    }

    @Test
    @DisplayName("특정 위치의 기물을 조회한다")
    void findPiece() {
        verify("a8", createBlack(Type.ROOK));
        verify("b8", createBlack(Type.KNIGHT));
        verify("c8", createBlack(Type.BISHOP));
        verify("d8", createBlack(Type.QUEEN));
        verify("e8", createBlack(Type.KING));
        verify("d7", createBlack(Type.PAWN));

        verify("a1", createWhite(Type.ROOK));
        verify("d2", createWhite(Type.PAWN));

        verify("f5", PieceFactory.createBlank());
    }

    @Test
    @DisplayName("특정 위치에 기물을 추가한다")
    void addPiece() {
        board.initializeEmpty();
        String position = "f5";
        Piece newPiece = createBlack(Type.ROOK);
        verify(position, PieceFactory.createBlank());

        board.setPiece(Position.of(position), newPiece);

        verify(position, newPiece);
    }

    @Test
    @DisplayName("체스판 위 특정 색상의 기물을 점수 순으로 정렬한다")
    void sortAllPieces() {
        board.initializeEmpty();
        List<Piece> black = new ArrayList<>();
        List<Piece> white = new ArrayList<>();

        board.setPiece(Position.of("e6"), createBlack(Type.QUEEN));
        black.add(createBlack(Type.QUEEN));
        board.setPiece(Position.of("c8"), createBlack(Type.ROOK));
        black.add(createBlack(Type.ROOK));
        board.setPiece(Position.of("b6"), createBlack(Type.PAWN));
        black.add(createBlack(Type.PAWN));
        board.setPiece(Position.of("b8"), createBlack(Type.KING));
        black.add(createBlack(Type.KING));

        board.setPiece(Position.of("f1"), createWhite(Type.KING));
        white.add(createWhite(Type.KING));
        board.setPiece(Position.of("f2"), createWhite(Type.PAWN));
        white.add(createWhite(Type.PAWN));
        board.setPiece(Position.of("f3"), createWhite(Type.PAWN));
        white.add(createWhite(Type.PAWN));
        board.setPiece(Position.of("e1"), createWhite(Type.ROOK));
        white.add(createWhite(Type.ROOK));

        List<Piece> sortedBlack = board.findAllPiecesInDescOrder(Color.BLACK);
        List<Piece> sortedWhite = board.findAllPiecesInAscOrder(Color.WHITE);

        for (int i = 0; i < black.size(); i++) {
            assertThat(black.get(i)).isEqualTo(sortedBlack.get(i));
        }
        for (int i = 0; i < white.size(); i++) {
            assertThat(white.get(i)).isEqualTo(sortedWhite.get(i));
        }
    }

}
