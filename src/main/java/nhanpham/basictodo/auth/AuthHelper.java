package nhanpham.basictodo.auth;

import java.time.Instant;

import org.bson.types.ObjectId;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.http.Cookie;
import nhanpham.basictodo.User.User;
import nhanpham.basictodo.auth.AuthToken.AuthToken;
import nhanpham.basictodo.auth.AuthToken.AuthVerifiedToken;

public class AuthHelper {

    private static String JWT_SECRET = "9yegwafy7pnc;asayrggasswpdapdas";
    private static String JWT_ISSUER = "nhanpham-app";
    private static int JWT_EXPIRES_SECONDS = 60 * 60 * 24;

    public AuthToken createToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);

            String token = JWT.create()
                    .withIssuer(JWT_ISSUER)
                    .withClaim("id", user.getId())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRES_SECONDS))
                    .sign(algorithm);

            return new AuthToken(token);
        } catch (JWTCreationException e) {
            throw new IllegalStateException("Failed to create token");
        }
    }

    public AuthVerifiedToken validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return new AuthVerifiedToken(new ObjectId(decodedJWT.getClaim("id").asString()),
                decodedJWT.getIssuedAt(),
                decodedJWT.getExpiresAt());
    }

    public Cookie createCookie(String name, Object value, Boolean isSecured, Boolean isHttpOnly, Integer daysToExpire,
            Integer secondsToExpire) {
        Cookie cookie = new Cookie(name, value.toString());

        if (daysToExpire != null)
            cookie.setMaxAge(daysToExpire * 24 * 60 * 60);

        if (daysToExpire == null && secondsToExpire != null)
            cookie.setMaxAge(secondsToExpire);

        cookie.setSecure(isSecured);
        cookie.setHttpOnly(isHttpOnly);

        return cookie;
    }
}
