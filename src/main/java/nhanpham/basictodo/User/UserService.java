package nhanpham.basictodo.User;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserToUpsertDto userToCreate) {
        return userRepository.insert(new User(userToCreate.getUsername(), userToCreate.getEmail(),
                userToCreate.getPassword(), userToCreate.getUserRole()));
    }

    public Optional<User> getUser(ObjectId id) {
        return userRepository.findById(id);
    }

    public User updateUser(ObjectId id, UserToUpsertDto userToUpdate) {
        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("id").is(id));

        Update userUpdate = new Update();

        userUpdate.set("username", userToUpdate.getUsername());
        userUpdate.set("password", userToUpdate.getPassword());
        userUpdate.set("email", userToUpdate.getEmail());
        userUpdate.set("userRole", userToUpdate.getUserRole());

        return mongoTemplate.findAndModify(userQuery, userUpdate, new FindAndModifyOptions().returnNew(true),
                User.class);
    }

    public String deleteUser(ObjectId id) {
        userRepository.deleteById(id);

        return "User deleted";
    }
}
