package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Piece;
import softeer2nd.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static softeer2nd.chess.pieces.Piece.*;

/**
 * 일급 컬렉션으로 구현
 */
public class Rank {

    private final List<Piece> pieceList = new ArrayList<>();

    private Rank(int size) {
        for (int fileIndex = 0; fileIndex < size; fileIndex++) {
            pieceList.add(createBlank());
        }
    }

    // 비어있는 랭크를 생성한다
    public static Rank createEmptyRank() {
        return new Rank(Board.SIZE);
    }

    // 게임 초기 진영의 1번째 줄을 초기화한 (룩 나이트 비숍 퀸 킹 비숍 나이트 룩)
    public void initFirstRank(Color color) {
        pieceList.clear();
        if (color.equals(Color.BLACK)) {
            pieceList.add(createBlackRook());
            pieceList.add(createBlackKnight());
            pieceList.add(createBlackBishop());
            pieceList.add(createBlackQueen());
            pieceList.add(createBlackKing());
            pieceList.add(createBlackBishop());
            pieceList.add(createBlackKnight());
            pieceList.add(createBlackRook());
        } else {
            pieceList.add(createWhiteRook());
            pieceList.add(createWhiteKnight());
            pieceList.add(createWhiteBishop());
            pieceList.add(createWhiteQueen());
            pieceList.add(createWhiteKing());
            pieceList.add(createWhiteBishop());
            pieceList.add(createWhiteKnight());
            pieceList.add(createWhiteRook());
        }
    }

    // 게임 초기 진영의 2번째 줄을 초기화한다 (폰 8개)
    public void initSecondRank(Color color) {
        pieceList.clear();
        if (color.equals(Color.BLACK)) {
            pieceList.add(createBlackPawn());
            pieceList.add(createBlackPawn());
            pieceList.add(createBlackPawn());
            pieceList.add(createBlackPawn());
            pieceList.add(createBlackPawn());
            pieceList.add(createBlackPawn());
            pieceList.add(createBlackPawn());
            pieceList.add(createBlackPawn());
        } else {
            pieceList.add(createWhitePawn());
            pieceList.add(createWhitePawn());
            pieceList.add(createWhitePawn());
            pieceList.add(createWhitePawn());
            pieceList.add(createWhitePawn());
            pieceList.add(createWhitePawn());
            pieceList.add(createWhitePawn());
            pieceList.add(createWhitePawn());
        }
    }

    // 특정 위치의 기물을 반환한다
    public Piece getPieceAt(int fileIndex) {
        return pieceList.get(fileIndex);
    }

    // 특정 위치에 기물을 추가한다
    public void setPiece(int fileIndex, Piece piece) {
        pieceList.set(fileIndex, piece);
    }

    // 특정 위치의 기물을 제거한다
    public void removePiece(int fileIndex) {
        pieceList.set(fileIndex, createBlank());
    }

    // 기물의 개수를 반환한다
    public int countPieces() {
        int count = 0;
        for (Piece piece : pieceList) {
            if (!piece.getType().equals(Type.NONE)) {
                count++;
            }
        }
        return count;
    }

    // 색상과 종류에 따른 기물의 개수를 계산한다
    public int countPiecesWithColorAndType(Color color, Type type) {
        return (int) pieceList.stream()
                .filter(piece -> piece.getColor().equals(color) && piece.getType().equals(type))
                .count();
    }

    // 색상에 따른 기물 리스트를 반환한다.
    public List<Piece> getPiecesWithColor(Color color) {
        return pieceList.stream()
                .filter(piece -> piece.getColor().equals(color))
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
