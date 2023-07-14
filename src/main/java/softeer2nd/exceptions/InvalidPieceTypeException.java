package softeer2nd.exceptions;

public class InvalidPieceTypeException extends ChessGameException {

    private static final String MESSAGE = "기물의 타입이 올바르지 않습니다";

    public InvalidPieceTypeException() {
        super(MESSAGE);
    }
}
