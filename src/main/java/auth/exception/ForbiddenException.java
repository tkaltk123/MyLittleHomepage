package auth.exception;

public class ForbiddenException extends AuthenticationException {

    public ForbiddenException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
