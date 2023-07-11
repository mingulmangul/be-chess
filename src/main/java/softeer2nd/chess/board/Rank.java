package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Piece;
import softeer2nd.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static softeer2nd.chess.board.Board.SIZE;
import static softeer2nd.chess.pieces.Piece.*;

/**
 * 일급 컬렉션으로 구현
 */
public class Rank {

    private final List<Piece> pieceList = new ArrayList<>();

    private Rank() {
        for (int x = 0; x < SIZE; x++) {
            pieceList.add(createBlank());
        }
    }

    // 비어있는 랭크를 생성한다
    public static Rank createRank() {
        return new Rank();
    }

    // 게임 초기 진영의 1번째 줄을 초기화한다 (룩 나이트 비숍 퀸 킹 비숍 나이트 룩)
    public void initFirstRank(Color color) {
        if (color == Color.BLACK) {
            setPiece(0, createBlackRook());
            setPiece(1, createBlackKnight());
            setPiece(2, createBlackBishop());
            setPiece(3, createBlackQueen());
            setPiece(4, createBlackKing());
            setPiece(5, createBlackBishop());
            setPiece(6, createBlackKnight());
            setPiece(7, createBlackRook());
        } else {
            setPiece(0, createWhiteRook());
            setPiece(1, createWhiteKnight());
            setPiece(2, createWhiteBishop());
            setPiece(3, createWhiteQueen());
            setPiece(4, createWhiteKing());
            setPiece(5, createWhiteBishop());
            setPiece(6, createWhiteKnight());
            setPiece(7, createWhiteRook());
        }
    }

    // 게임 초기 진영의 2번째 줄을 초기화한다 (폰 8개)
    public void initSecondRank(Color color) {
        if (color == Color.BLACK) {
            for (int x = 0; x < SIZE; x++) {
                setPiece(x, createBlackPawn());
            }
        } else {
            for (int x = 0; x < SIZE; x++) {
                setPiece(x, createWhitePawn());
            }
        }
    }

    // 특정 위치의 기물을 반환한다
    public Piece getPieceAt(int x) {
        return pieceList.get(x);
    }

    // 특정 위치에 기물을 추가한다
    public void setPiece(int x, Piece piece) {
        pieceList.set(x, piece);
    }

    // 특정 위치의 기물을 제거한다
    public Piece removePiece(int x) {
        Piece removedPiece = getPieceAt(x);
        pieceList.set(x, createBlank());
        return removedPiece;
    }

    // 기물의 개수를 반환한다
    public int countPiecesInRank() {
        return (int) pieceList.stream()
                              .filter(piece -> piece.getType() != Type.NONE)
                              .count();
    }

    // 색상과 종류에 따른 기물의 개수를 계산한다
    public int countPiecesInRank(Color color, Type type) {
        return (int) pieceList.stream()
                              .filter(piece -> piece.getColor() == color && piece.getType() == type)
                              .count();
    }

    // 색상에 따른 기물 리스트를 반환한다.
    public List<Piece> getPieceList(Color color) {
        return pieceList.stream()
                        .filter(piece -> piece.getColor() == color)
                        .collect(Collectors.toList());
    }

    // 랭크를 출력한다
    public String showRank() {
        StringBuilder sb = new StringBuilder();
        for (Piece piece : pieceList) {
            sb.append(piece.getRepresentation());
        }
        return StringUtils.appendNewLine(sb.toString());
    }
}
