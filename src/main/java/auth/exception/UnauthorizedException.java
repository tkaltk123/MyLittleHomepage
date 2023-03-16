package auth.exception;

public class UnauthorizedException extends AuthenticationException {

    public UnauthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
