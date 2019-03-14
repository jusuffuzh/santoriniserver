package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    Iterable<User> all() {
        return service.getUsers();
    }

    @PostMapping("/register")
    User createUser(@RequestBody User newUser) { return service.createUser(newUser); }

    @GetMapping("/user/{id}")
    User getInfo(@PathVariable long id) {return service.getUserInfo(id);}

    @PutMapping("/edit/{id}")
    User changeUser(@PathVariable long id, @RequestBody User changeUser) { return service.edit(id, changeUser); }

    @PostMapping("/login")
    User login(@RequestBody User loginUser) { return service.login(loginUser);
    }

}
