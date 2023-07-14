package softeer2nd.exceptions;

public abstract class ChessGameException extends RuntimeException {
    protected ChessGameException(String message) {
        super(message);
    }
}
