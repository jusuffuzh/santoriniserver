package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setRegistrationDate(LocalDate.now());
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User getUserInfo(long id) {
        return userRepository.findById(id);
    }

    public User edit(long id, User changedUser) {
        User Y = userRepository.findById(id);
        Y.setBirthday(changedUser.getBirthday());
        Y.setUsername(changedUser.getUsername());
        return Y;
    }

    public User login(User loginUser) {
        User X = userRepository.findByUsername(loginUser.getUsername());
        if (X.getPassword().equals(loginUser.getPassword()) && X.getUsername().equals(loginUser.getUsername())) {
            X.setStatus(UserStatus.ONLINE);
            return X;
        } else {
            return null;
        }

    }}