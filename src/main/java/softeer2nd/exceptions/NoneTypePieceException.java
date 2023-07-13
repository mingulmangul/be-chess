package softeer2nd.exceptions;

public class NoneTypePieceException extends ChessGameException {

    private static final String MESSAGE = "존재하지 않는 기물입니다.";

    public NoneTypePieceException() {
        super(MESSAGE);
    }
}
