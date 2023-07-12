package softeer2nd.chess.pieces;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static softeer2nd.chess.pieces.Piece.Color;
import static softeer2nd.chess.pieces.Piece.Type;

class PieceTest {

    @Test
    @DisplayName("기물 구현")
    void create_pieces() {
        // 폰
        verifyPiece(PieceFactory.createBlack(Type.PAWN), Color.BLACK, Type.PAWN.getBlackRepresentation());
        verifyPiece(PieceFactory.createWhite(Type.PAWN), Color.WHITE, Type.PAWN.getWhiteRepresentation());

        // 룩
        verifyPiece(PieceFactory.createBlack(Type.ROOK), Color.BLACK, Type.ROOK.getBlackRepresentation());
        verifyPiece(PieceFactory.createWhite(Type.ROOK), Color.WHITE, Type.ROOK.getWhiteRepresentation());

        // 나이트
        verifyPiece(PieceFactory.createBlack(Type.KNIGHT), Color.BLACK, Type.KNIGHT.getBlackRepresentation());
        verifyPiece(PieceFactory.createWhite(Type.KNIGHT), Color.WHITE, Type.KNIGHT.getWhiteRepresentation());

        // 비숍
        verifyPiece(PieceFactory.createBlack(Type.BISHOP), Color.BLACK, Type.BISHOP.getBlackRepresentation());
        verifyPiece(PieceFactory.createWhite(Type.BISHOP), Color.WHITE, Type.BISHOP.getWhiteRepresentation());

        // 퀸
        verifyPiece(PieceFactory.createBlack(Type.QUEEN), Color.BLACK, Type.QUEEN.getBlackRepresentation());
        verifyPiece(PieceFactory.createWhite(Type.QUEEN), Color.WHITE, Type.QUEEN.getWhiteRepresentation());

        // 킹
        verifyPiece(PieceFactory.createBlack(Type.KING), Color.BLACK, Type.KING.getBlackRepresentation());
        verifyPiece(PieceFactory.createWhite(Type.KING), Color.WHITE, Type.KING.getWhiteRepresentation());
    }

    private void verifyPiece(Piece piece, Color color, char representation) {
        assertTrue(piece.isColor(color));
        assertEquals(representation, piece.getRepresentation());
    }

}
