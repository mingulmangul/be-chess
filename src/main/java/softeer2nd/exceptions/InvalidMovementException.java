package softeer2nd.exceptions;

public class InvalidMovementException extends ChessGameException {

    private static final String MESSAGE = "이동할 수 없는 위치입니다.";

    public InvalidMovementException() {
        super(MESSAGE);
    }
}
