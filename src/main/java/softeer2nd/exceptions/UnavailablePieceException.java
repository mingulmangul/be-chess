package softeer2nd.exceptions;

public class UnavailablePieceException extends ChessGameException {

    private static final String MESSAGE = "상대 팀 기물은 움직일 수 없습니다.";

    public UnavailablePieceException() {
        super(MESSAGE);
    }
}
