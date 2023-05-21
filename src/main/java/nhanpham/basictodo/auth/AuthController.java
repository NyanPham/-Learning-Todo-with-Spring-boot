package nhanpham.basictodo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nhanpham.basictodo.User.User;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public User register(@RequestBody UserToRegisterDto userToRegister) {
        return authService.register(userToRegister);
    }

    @PostMapping("login")
    public User signIn(@RequestBody UserToLoginDto userToLogin) {
        return authService.signIn(userToLogin);
    }
}
