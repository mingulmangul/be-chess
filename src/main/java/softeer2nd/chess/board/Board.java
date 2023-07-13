package softeer2nd.chess.board;

import softeer2nd.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static softeer2nd.chess.pieces.Piece.Color;
import static softeer2nd.chess.pieces.Piece.Type;

public class Board {

    public static final int SIZE = 8;

    private static final int FIRST_RANK_OF_WHITE = 0;
    private static final int SECOND_RANK_OF_WHITE = 1;
    private static final int FIRST_RANK_OF_BLACK = SIZE - 1;
    private static final int SECOND_RANK_OF_BLACK = SIZE - 2;

    private final List<Rank> ranks = new ArrayList<>();

    public Board() {
        initializeEmpty();  // 빈 체스판 생성
    }

    public List<Rank> getRanks() {
        return ranks;
    }

    // 체스판을 비어있는 상태로 초기화한다
    public void initializeEmpty() {
        ranks.clear();
        for (int y = 0; y < Board.SIZE; y++) {
            ranks.add(Rank.createRank());
        }
    }

    // 체스판을 게임 시작 상태로 초기화한다
    public void initialize() {
        initializeEmpty();
        ranks.get(FIRST_RANK_OF_WHITE).initFirstRank(Color.WHITE);
        ranks.get(SECOND_RANK_OF_WHITE).initSecondRank(Color.WHITE);
        ranks.get(SECOND_RANK_OF_BLACK).initSecondRank(Color.BLACK);
        ranks.get(FIRST_RANK_OF_BLACK).initFirstRank(Color.BLACK);
    }

    // 체스판 위 전체 기물의 개수를 계산한다
    public int countPieces() {
        return ranks.stream()
                    .mapToInt(Rank::countPiecesInRank)
                    .sum();
    }

    // 특정 색상과 종류를 가진 기물의 개수를 계산한다
    public int countPieces(Color color, Type type) {
        return ranks.stream()
                    .mapToInt(rank -> rank.countPiecesInRank(color, type))
                    .sum();
    }

    // 특정 위치의 기물을 조회한다
    public Piece findPiece(Position position) {
        return ranks.get(position.getY())
                    .getPiece(position.getX());
    }

    // 체스판 위 특정 색상의 모든 기물을 찾아 반환한다
    private List<Piece> findAllPieces(Color color) {
        List<Piece> pieces = new ArrayList<>();
        for (Rank rank : ranks) {
            List<Piece> rankPieceList = rank.getPieceList(color);
            if (rankPieceList.isEmpty()) {
                continue;
            }
            pieces.addAll(rankPieceList);
        }
        return pieces;
    }

    // 체스판 위 특정 색상의 기물들을 점수를 기준으로 내림차순 정렬한다
    public List<Piece> findAllPiecesInDescOrder(Color color) {
        List<Piece> pieces = findAllPieces(color);
        Collections.sort(pieces);
        return pieces;
    }

    // 체스판 위 특정 색상의 기물들을 점수를 기준으로 오름차순 정렬한다
    public List<Piece> findAllPiecesInAscOrder(Color color) {
        List<Piece> pieces = findAllPieces(color);
        pieces.sort(Comparator.reverseOrder());
        return pieces;
    }

    // 특정 위치의 기물을 다른 기물로 바꾼다
    public Piece replacePiece(Position position, Piece piece) {
        Rank rank = ranks.get(position.getY());
        Piece replacedPiece = rank.getPiece(position.getX());
        rank.setPiece(position.getX(), piece);
        return replacedPiece;
    }

    // 특정 위치의 기물을 제거한다 (제거한 기물 반환)
    public Piece removePiece(Position position) {
        return ranks.get(position.getY())
                    .removePiece(position.getX());
    }

    public String showRankAt(int x) {
        return ranks.get(x).showRank();
    }

}
