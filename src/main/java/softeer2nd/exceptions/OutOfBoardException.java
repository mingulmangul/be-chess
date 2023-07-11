package softeer2nd.exceptions;

public class OutOfBoardException extends RuntimeException {

    private static final String MESSAGE = "잘못된 좌표입니다.";

    public OutOfBoardException() {
        super(MESSAGE);
    }
}
