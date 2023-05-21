package nhanpham.basictodo.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import nhanpham.basictodo.User.User;
import nhanpham.basictodo.User.UserRole;
import nhanpham.basictodo.auth.AuthToken.AuthToken;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthHelper authHelper;

    public User register(UserToRegisterDto userToRegister, HttpServletResponse response) {
        Boolean userExists = authRepository.getUserByEmail(userToRegister.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("User already exists");
        }

        User newUser = new User(userToRegister.getUsername(), userToRegister.getEmail(), userToRegister.getPassword(),
                UserRole.USER);

        String hashedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        newUser.enableUser();

        authRepository.save(newUser);

        AuthToken token = authHelper.createToken(newUser);
        Cookie cookie = authHelper.createCookie("jwt", token.getToken(), false, false, 1);
        response.addCookie(cookie);

        return newUser;
    }

    public User signIn(UserToLoginDto userToLogin, HttpServletResponse response) {
        Optional<User> user = authRepository.getUserByEmail(userToLogin.getEmail());

        if (!user.isPresent()) {
            throw new IllegalStateException("User with that email not found");
        }

        Boolean correctPassword = bCryptPasswordEncoder.matches(userToLogin.getPassword(), user.get().getPassword());
        if (!correctPassword) {
            throw new IllegalStateException("Incorrect Password");
        }

        AuthToken token = authHelper.createToken(user.get());
        Cookie cookie = authHelper.createCookie("jwt", token.getToken(), false, false, 1);

        response.addCookie(cookie);

        return user.get();
    }
}
