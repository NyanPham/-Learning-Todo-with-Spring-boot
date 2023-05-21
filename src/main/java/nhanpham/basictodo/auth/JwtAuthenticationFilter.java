package nhanpham.basictodo.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nhanpham.basictodo.User.User;
import nhanpham.basictodo.User.UserService;
import nhanpham.basictodo.auth.AuthToken.AuthVerifiedToken;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthHelper authHelper;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String requestTokenHeader = request.getHeader("Authorization");

        String token = null;

        Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                .filter(c -> "jwt".equals(c.getName()))
                .findFirst();

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            token = requestTokenHeader.split("Bearer ")[1];
        } else if (tokenCookie.isPresent()) {
            token = tokenCookie.get().getValue();
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            AuthVerifiedToken verified = authHelper.validateToken(token);

            if (verified.getExpiresAt().before(new Date()))
                throw new IllegalStateException("Token expired. Please login again!");

            Optional<User> currentUser = userService.getUser(verified.getId());

            if (!currentUser.isPresent())
                throw new IllegalStateException("User with that token not found anymore");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currentUser,
                    null, currentUser.get().getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JWTDecodeException e) {
            filterChain.doFilter(request, response);
            return;
        }

        // Optional<User> user = userService.getUser()
        filterChain.doFilter(request, response);
    }

}
