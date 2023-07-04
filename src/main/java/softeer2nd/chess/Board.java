package softeer2nd.chess;

import softeer2nd.chess.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int size = 8;
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
        whitePawnsList = createPawnsList(Pawn.WHITE_COLOR);
        blackPawnsList = createPawnsList(Pawn.BLACK_COLOR);
    }

    public String pawnsListToString(List<Pawn> pawnList) {
        StringBuilder sb = new StringBuilder();
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
        for (int i = 0; i < size; i++) {
            if (i == 1) {
                sb.append(getBlackPawnsList());
            } else if (i == size - 2) {
                sb.append(getWhitePawnsList());
            } else {
                sb.append("........");
            }
            sb.append('\n');
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
