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

    private final List<Rank> ranks = new ArrayList<>();

    public Board() {
        initializeEmpty();  // 빈 체스판 생성
    }

    // 체스판을 비어있는 상태로 초기화한다
    public void initializeEmpty() {
        ranks.clear();
        for (int rankIndex = 0; rankIndex < SIZE; rankIndex++) {
            ranks.add(Rank.createEmptyRank());
        }
    }

    // 체스판을 게임 시작 상태로 초기화한다
    public void initialize() {
        initializeEmpty();
        ranks.get(0)
             .initFirstRank(Color.WHITE);
        ranks.get(1)
             .initSecondRank(Color.WHITE);
        ranks.get(SIZE - 1)
             .initFirstRank(Color.BLACK);
        ranks.get(SIZE - 2)
             .initSecondRank(Color.BLACK);
    }

    // 체스판을 출력한다
    public String showBoard() {
        StringBuilder sb = new StringBuilder();
        for (int rankIndex = SIZE - 1; rankIndex >= 0; rankIndex--) {
            sb.append(ranks.get(rankIndex)
                           .showRank());
        }
        return sb.toString();

    }

    // 체스판 위 전체 기물의 개수를 계산한다
    public int countPieces() {
        int count = 0;
        for (Rank rank : ranks) {
            count += rank.countPieces();
        }
        return count;
    }

    /**
     * 특정 색상과 종류를 가진 기물의 개수를 계산한다
     *
     * @param color 기물의 색상
     * @param type  기물의 종류
     * @return 기물의 개수
     */
    public int countPieces(Color color, Type type) {
        int count = 0;
        for (Rank rank : ranks) {
            count += rank.countPiecesInRank(color, type);
        }
        return count;
    }

    /**
     * 특정 위치의 기물을 조회한다
     *
     * @param position 위치
     * @return 찾아 낸 기물
     */
    public Piece findPiece(String position) {
        Position pos = Position.of(position);
        return ranks.get(pos.getRankIndex())
                    .getPieceAt(pos.getFileIndex());
    }

    /**
     * 체스판 위 특정 색상의 모든 기물을 찾아 반환한다
     *
     * @param color 색상
     * @return 기물 리스트
     */
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

    /**
     * 체스판 위 특정 색상의 기물들을 점수를 기준으로 내림차순 정렬한다
     *
     * @param color 색상
     * @return 정렬된 기물 리스트
     */
    public List<Piece> findAllPiecesInDescOrder(Color color) {
        List<Piece> pieces = findAllPieces(color);
        Collections.sort(pieces);
        return pieces;
    }

    /**
     * 체스판 위 특정 색상의 기물들을 점수를 기준으로 오름차순 정렬한다
     *
     * @param color 색상
     * @return 정렬된 기물 리스트
     */
    public List<Piece> findAllPiecesInAscOrder(Color color) {
        List<Piece> pieces = findAllPieces(color);
        pieces.sort(Comparator.reverseOrder());
        return pieces;
    }

    /**
     * 특정 위치에 기물을 추가한다
     *
     * @param position 위치
     * @param piece    추가할 기물
     */
    public void addPiece(String position, Piece piece) {
        Position pos = Position.of(position);
        ranks.get(pos.getRankIndex())
             .setPiece(pos.getFileIndex(), piece);
    }

    /**
     * 특정 위치의 기물을 제거한다
     *
     * @param position 위치
     * @return 제거한 기물
     */
    public Piece removePiece(String position) {
        Position pos = Position.of(position);
        return ranks.get(pos.getRankIndex())
                    .removePiece(pos.getFileIndex());
    }

    /**
     * 체스판의 점수를 계산한다
     *
     * @param color 점수를 계산할 팀의 색상
     * @return 점수
     */
    public double calculatePoint(Color color) {
        double point = 0.0d;

        for (int fileIndex = 0; fileIndex < SIZE; fileIndex++) {
            double pawnPoint = 0.0d;

            for (int rankIndex = 0; rankIndex < SIZE; rankIndex++) {
                Piece piece = ranks.get(rankIndex)
                                   .getPieceAt(fileIndex);

                if (piece.getColor() != color) {
                    continue;
                }

                if (piece.getType() == Type.PAWN) {
                    pawnPoint += piece.getPoint();
                } else {
                    point += piece.getPoint();
                }
            }

            if (pawnPoint > 1) {
                pawnPoint /= 2;
            }
            point += pawnPoint;
        }

        return point;
    }

    /**
     * 기물을 이동시킨다
     *
     * @param sourcePosition 이동할 기물의 위치
     * @param targetPosition 목적지
     */
    public void move(String sourcePosition, String targetPosition) {
        if (findPiece(sourcePosition).getType() == Type.NONE) {
            throw new IllegalArgumentException("비어있는 칸 입니다.");
        }
        Piece piece = removePiece(sourcePosition);
        addPiece(targetPosition, piece);
    }
}
