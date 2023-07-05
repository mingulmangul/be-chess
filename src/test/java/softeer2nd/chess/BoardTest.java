package softeer2nd.chess;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static softeer2nd.utils.StringUtils.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setup() {
        board = new Board();
    }

    @Test
    @DisplayName("보드 초기화")
    public void create() throws Exception {
        board.initialize();
        assertEquals(32, board.pieceCount());
        String blankRank = appendNewLine("........");
        assertEquals(
                appendNewLine("RNBQKBNR") +
                        appendNewLine("PPPPPPPP") +
                        blankRank + blankRank + blankRank + blankRank +
                        appendNewLine("pppppppp") +
                        appendNewLine("rnbqkbnr"),
                board.showBoard()
        );
    }

    /*
    public void addPawn(final String color) {
        int size = board.size();

        Piece pawn = new Piece(color);
        board.add(pawn);

        assertEquals(size + 1, board.size());
        assertEquals(pawn, board.findPawn(size));
    }

    @Test
    @DisplayName("보드에 폰을 추가합니다")
    public void create() {
        addPawn(Piece.WHITE_COLOR);
        addPawn(Piece.BLACK_COLOR);
    }

    보드에 폰 이외의 객체 추가 -> 컴파일 에러
    @Test
    public void addPawnOnly() {
        Board board = new Board();

        Integer intVar = new Integer("7");
        board.add(intVar);
    }

    @Test
    @DisplayName("8*8 체스판을 초기화합니다")
    public void initialize() {
        board.initialize();
        String result = board.print();
        System.out.println(result);
        assertEquals("pppppppp", board.getWhitePawnsList());
        assertEquals("PPPPPPPP", board.getBlackPawnsList());
    }

     */
}
