package softeer2nd.chess.pieces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static softeer2nd.chess.pieces.Piece.*;

class PieceTest {

    @Test
    @DisplayName("기물 구현")
    void create_pieces() {
        // 폰
        verifyPiece(createBlackPawn(), Color.BLACK, Type.PAWN.getBlackRepresentation());
        verifyPiece(createWhitePawn(), Color.WHITE, Type.PAWN.getWhiteRepresentation());

        // 룩
        verifyPiece(createBlackRook(), Color.BLACK, Type.ROOK.getBlackRepresentation());
        verifyPiece(createWhiteRook(), Color.WHITE, Type.ROOK.getWhiteRepresentation());

        // 나이트
        verifyPiece(createBlackKnight(), Color.BLACK, Type.KNIGHT.getBlackRepresentation());
        verifyPiece(createWhiteKnight(), Color.WHITE, Type.KNIGHT.getWhiteRepresentation());

        // 비숍
        verifyPiece(createBlackBishop(), Color.BLACK, Type.BISHOP.getBlackRepresentation());
        verifyPiece(createWhiteBishop(), Color.WHITE, Type.BISHOP.getWhiteRepresentation());

        // 퀸
        verifyPiece(createBlackQueen(), Color.BLACK, Type.QUEEN.getBlackRepresentation());
        verifyPiece(createWhiteQueen(), Color.WHITE, Type.QUEEN.getWhiteRepresentation());

        // 킹
        verifyPiece(createBlackKing(), Color.BLACK, Type.KING.getBlackRepresentation());
        verifyPiece(createWhiteKing(), Color.WHITE, Type.KING.getWhiteRepresentation());
    }

    private void verifyPiece(Piece piece, Color color, char representation) {
        assertTrue(piece.isColor(color));
        assertEquals(representation, piece.getRepresentation());
    }

}
