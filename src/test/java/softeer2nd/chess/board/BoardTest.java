package softeer2nd.chess.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static softeer2nd.chess.pieces.Piece.*;
import static softeer2nd.utils.StringUtils.appendNewLine;

class BoardTest {

    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board();
        board.initialize();
    }

    @Test
    @DisplayName("보드를 초기화한다")
    void create() {
        assertEquals(32, board.countPieces());
        String blankRank = appendNewLine("........");
        assertEquals(
                appendNewLine("RNBQKBNR") +
                        appendNewLine("PPPPPPPP") +
                        blankRank + blankRank + blankRank + blankRank +
                        appendNewLine("pppppppp") +
                        appendNewLine("rnbqkbnr"),
                board.showBoard()
        );
    }

    @Test
    @DisplayName("특정 기물과 색에 해당하는 기물의 개수를 계산한다")
    void countPiecesWithColorAndType() {
        int countBlackPawn = board.countPieces(Color.BLACK, Type.PAWN);
        int countWhiteKnight = board.countPieces(Color.WHITE, Type.KNIGHT);
        int countWhiteKing = board.countPieces(Color.WHITE, Type.KING);

        assertThat(countBlackPawn).isEqualTo(8);
        assertThat(countWhiteKnight).isEqualTo(2);
        assertThat(countWhiteKing).isEqualTo(1);
    }

    private void verify(String position, Piece expected) {
        Piece piece = board.findPiece(position);
        assertThat(piece).isEqualTo(expected);
    }

    @Test
    @DisplayName("특정 위치의 기물을 조회한다")
    void findPiece() {
        verify("a8", createBlackRook());
        verify("b8", createBlackKnight());
        verify("c8", createBlackBishop());
        verify("d8", createBlackQueen());
        verify("e8", createBlackKing());
        verify("f8", createBlackBishop());
        verify("g8", createBlackKnight());
        verify("h8", createBlackRook());
        verify("d7", createBlackPawn());

        verify("a1", createWhiteRook());
        verify("d2", createWhitePawn());

        verify("f5", createBlank());
    }

    @Test
    @DisplayName("특정 위치에 기물을 추가한다")
    void addPiece() {
        board.initializeEmpty();
        String position = "f5";
        Piece newPiece = createBlackRook();
        verify(position, createBlank());

        board.addPiece(position, newPiece);

        verify(position, newPiece);
        System.out.println(board.showBoard());
    }

    @Test
    @DisplayName("체스판의 점수를 계산한다")
    void calcScore() {
        board.initializeEmpty();
        board.addPiece("b6", Piece.createBlackPawn());  // 1
        board.addPiece("e6", Piece.createBlackQueen()); // 9
        board.addPiece("b8", Piece.createBlackKing());  // 0
        board.addPiece("c8", Piece.createBlackRook());  // 5
        board.addPiece("f2", Piece.createWhitePawn());  // 0.5
        board.addPiece("f3", Piece.createWhitePawn());  // 0.5
        board.addPiece("e1", Piece.createWhiteRook());  // 5
        board.addPiece("f1", Piece.createWhiteKing());  // 0
        System.out.println(board.showBoard());

        double blackScore = board.calculatePoint(Color.BLACK);
        double whiteScore = board.calculatePoint(Color.WHITE);

        assertThat(blackScore).isEqualTo(15.0d);
        assertThat(whiteScore).isEqualTo(6.0d);
    }

    @Test
    @DisplayName("체스판 위 특정 색상의 기물을 점수 순으로 정렬한다")
    void sortAllPieces() {
        board.initializeEmpty();
        List<Piece> black = new ArrayList<>();
        List<Piece> white = new ArrayList<>();

        board.addPiece("e6", Piece.createBlackQueen());
        black.add(createBlackQueen());
        board.addPiece("c8", Piece.createBlackRook());
        black.add(createBlackRook());
        board.addPiece("b6", Piece.createBlackPawn());
        black.add(createBlackPawn());
        board.addPiece("b8", Piece.createBlackKing());
        black.add(createBlackKing());

        board.addPiece("f1", Piece.createWhiteKing());
        white.add(createWhiteKing());
        board.addPiece("f2", Piece.createWhitePawn());
        white.add(createWhitePawn());
        board.addPiece("f3", Piece.createWhitePawn());
        white.add(createWhitePawn());
        board.addPiece("e1", Piece.createWhiteRook());
        white.add(createWhiteRook());

        System.out.println(board.showBoard());

        List<Piece> sortedBlack = board.findAllPiecesInDescOrder(Color.BLACK);
        List<Piece> sortedWhite = board.findAllPiecesInAscOrder(Color.WHITE);

        for (int i = 0; i < black.size(); i++) {
            assertThat(black.get(i)).isEqualTo(sortedBlack.get(i));
        }
        for (int i = 0; i < white.size(); i++) {
            assertThat(white.get(i)).isEqualTo(sortedWhite.get(i));
        }
    }

    @Test
    @DisplayName("기물을 다른 위치로 이동한다")
    void move() {
        board.initialize();

        String sourcePosition = "b2";
        String targetPosition = "b3";
        board.move(sourcePosition, targetPosition);
        assertThat(Piece.createBlank())
                .isEqualTo(board.findPiece(sourcePosition));
        assertThat(Piece.createWhitePawn())
                .isEqualTo(board.findPiece(targetPosition));
    }
}
