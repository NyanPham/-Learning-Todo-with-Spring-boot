package nhanpham.basictodo.auth.AuthToken;

import java.util.Date;

import org.bson.types.ObjectId;

public class AuthVerifiedToken {
    private ObjectId id;
    private Date issuedAt;
    private Date expiresAt;

    public ObjectId getId() {
        return id;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public AuthVerifiedToken(ObjectId id, Date issuedAt, Date expiresAt) {
        this.id = id;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
