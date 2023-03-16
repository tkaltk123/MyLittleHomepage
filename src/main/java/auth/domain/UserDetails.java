package auth.domain;

public interface UserDetails {

    Long getId();

    String getUsername();

    String getPassword();

    UserRole getRole();

    boolean isWrongPassword(String password);

    boolean isNotAdmin();
}
