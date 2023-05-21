package nhanpham.basictodo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import nhanpham.basictodo.User.User;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public User register(@RequestBody UserToRegisterDto userToRegister, HttpServletResponse response) {
        return authService.register(userToRegister, response);
    }

    @PostMapping("login")
    public User signIn(@RequestBody UserToLoginDto userToLogin, HttpServletResponse response) {
        return authService.signIn(userToLogin, response);
    }
    
    @PreAuthorize("isAuthenticated")
    @GetMapping("logout")
    public void signOut(HttpServletResponse response) {
        authService.signOut(response);
    }
}
