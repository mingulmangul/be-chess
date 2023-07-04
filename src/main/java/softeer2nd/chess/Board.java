package softeer2nd.chess;

import softeer2nd.chess.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int size = 8;
    private final List<List<Pawn>> board = new ArrayList<>();
    private List<Pawn> whitePawnsList = new ArrayList<>();
    private List<Pawn> blackPawnsList = new ArrayList<>();

    private List<Pawn> createPawnsList(final String color) {
        List<Pawn> pawnsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pawnsList.add(new Pawn(color));
        }
        return pawnsList;
    }

    public void initialize() {
        for (int i = 0; i < size; i++) {
            if (i == 1) {
                blackPawnsList = createPawnsList(Pawn.BLACK_COLOR);
                board.add(blackPawnsList);
            } else if (i == size - 2) {
                whitePawnsList = createPawnsList(Pawn.WHITE_COLOR);
                board.add(whitePawnsList);
            } else {
                board.add(new ArrayList<>());
            }
        }
    }

    public String pawnsListToString(List<Pawn> pawnList) {
        StringBuilder sb = new StringBuilder();
        if (pawnList.isEmpty()) {
            return sb.append("........").toString();
        }
        for (Pawn pawn : pawnList) {
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
        for (List<Pawn> row : board) {
            sb.append(pawnsListToString(row)).append('\n');
        }
        return sb.toString();
    }

    public int size() {
        return whitePawnsList.size() + blackPawnsList.size();
    }

    public void add(Pawn pawn) {
        if (pawn.getColor().equals(Pawn.WHITE_COLOR)) {
            whitePawnsList.add(new Pawn(Pawn.WHITE_COLOR));
        } else {
            blackPawnsList.add(new Pawn(Pawn.BLACK_COLOR));
        }
    }

//    public Pawn findPawn(int index) {
//        return pawnList.get(index);
//    }
}
