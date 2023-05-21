package nhanpham.basictodo.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import nhanpham.basictodo.User.User;
import nhanpham.basictodo.User.UserRole;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User register(UserToRegisterDto userToRegister) {
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

        return newUser;
    }

    public User signIn(UserToLoginDto userToLogin) {
        Optional<User> user = authRepository.getUserByEmail(userToLogin.getEmail());
        
        if (!user.isPresent()) {
            throw new IllegalStateException("User with that email not found");
        }

        Boolean correctPassword = bCryptPasswordEncoder.matches(userToLogin.getPassword(), user.get().getPassword());
        if (!correctPassword) {
            throw new IllegalStateException("Incorrect Password");
        }

        return user.get();
    }
}
