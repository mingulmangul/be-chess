package softeer2nd.chess.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import softeer2nd.chess.pieces.Piece;
import softeer2nd.chess.pieces.PieceFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static softeer2nd.chess.pieces.Piece.Color;
import static softeer2nd.chess.pieces.Piece.Type;

@DisplayName("랭크(체스판의 한 행)")
class RankTest {

    Rank rank;

    @BeforeEach
    void setUp() {
        rank = Rank.createRank();
    }

    @Test
    @DisplayName("특정 위치에 기물을 추가한다")
    void add() {
        Piece blackPawn = PieceFactory.createBlack(Type.PAWN);
        int fileIdx = 0;

        rank.setPiece(fileIdx, blackPawn);

        Piece addedPawn = rank.getPiece(fileIdx);
        assertThat(addedPawn).isSameAs(blackPawn);
    }

    @Test
    @DisplayName("특정 위치의 기물을 삭제한다")
    void remove() {
        int fileIdx = 0;
        rank.setPiece(fileIdx, PieceFactory.createBlack(Type.PAWN));

        rank.removePiece(fileIdx);

        Piece blankPiece = PieceFactory.createBlank();
        assertThat(rank.getPiece(fileIdx)).isEqualTo(blankPiece);
    }

    @Test
    @DisplayName("특정 색상과 종류를 가진 기물의 개수를 구한다")
    void count() {
        rank.setPiece(0, PieceFactory.createBlack(Type.PAWN));
        rank.setPiece(1, PieceFactory.createBlack(Type.PAWN));
        rank.setPiece(5, PieceFactory.createBlack(Type.PAWN));
        rank.setPiece(4, PieceFactory.createWhite(Type.BISHOP));
        rank.setPiece(7, PieceFactory.createWhite(Type.BISHOP));

        int countPawn = rank.countPiecesInRank(Color.BLACK, Type.PAWN);
        int countRook = rank.countPiecesInRank(Color.WHITE, Type.BISHOP);

        assertThat(countPawn).isEqualTo(3);
        assertThat(countRook).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 색상의 기물 리스트를 반환한다")
    void getPiecesWithColor() {
        rank.setPiece(6, PieceFactory.createBlack(Type.PAWN));
        rank.setPiece(1, PieceFactory.createBlack(Type.PAWN));
        rank.setPiece(5, PieceFactory.createBlack(Type.PAWN));
        rank.setPiece(4, PieceFactory.createWhite(Type.BISHOP));
        rank.setPiece(7, PieceFactory.createWhite(Type.BISHOP));

        List<Piece> blackPieces = rank.getPieceList(Color.BLACK);
        List<Piece> whitePieces = rank.getPieceList(Color.WHITE);

        assertThat(blackPieces).hasSize(3);
        assertThat(whitePieces).hasSize(2);
    }
}
