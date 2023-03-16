package auth.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }
}
