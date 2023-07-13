package softeer2nd.exceptions;

public class InvalidDirectionException extends ChessGameException {

    private static final String MESSAGE = "잘못된 방향입니다.";

    public InvalidDirectionException() {
        super(MESSAGE);
    }
}
