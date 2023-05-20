package nhanpham.basictodo.User;

public class UserToUpsertDao {
    private String email;
    private String username;
    private String password;
    private UserRole userRole;

    public UserToUpsertDao(String email, String username, String password, UserRole userRole) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
