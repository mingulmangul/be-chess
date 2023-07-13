package softeer2nd.exceptions;

public class InvalidCommandException extends ChessGameException {

    private static final String MESSAGE = "잘못된 명령어입니다.";

    public InvalidCommandException() {
        super(MESSAGE);
    }
}
