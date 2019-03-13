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
    User createUser(@RequestBody User newUser) { return this.service.createUser(newUser); }

    @PostMapping("/login")
    User isRegister(@RequestBody User newUser2) {
        return service.registerCheck(newUser2);
    }

    @GetMapping("/user/{id}")
    User getInfo(@PathVariable long id) {return service.getUserInfo(id);}



}
