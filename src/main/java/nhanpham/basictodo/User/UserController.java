package nhanpham.basictodo.User;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody UserToUpsertDao userToCreate) {
        return userService.createUser(userToCreate);
    }

    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable("id") ObjectId id) {
        return userService.getUser(id);
    }

    @PatchMapping("{id}")
    public User updateUser(@PathVariable("id") ObjectId id, @RequestBody UserToUpsertDao userToUpdate) {
        return userService.updateUser(id, userToUpdate);
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable("id") ObjectId id) {
        return userService.deleteUser(id);
    }
}
