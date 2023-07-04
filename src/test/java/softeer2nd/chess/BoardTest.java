package softeer2nd.chess;

import org.junit.jupiter.api.*;
import softeer2nd.chess.Board;
import softeer2nd.chess.pieces.Pawn;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    Board board;

    @BeforeEach
    public void before() {
        board = new Board();
    }

    public void addPawn(String color) {
        int size = board.size();

        Pawn pawn = new Pawn(color);
        board.add(pawn);

        assertEquals(size + 1, board.size());
        assertEquals(pawn, board.findPawn(size));
    }

    @Test
    @DisplayName("보드에 폰을 추가합니다")
    public void create() {
        addPawn(Pawn.WHITE_COLOR);
        addPawn(Pawn.BLACK_COLOR);
    }

//    보드에 폰 이외의 객체 추가 -> 컴파일 에러
//    @Test
//    public void addPawnOnly() {
//        Board board = new Board();
//
//        Integer intVar = new Integer("7");
//        board.add(intVar);
//    }
}
