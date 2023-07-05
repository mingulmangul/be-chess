package softeer2nd.chess;

import softeer2nd.chess.pieces.Piece;
import softeer2nd.chess.pieces.PieceType;
import softeer2nd.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {

    private final int size = 8;
    private final List<List<Piece>> board = new ArrayList<>();
//    private List<Piece> whitePawnsList = new ArrayList<>();
//    private List<Piece> blackPawnsList = new ArrayList<>();

    private List<Piece> createFirstPieceRow(final String color) {
        return new ArrayList<>() {{
            add(Piece.createRook(color));
            add(Piece.createKnight(color));
            add(Piece.createBishop(color));
            add(Piece.createQueen(color));
            add(Piece.createKing(color));
            add(Piece.createBishop(color));
            add(Piece.createKnight(color));
            add(Piece.createRook(color));
        }};
    }

    private List<Piece> createPawnRow(final String color) {
        List<Piece> row = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            row.add(Piece.createPawn(color));
        }
        return row;
    }

    private List<Piece> createEmptyRow() {
        List<Piece> row = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            row.add(Piece.createEmptyPiece());
        }
        return row;
    }

    private void initBlack() {
        board.add(createFirstPieceRow(Piece.BLACK_COLOR));
        board.add(createPawnRow(Piece.BLACK_COLOR));
    }

    private void initWhite() {
        board.add(createPawnRow(Piece.WHITE_COLOR));
        board.add(createFirstPieceRow(Piece.WHITE_COLOR));
    }

    public void initialize() {
        initBlack();
        for (int i = 0; i < size - 4; i++) {
            board.add(createEmptyRow());
        }
        initWhite();
    }

    public int pieceCount() {
        int count = 0;
        for (List<Piece> row : board) {
            for (Piece piece : row) {
                if (!Objects.equals(piece.getType(), PieceType.NONE)) {
                    count++;
                }
            }
        }
        return count;
    }

    public String showBoard() {
        StringBuilder sb = new StringBuilder();
        for (List<Piece> row : board) {
            sb.append(rowToString(row));
        }
        return sb.toString();
    }

    private String rowToString(List<Piece> row) {
        StringBuilder sb = new StringBuilder();
        for (Piece piece : row) {
            sb.append(piece.toString());
        }
        return StringUtils.appendNewLine(sb.toString());
    }

    /*
    private List<Piece> createPawnsList(final String color) {
        boolean isWhite = color.equals(Piece.WHITE_COLOR);
        List<Piece> pawnsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Piece pawn = (isWhite) ? Piece.createWhitePawn() : Piece.createBlackPawn();
            pawnsList.add(pawn);
        }
        return pawnsList;
    }

    public int size() {
        return whitePawnsList.size() + blackPawnsList.size();
    }

    public void add(Piece pawn) {
        if (pawn.getColor().equals(Piece.WHITE_COLOR)) {
            whitePawnsList.add(new Piece(Piece.WHITE_COLOR));
        } else {
            blackPawnsList.add(new Piece(Piece.BLACK_COLOR));
        }
    }

    public Pawn findPawn(int index) {
        return pawnList.get(index);
    }

    public String pawnsListToString(List<Piece> pawnList) {
        StringBuilder sb = new StringBuilder();
        if (pawnList.isEmpty()) {
            return sb.append("........").toString();
        }
        for (Piece pawn : pawnList) {
            sb.append(pawn.getRepresentation());
        }
        return sb.toString();
    }

    public String getWhitePawnsList() {
        return pawnsListToString(whitePawnsList);
    }

    public String getBlackPawnsList() {
        return pawnsListToString(blackPawnsList);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (List<Piece> row : board) {
            sb.append(pawnsListToString(row)).append('\n');
        }
        return sb.toString();
    }

     */
}
