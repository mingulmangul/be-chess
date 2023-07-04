package softeer2nd.chess;

import softeer2nd.chess.pieces.Pawn;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Pawn> pawnList = new ArrayList<>();

    public int size() {
        return pawnList.size();
    }

    public void add(Pawn pawn) {
        pawnList.add(pawn);
    }

    public Pawn findPawn(int index) {
        return pawnList.get(index);
    }
}
