package softeer2nd.chess.pieces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PieceTest {

//    void verifyPawn(final String color, final char representation) {
//        Piece pawn = new Piece(color);
//        assertThat(pawn.getColor()).isEqualTo(color);
//        assertThat(pawn.getRepresentation()).isEqualTo(representation);
//    }
//
//    @Test
//    @DisplayName("흰색과 검정색 폰이 생성되어야 한다")
//    void create() {
//        verifyPawn(Piece.WHITE_COLOR, Piece.WHITE_REPRESENTATION);
//        verifyPawn(Piece.BLACK_COLOR, Piece.BLACK_REPRESENTATION);
//    }
//
//    @Test
//    @DisplayName("기본 생성자로 흰색 폰이 생성되어야 한다")
//    void create_기본생성자() throws Exception {
//        Piece pawn = new Piece();
//        assertEquals(Piece.WHITE_COLOR, pawn.getColor());
//        assertEquals(Piece.WHITE_REPRESENTATION, pawn.getRepresentation());
//    }

    @Test
    @DisplayName("기물 구현")
    void create_pieces() {
        // 폰
        verifyPiece(Piece.createPawn(Piece.BLACK_COLOR), 'P');
        verifyPiece(Piece.createPawn(Piece.WHITE_COLOR), 'p');

        // 룩
        verifyPiece(Piece.createRook(Piece.BLACK_COLOR), 'R');
        verifyPiece(Piece.createRook(Piece.WHITE_COLOR), 'r');

        // 나이트
        verifyPiece(Piece.createKnight(Piece.BLACK_COLOR), 'N');
        verifyPiece(Piece.createKnight(Piece.WHITE_COLOR), 'n');

        // 비숍
        verifyPiece(Piece.createBishop(Piece.BLACK_COLOR), 'B');
        verifyPiece(Piece.createBishop(Piece.WHITE_COLOR), 'b');

        // 퀸
        verifyPiece(Piece.createQueen(Piece.BLACK_COLOR), 'Q');
        verifyPiece(Piece.createQueen(Piece.WHITE_COLOR), 'q');

        // 킹
        verifyPiece(Piece.createKing(Piece.BLACK_COLOR), 'K');
        verifyPiece(Piece.createKing(Piece.WHITE_COLOR), 'k');
    }

    private void verifyPiece(Piece piece, char representation) {
        if (piece.isWhite()) {
            assertFalse(piece.isBlack());
            assertEquals(Piece.WHITE_COLOR, piece.getColor());
        }
        if (piece.isBlack()) {
            assertFalse(piece.isWhite());
            assertEquals(Piece.BLACK_COLOR, piece.getColor());
        }
        assertEquals(String.valueOf(representation), piece.toString());
    }


}
