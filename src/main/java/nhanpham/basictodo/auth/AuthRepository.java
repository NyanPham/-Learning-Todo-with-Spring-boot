package nhanpham.basictodo.auth;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nhanpham.basictodo.User.User;

@Repository
public interface AuthRepository extends MongoRepository<User, ObjectId> {
    public Optional<User> getUserByEmail(String email);
}
