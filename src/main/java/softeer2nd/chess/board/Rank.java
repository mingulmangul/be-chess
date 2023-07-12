package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Piece;
import softeer2nd.chess.pieces.PieceFactory;
import softeer2nd.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static softeer2nd.chess.board.Board.SIZE;
import static softeer2nd.chess.pieces.Piece.Color;
import static softeer2nd.chess.pieces.Piece.Type;

/**
 * 일급 컬렉션으로 구현
 */
public class Rank {

    private final List<Piece> pieceList = new ArrayList<>();

    private Rank() {
        for (int x = 0; x < SIZE; x++) {
            pieceList.add(PieceFactory.createBlank());
        }
    }

    // 비어있는 랭크를 생성한다
    public static Rank createRank() {
        return new Rank();
    }

    // 게임 초기 진영의 1번째 줄을 초기화한다 (룩 나이트 비숍 퀸 킹 비숍 나이트 룩)
    public void initFirstRank(Color color) {
        if (color == Color.BLACK) {
            setPiece(0, PieceFactory.createBlack(Type.ROOK));
            setPiece(1, PieceFactory.createBlack(Type.KNIGHT));
            setPiece(2, PieceFactory.createBlack(Type.BISHOP));
            setPiece(3, PieceFactory.createBlack(Type.QUEEN));
            setPiece(4, PieceFactory.createBlack(Type.KING));
            setPiece(5, PieceFactory.createBlack(Type.BISHOP));
            setPiece(6, PieceFactory.createBlack(Type.KNIGHT));
            setPiece(7, PieceFactory.createBlack(Type.ROOK));
        } else {
            setPiece(0, PieceFactory.createWhite(Type.ROOK));
            setPiece(1, PieceFactory.createWhite(Type.KNIGHT));
            setPiece(2, PieceFactory.createWhite(Type.BISHOP));
            setPiece(3, PieceFactory.createWhite(Type.QUEEN));
            setPiece(4, PieceFactory.createWhite(Type.KING));
            setPiece(5, PieceFactory.createWhite(Type.BISHOP));
            setPiece(6, PieceFactory.createWhite(Type.KNIGHT));
            setPiece(7, PieceFactory.createWhite(Type.ROOK));
        }
    }

    // 게임 초기 진영의 2번째 줄을 초기화한다 (폰 8개)
    public void initSecondRank(Color color) {
        if (color == Color.BLACK) {
            for (int x = 0; x < SIZE; x++) {
                setPiece(x, PieceFactory.createBlack(Type.PAWN));
            }
        } else {
            for (int x = 0; x < SIZE; x++) {
                setPiece(x, PieceFactory.createWhite(Type.PAWN));
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
        pieceList.set(x, PieceFactory.createBlank());
        return removedPiece;
    }

    // 기물의 개수를 반환한다
    public int countPiecesInRank() {
        return (int) pieceList.stream()
                              .filter(piece -> !piece.isType(Type.NONE))
                              .count();
    }

    // 색상과 종류에 따른 기물의 개수를 계산한다
    public int countPiecesInRank(Color color, Type type) {
        return (int) pieceList.stream()
                              .filter(piece -> piece.isColor(color) && piece.isType(type))
                              .count();
    }

    // 색상에 따른 기물 리스트를 반환한다.
    public List<Piece> getPieceList(Color color) {
        return pieceList.stream()
                        .filter(piece -> piece.isColor(color))
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
