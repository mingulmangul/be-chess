package softeer2nd.chess.pieces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static softeer2nd.chess.pieces.Piece.*;

public class PieceTest {

    @Test
    @DisplayName("기물 구현")
    void create_pieces() {
        // 폰
        verifyPiece(createBlackPawn(), Type.PAWN.getBlackRepresentation());
        verifyPiece(createWhitePawn(), Type.PAWN.getWhiteRepresentation());

        // 룩
        verifyPiece(createBlackRook(), Type.ROOK.getBlackRepresentation());
        verifyPiece(createWhiteRook(), Type.ROOK.getWhiteRepresentation());

        // 나이트
        verifyPiece(createBlackKnight(), Type.KNIGHT.getBlackRepresentation());
        verifyPiece(createWhiteKnight(), Type.KNIGHT.getWhiteRepresentation());

        // 비숍
        verifyPiece(createBlackBishop(), Type.BISHOP.getBlackRepresentation());
        verifyPiece(createWhiteBishop(), Type.BISHOP.getWhiteRepresentation());

        // 퀸
        verifyPiece(createBlackQueen(), Type.QUEEN.getBlackRepresentation());
        verifyPiece(createWhiteQueen(), Type.QUEEN.getWhiteRepresentation());

        // 킹
        verifyPiece(createBlackKing(), Type.KING.getBlackRepresentation());
        verifyPiece(createWhiteKing(), Type.KING.getWhiteRepresentation());
    }

    private void verifyPiece(Piece piece, char representation) {
        if (piece.isWhite()) {
            assertFalse(piece.isBlack());
            assertEquals(Color.WHITE, piece.getColor());
        }
        if (piece.isBlack()) {
            assertFalse(piece.isWhite());
            assertEquals(Color.BLACK, piece.getColor());
        }
        assertEquals(String.valueOf(representation), piece.toString());
    }


}
