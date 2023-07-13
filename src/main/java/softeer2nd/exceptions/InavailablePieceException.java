package softeer2nd.exceptions;

public class InavailablePieceException extends ChessGameException {

    private static final String MESSAGE = "상대 팀 기물은 움직일 수 없습니다.";

    public InavailablePieceException() {
        super(MESSAGE);
    }
}
